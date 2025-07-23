package com.fishdicg.identity_service.controller;

import com.fishdicg.identity_service.dto.ApiResponse;
import com.fishdicg.identity_service.dto.PageResponse;
import com.fishdicg.identity_service.dto.request.authorization.AssignRoleRequest;
import com.fishdicg.identity_service.dto.request.authorization.RoleCreationRequest;
import com.fishdicg.identity_service.dto.response.PermissionResponse;
import com.fishdicg.identity_service.dto.response.RoleResponse;
import com.fishdicg.identity_service.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<String> createRole(@RequestBody RoleCreationRequest request) {
        return ApiResponse.<String>builder()
                .result(roleService.createRole(request))
                .build();
    }

    @GetMapping("/{name}")
    ApiResponse<RoleResponse> getRole(@PathVariable String name) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.getRole(name))
                .build();
    }

    @GetMapping
    ApiResponse<PageResponse<RoleResponse>> getAllRoles(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "search", required = false, defaultValue = "") String search) {
        return ApiResponse.<PageResponse<RoleResponse>>builder()
                .result(roleService.getAllRoles(page, size, search))
                .build();
    }

    @PutMapping("/{name}")
    ApiResponse<String> updateRole(@PathVariable String name,
                                   @RequestBody RoleCreationRequest request) {
        return ApiResponse.<String>builder()
                .result(roleService.updateRole(name, request))
                .build();
    }

    @DeleteMapping("/{name}")
    ApiResponse<String> deleteRole(@PathVariable String name) {
        return ApiResponse.<String>builder()
                .result(roleService.deleteRole(name))
                .build();
    }

    @GetMapping("/{name}/permissions")
    ApiResponse<List<PermissionResponse>> getRolePermissions(@PathVariable String name) {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(roleService.getRolePermissions(name))
                .build();
    }   

    @PostMapping("/{name}")
    ApiResponse<String> addPermissionToRole(@PathVariable String name,
                                            @RequestBody List<AssignRoleRequest> request) {
        return ApiResponse.<String>builder()
                .result(roleService.addPermission(name, request))
                .build();
    }

    @GetMapping("/permissions")
    ApiResponse<PageResponse<RoleResponse>> getAllPermissions(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "search", required = false, defaultValue = "") String search) {
        return ApiResponse.<PageResponse<RoleResponse>>builder()
                .result(roleService.getAllPermissions(page, size, search))
                .build();
    }
}
