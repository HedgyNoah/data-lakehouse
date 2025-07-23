package com.fishdicg.identity_service.service;

import com.fishdicg.event.dto.NotificationEvent;
import com.fishdicg.identity_service.dto.PageResponse;
import com.fishdicg.identity_service.dto.identity.*;
import com.fishdicg.identity_service.dto.request.UserCreationRequest;
import com.fishdicg.identity_service.dto.request.UserUpdateRequest;
import com.fishdicg.identity_service.dto.request.authorization.AssignRoleRequest;
import com.fishdicg.identity_service.dto.response.RoleResponse;
import com.fishdicg.identity_service.dto.response.UserResponse;
import com.fishdicg.identity_service.exception.ErrorNormalizer;
import com.fishdicg.identity_service.mapper.UserMapper;
import com.fishdicg.identity_service.repository.keycloakclient.IdentityClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserMapper userMapper;
    IdentityClient identityClient;
    ErrorNormalizer errorNormalizer;
    KeycloakExtractor keycloakExtractor;
    KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${idp.roles.user.id}")
    @NonFinal
    String userRoleId;

    @Value("${idp.roles.user.name}")
    @NonFinal
    String userRoleName;

    @PreAuthorize("hasAuthority('VIEW_USER')")
    public UserResponse getUser(String id) {
        try {
            UserResponse userResponse = identityClient
                    .getUser(keycloakExtractor.extractClientAccessToken(), id);
            userResponse.setRoles(getUserRole(id));
            return userResponse;
        } catch (FeignException e) {
            throw errorNormalizer.handleKeycloakException(e);
        }
    }

    public UserResponse getMyInfo() {
        var id = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            UserResponse userResponse = identityClient
                    .getUser(keycloakExtractor.extractClientAccessToken(), id);
            userResponse.setRoles(getUserRole(id));
            return userResponse;
        } catch (FeignException e) {
            throw errorNormalizer.handleKeycloakException(e);
        }
    }


    @PreAuthorize("hasAuthority('VIEW_USER')")
    public PageResponse<UserResponse> getAllUsers(int page, int size, String search) {
        try {
            int first = (page-1) * size;
            var userList = identityClient.getAllUsers(keycloakExtractor.extractClientAccessToken(),
                            first, size, search)
                    .stream().map(userResponse -> {
                        userResponse.setRoles(getUserRole(userResponse.getId()));
                        return userResponse;
                    }).toList();
            var totalElements = identityClient.getAllUsers(keycloakExtractor
                    .extractClientAccessToken(), 0, 10000, "").size();
            var totalPage = totalElements / size;

            return PageResponse.<UserResponse>builder()
                    .currentPage(page)
                    .pageSize(size)
                    .totalPage(totalPage)
                    .totalElements(totalElements)
                    .search(search)
                    .data(userList)
                    .build();
        } catch (FeignException e) {
            throw errorNormalizer.handleKeycloakException(e);
        }
    }

    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public String updateUser(UserUpdateRequest request, String id) {
        try {
            identityClient.updateUser(keycloakExtractor.extractClientAccessToken(), id,
                    UserUpdateParam.builder()
                                    .firstName(request.getFirstName())
                                    .lastName(request.getLastName())
                                    .email(request.getEmail())
                                    .credentials(List.of(Credential.builder()
                                                    .value(request.getPassword())
                                            .build()))
                            .build());

            return "User updated";
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeycloakException(exception);
        }
    }

    @PreAuthorize("hasAuthority('DELETE_USER')")
    public String deleteUser(String id) {
        try {
            identityClient.deleteUser(keycloakExtractor.extractClientAccessToken(), id);
            return "User has been successfully deleted.";
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeycloakException(exception);
        }
    }

    @PreAuthorize("hasAuthority('ADD_USER')")
    public UserResponse createUser(UserCreationRequest request) {
        try {
            var creationResponse = identityClient.createUser(keycloakExtractor.extractClientAccessToken(),
                    UserCreationParam.builder()
                            .username(request.getUsername())
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .email(request.getEmail())
                            .enabled(true)
                            .emailVerified(false)
                            .credentials(List.of(Credential.builder()
                                    .type("password")
                                    .value(request.getPassword())
                                    .temporary(false)
                                    .build()))
                            .build());

            String keycloakId = keycloakExtractor.extractKeycloakId(creationResponse);

            // Assign default user role
            assignRole(keycloakId, List.of(AssignRoleRequest.builder()
                                    .id(userRoleId)
                                    .name(userRoleName)
                            .build()));

            NotificationEvent notificationEvent = NotificationEvent.builder()
                    .channel("EMAIL")
                    .recipient(request.getEmail())
                    .subject("Welcome to Data lakehouse")
                    .body("Hello, " + request.getUsername())
                    .build();

            // Publish message to Kafka
            kafkaTemplate.send("notification-delivery", notificationEvent);

            UserResponse userResponse = userMapper.toUserResponse(request);
            userResponse.setId(keycloakId);
            userResponse.setRoles(getUserRole(keycloakId));

            return userResponse;
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeycloakException(exception);
        }
    }

    public String assignRole(String id, List<AssignRoleRequest> request) {
        try {
            identityClient.assignRole(
                    keycloakExtractor.extractClientAccessToken(), id, request);

            return "Role has been assigned.";
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeycloakException(exception);
        }
    }

    public List<RoleResponse> getUserRole(String id) {
        try {
            return identityClient.getUserRole(
                    keycloakExtractor.extractClientAccessToken(), id)
                    .stream().filter(roleResponse -> {
                        String[] nonKeycloakName = roleResponse.getName().split("-");
                        return !nonKeycloakName[0].equals("default");
                    }).toList();
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeycloakException(exception);
        }
    }
}
