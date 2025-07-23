package com.fishdicg.identity_service.controller;

import com.fishdicg.identity_service.dto.ApiResponse;
import com.fishdicg.identity_service.dto.PageResponse;
import com.fishdicg.identity_service.dto.request.UserCreationRequest;
import com.fishdicg.identity_service.dto.request.UserUpdateRequest;
import com.fishdicg.identity_service.dto.request.authorization.AssignRoleRequest;
import com.fishdicg.identity_service.dto.response.RoleResponse;
import com.fishdicg.identity_service.dto.response.UserResponse;
import com.fishdicg.identity_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<UserResponse> getUser(@PathVariable String id) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(id))
                .build();
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @GetMapping
    ApiResponse<PageResponse<UserResponse>> getAllUsers(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "search", required = false, defaultValue = "") String search) {
        return ApiResponse.<PageResponse<UserResponse>>builder()
                .result(userService.getAllUsers(page, size, search))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<String> updateUser(@PathVariable String id,
                                         @RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<String>builder()
                .result(userService.updateUser(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteUser(@PathVariable String id) {
        return ApiResponse.<String>builder()
                .result(userService.deleteUser(id))
                .build();
    }

    @PostMapping("/{id}")
    ApiResponse<String> assignRoleToUser(@PathVariable String id,
                                         @RequestBody List<AssignRoleRequest> request) {
        return ApiResponse.<String>builder()
                .result(userService.assignRole(id, request))
                .build();
    }

    @GetMapping("/{id}/roles")
    ApiResponse<List<RoleResponse>> getUserRole(@PathVariable String id) {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(userService.getUserRole(id))
                .build();
    }
}
