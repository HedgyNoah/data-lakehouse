package com.fishdicg.workspace_service.controller;

import com.fishdicg.workspace_service.dto.ApiResponse;
import com.fishdicg.workspace_service.dto.PageResponse;
import com.fishdicg.workspace_service.dto.response.WorkspaceResponse;
import com.fishdicg.workspace_service.dto.request.WorkspaceRequest;
import com.fishdicg.workspace_service.service.WorkspaceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("/manage")
public class WorkspaceController {
    WorkspaceService workspaceService;

    @PostMapping
    ApiResponse<WorkspaceResponse> createWorkspace(@RequestBody WorkspaceRequest request) {
        return ApiResponse.<WorkspaceResponse>builder()
                .code(201)
                .result(workspaceService.createWorkspace(request))
                .build();
    }

    @GetMapping
    ApiResponse<PageResponse<WorkspaceResponse>> getAllWorkspace(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "sortBy", required = false, defaultValue = "createdDate") String sortBy,
            @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
            @RequestParam(value = "search", required = false, defaultValue = "") String search) {
        return ApiResponse.<PageResponse<WorkspaceResponse>>builder()
                .code(200)
                .result(workspaceService.getAll(page, size, sortBy, order, search))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<WorkspaceResponse> getWorkspace(@PathVariable String id) {
        return ApiResponse.<WorkspaceResponse>builder()
                .code(200)
                .result(workspaceService.getOne(id))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<WorkspaceResponse> updateWorkspace(@PathVariable String id,
                                                   @RequestBody WorkspaceRequest request) {
        return ApiResponse.<WorkspaceResponse>builder()
                .code(200)
                .result(workspaceService.updateWorkspace(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteWorkspace(@PathVariable String id) {
        return ApiResponse.<String>builder()
                .code(200)
                .result(workspaceService.deleteWorkspace(id))
                .build();
    }
}
