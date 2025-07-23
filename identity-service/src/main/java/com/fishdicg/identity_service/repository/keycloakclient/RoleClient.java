package com.fishdicg.identity_service.repository.keycloakclient;

import com.fishdicg.identity_service.dto.ApiResponse;
import com.fishdicg.identity_service.dto.request.authorization.AssignRoleRequest;
import com.fishdicg.identity_service.dto.request.authorization.RoleCreationRequest;
import com.fishdicg.identity_service.dto.response.PermissionResponse;
import com.fishdicg.identity_service.dto.response.RoleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "role-client", url = "${idp.url}")
public interface RoleClient {
    @PostMapping(value = "/admin/realms/${idp.realm-name}/roles",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    void createRole(
            @RequestHeader("authorization") String token,
            @RequestBody RoleCreationRequest request);

    @GetMapping(value = "/admin/realms/${idp.realm-name}/roles/{name}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    RoleResponse getRole(
            @PathVariable String name,
            @RequestHeader("authorization") String token);

    @GetMapping(value = "/admin/realms/${idp.realm-name}/roles",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<RoleResponse> getAllRole(@RequestHeader("authorization") String token,
                                  @RequestParam(value = "first", required = false, defaultValue = "0") int first,
                                  @RequestParam(value = "max", required = false, defaultValue = "10") int max,
                                  @RequestParam(value = "search", required = false, defaultValue = "") String search);

    @PutMapping(value = "/admin/realms/${idp.realm-name}/roles/{name}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    void updateRole(
            @PathVariable String name,
            @RequestHeader("authorization") String token,
            @RequestBody RoleCreationRequest request);

    @DeleteMapping(value = "/admin/realms/${idp.realm-name}/roles/{name}")
    void deleteRole(
            @PathVariable String name,
            @RequestHeader("authorization") String token);

    @GetMapping(value = "/admin/realms/${idp.realm-name}/roles/{name}/composites",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<PermissionResponse> getPermission(
            @PathVariable String name,
            @RequestHeader("authorization") String token);

    @PostMapping(value = "/admin/realms/${idp.realm-name}/roles/{name}/composites",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    void addPermission(
            @PathVariable String name,
            @RequestHeader("authorization") String token,
            @RequestBody List<AssignRoleRequest> requests);
}
