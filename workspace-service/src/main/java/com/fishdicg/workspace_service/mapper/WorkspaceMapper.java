package com.fishdicg.workspace_service.mapper;

import com.fishdicg.workspace_service.dto.request.WorkspaceRequest;
import com.fishdicg.workspace_service.dto.response.WorkspaceResponse;
import com.fishdicg.workspace_service.entity.Workspace;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WorkspaceMapper {
    @Mapping(source = "createdDate", target = "createdDate")
    WorkspaceResponse toWorkspaceResponse(Workspace workspace);

    void updateWorkspace(@MappingTarget Workspace workspace, WorkspaceRequest request);
}
