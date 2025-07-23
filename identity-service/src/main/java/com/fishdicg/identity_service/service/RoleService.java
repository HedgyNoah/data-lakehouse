package com.fishdicg.identity_service.service;

import com.fishdicg.identity_service.dto.PageResponse;
import com.fishdicg.identity_service.dto.request.authorization.AssignRoleRequest;
import com.fishdicg.identity_service.dto.request.authorization.RoleCreationRequest;
import com.fishdicg.identity_service.dto.response.PermissionResponse;
import com.fishdicg.identity_service.dto.response.RoleResponse;
import com.fishdicg.identity_service.exception.ErrorNormalizer;
import com.fishdicg.identity_service.repository.keycloakclient.RoleClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    KeycloakExtractor keycloakExtractor;
    RoleClient roleClient;
    ErrorNormalizer errorNormalizer;

    @PreAuthorize("hasAuthority('MANAGE_AUTHORIZATION')")
    public String createRole(RoleCreationRequest request) {
        try {
            roleClient.createRole(keycloakExtractor.extractClientAccessToken(), request);
            return "Role has been successfully created.";
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeycloakException(exception);
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_AUTHORIZATION')")
    public RoleResponse getRole(String name) {
        try {
            RoleResponse roleResponse = roleClient
                    .getRole(name, keycloakExtractor.extractClientAccessToken());
            roleResponse.setPermissions(getRolePermissions(roleResponse.getName()));

            return roleResponse;
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeycloakException(exception);
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_AUTHORIZATION')")
    public PageResponse<RoleResponse> getAllRoles(int page, int size, String search) {
        try {
            int first = (page-1) * size;
            var roleList = listOfRoles(first, size, search);
            var totalElements = listOfRoles(0, 1000, "").size();
            var totalPage = totalElements / size;

            return PageResponse.<RoleResponse>builder()
                    .currentPage(page)
                    .pageSize(size)
                    .totalPage(totalPage)
                    .totalElements(totalElements)
                    .search(search)
                    .data(roleList)
                    .build();
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeycloakException(exception);
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_AUTHORIZATION')")
    public PageResponse<RoleResponse> getAllPermissions(int page, int size, String search) {
        try {
            int first = (page - 1) * size;
            var permissionList = listOfPermissions(first, size, search);
            var totalElements = listOfPermissions(0, 10000, "").size();
            var totalPage = totalElements / size;

            return PageResponse.<RoleResponse>builder()
                    .currentPage(page)
                    .pageSize(size)
                    .totalElements(totalElements)
                    .totalPage(totalPage)
                    .search(search)
                    .data(permissionList)
                    .build();
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeycloakException(exception);
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_AUTHORIZATION')")
    public String updateRole(String name, RoleCreationRequest request) {
        try {
            roleClient.updateRole(name, keycloakExtractor.extractClientAccessToken(), request);
            return "Role has been successfully updated.";
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeycloakException(exception);
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_AUTHORIZATION')")
    public String deleteRole(String name) {
        try {
            roleClient.deleteRole(name, keycloakExtractor.extractClientAccessToken());
            return "Role has been successfully deleted.";
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeycloakException(exception);
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_AUTHORIZATION')")
    public String addPermission(String name, List<AssignRoleRequest> request) {
        try {
            roleClient.addPermission(name, keycloakExtractor.extractClientAccessToken(), request);
            return "Permissions have been successfully added.";
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeycloakException(exception);
        }
    }

    @PreAuthorize("hasAuthority('MANAGE_AUTHORIZATION')")
    public List<PermissionResponse> getRolePermissions(String name) {
        try {
            return roleClient.getPermission(name,
                    keycloakExtractor.extractClientAccessToken());
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeycloakException(exception);
        }
    }

    private List<RoleResponse> listOfRoles(int first, int max, String search) {
        return roleClient.getAllRole(keycloakExtractor.extractClientAccessToken(),
                        first, max, search)
                .stream().map(roleResponse -> {
                            roleResponse.setPermissions(getRolePermissions(roleResponse.getName()));
                            return roleResponse;
                        }
                ).filter(roleResponse -> {
                    String[] separatedName = roleResponse.getName().split("_");
                    String[] nonKeycloakName = roleResponse.getName().split("-");
                    return separatedName[0].equals("ROLE")
                            && !nonKeycloakName[0].equals("default");
                }).toList();
    }

    private List<RoleResponse> listOfPermissions(int first, int max, String search) {
        return roleClient.getAllRole(keycloakExtractor.extractClientAccessToken(),
                        first, max, search)
                .stream().filter(roleResponse -> {
                    String[] separatedName = roleResponse.getName().split("_");
                    String[] nonKeycloakName = roleResponse.getName().split("-");
                    return !separatedName[0].equals("ROLE")
                            && !nonKeycloakName[0].equals("default");
                }).toList();
    }
}