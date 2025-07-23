package com.fishdicg.workspace_service.service;

import com.fishdicg.workspace_service.dto.PageResponse;
import com.fishdicg.workspace_service.dto.response.UserResponse;
import com.fishdicg.workspace_service.dto.response.WorkspaceResponse;
import com.fishdicg.workspace_service.dto.request.WorkspaceRequest;
import com.fishdicg.workspace_service.entity.Workspace;
import com.fishdicg.workspace_service.exception.AppException;
import com.fishdicg.workspace_service.exception.ErrorCode;
import com.fishdicg.workspace_service.mapper.WorkspaceMapper;
import com.fishdicg.workspace_service.repository.WorkspaceRepository;
import com.fishdicg.workspace_service.repository.httpclient.IdentityClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WorkspaceService {
    WorkspaceRepository workspaceRepository;
    WorkspaceMapper workspaceMapper;
    IdentityClient identityClient;

    @PreAuthorize("hasAuthority('ADD_WORKSPACE')")
    public WorkspaceResponse createWorkspace(WorkspaceRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            Workspace workspace = Workspace.builder()
                    .userId(authentication.getName())
                    .name(request.getName())
                    .description(request.getDescription())
                    .createdDate(LocalDateTime.now())
                    .build();

            workspace = workspaceRepository.save(workspace);
            WorkspaceResponse workspaceResponse = workspaceMapper.toWorkspaceResponse(workspace);

            var user = identityClient.getUser(authentication.getName());

            workspaceResponse.setCreator(user.getResult().getUsername());
            return workspaceResponse;
        } catch (FeignException e) {
            throw new AppException(ErrorCode.USER_NOT_EXIST);
        }
    }

    @PreAuthorize("hasAuthority('VIEW_WORKSPACE')")
    public PageResponse<WorkspaceResponse> getAll(int page, int size, String sortBy,
                                                  String order, String search) {
        Sort sort = "asc".equalsIgnoreCase(order) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<Workspace> pageData;
        if(search != null && !search.isEmpty()) {
            pageData = workspaceRepository.findByNameContaining(search, pageable);
        } else {
            pageData = workspaceRepository.findAll(pageable);
        }

        List<WorkspaceResponse> workspaceList = pageData.getContent().stream().map(workspace -> {
            var workspaceResponse = workspaceMapper.toWorkspaceResponse(workspace);

            try {
                UserResponse userResponse = identityClient.getUser(workspace.getUserId()).getResult();
                workspaceResponse.setCreator(userResponse.getUsername());
            } catch (FeignException exception) {
                throw new AppException(ErrorCode.USER_NOT_EXIST);
            }

            return workspaceResponse;
        }).toList();

        return PageResponse.<WorkspaceResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPage(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .sortBy(sortBy)
                .order(order)
                .search(search)
                .data(workspaceList)
                .build();
    }

    @PreAuthorize("hasAuthority('VIEW_WORKSPACE')")
    public WorkspaceResponse getOne(String id) {
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WORKSPACE_NOT_EXIST));

        WorkspaceResponse workspaceResponse = workspaceMapper.toWorkspaceResponse(workspace);
        try {
            UserResponse userResponse = identityClient.getUser(workspace.getUserId()).getResult();
            workspaceResponse.setCreator(userResponse.getUsername());
        } catch (FeignException exception) {
            throw new AppException(ErrorCode.USER_NOT_EXIST);
        }
        return workspaceResponse;
    }

    @PreAuthorize("hasAuthority('UPDATE_WORKSPACE')")
    public WorkspaceResponse updateWorkspace(String id, WorkspaceRequest request) {
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WORKSPACE_NOT_EXIST));
        workspaceMapper.updateWorkspace(workspace, request);
        workspaceRepository.save(workspace);

        WorkspaceResponse workspaceResponse = workspaceMapper.toWorkspaceResponse(workspace);
        try {
            UserResponse userResponse = identityClient.getUser(workspace.getUserId()).getResult();
            workspaceResponse.setCreator(userResponse.getUsername());
        } catch (FeignException exception) {
            throw new AppException(ErrorCode.USER_NOT_EXIST);
        }

        return workspaceResponse;
    }

    @PreAuthorize("hasAuthority('DELETE_WORKSPACE')")
    public String deleteWorkspace(String id) {
        workspaceRepository.delete(workspaceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WORKSPACE_NOT_EXIST)));
        return "Workspace has been successfully deleted.";
    }
}
