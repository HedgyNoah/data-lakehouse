package com.fishdicg.identity_service.repository.keycloakclient;

import com.fishdicg.identity_service.dto.identity.*;
import com.fishdicg.identity_service.dto.request.authorization.AssignRoleRequest;
import com.fishdicg.identity_service.dto.response.RoleResponse;
import com.fishdicg.identity_service.dto.response.UserResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;

@FeignClient(name = "identity-client", url = "${idp.url}")
public interface IdentityClient {
    @PostMapping(value = "/realms/${idp.realm-name}/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenExchangeResponse exchangeToken(@QueryMap TokenExchangeParam param);

    @GetMapping(value = "/admin/realms/${idp.realm-name}/users", produces = MediaType.APPLICATION_JSON_VALUE)
    List<UserResponse> getAllUsers(@RequestHeader("authorization") String token,
                                   @RequestParam(value = "first", required = false, defaultValue = "0") int first,
                                   @RequestParam(value = "max", required = false, defaultValue = "10") int max,
                                   @RequestParam(value = "search", required = false, defaultValue = "") String search);

    @GetMapping(value = "/admin/realms/${idp.realm-name}/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserResponse getUser(@RequestHeader("authorization") String token,
                         @PathVariable String userId);

    @PostMapping(value = "/admin/realms/${idp.realm-name}/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createUser(
            @RequestHeader("authorization") String token,
            @RequestBody UserCreationParam param
    );

    @DeleteMapping(value = "/admin/realms/${idp.realm-name}/users/{id}")
    void deleteUser(
            @RequestHeader("authorization") String token,
            @PathVariable String id
    );

    @PutMapping(value = "/admin/realms/${idp.realm-name}/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    void updateUser(
            @RequestHeader("authorization") String token,
            @PathVariable String id,
            @RequestBody UserUpdateParam param
    );

    @PostMapping(value = "/admin/realms/${idp.realm-name}/users/{userId}/role-mappings/realm",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    void assignRole(
            @RequestHeader("authorization") String token,
            @PathVariable String userId,
            @RequestBody List<AssignRoleRequest> roles);

    @GetMapping(value = "/admin/realms/${idp.realm-name}/users/{userId}/role-mappings/realm",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<RoleResponse> getUserRole(
            @RequestHeader("authorization") String token,
            @PathVariable String userId);
}
