package com.fishdicg.identity_service.constant;

import com.fishdicg.identity_service.dto.response.NavigationResponse;

import java.util.List;

public class PredefinedNavigation {
    public static final List<NavigationResponse> NAVIGATION_LIST = List.of(
            NavigationResponse.builder()
                    .name("Quản lý quyền")
                    .path("/roles")
                    .icon("safety")
                    .permission("MANAGE_AUTHORIZATION")
                    .build(),
            NavigationResponse.builder()
                    .name("Quản lý người dùng")
                    .path("/users")
                    .icon("user")
                    .permission("VIEW_USER")
                    .build(),
            NavigationResponse.builder()
                    .name("Quản lý workspace")
                    .path("/workspaces")
                    .icon("desktop")
                    .permission("VIEW_WORKSPACE")
                    .build(),
            NavigationResponse.builder()
                    .name("Quản lý đơn vị")
                    .path("/units")
                    .icon("")
                    .permission("VIEW_UNITS")
                    .build()
    );

    private PredefinedNavigation() {}
}
