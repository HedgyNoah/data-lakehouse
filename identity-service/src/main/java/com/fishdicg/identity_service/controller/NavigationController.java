package com.fishdicg.identity_service.controller;

import com.fishdicg.identity_service.dto.ApiResponse;
import com.fishdicg.identity_service.dto.response.NavigationResponse;
import com.fishdicg.identity_service.service.NavigationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/navigation")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NavigationController {
    NavigationService navigationService;

    @GetMapping
    ApiResponse<List<NavigationResponse>> getAllNavigation() {
        return ApiResponse.<List<NavigationResponse>>builder()
                .result(navigationService.getAll())
                .build();
    }
}
