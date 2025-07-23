package com.example.profile_service.controller;

import com.example.profile_service.dto.ApiResponse;
import com.example.profile_service.dto.PageResponse;
import com.example.profile_service.dto.request.ProfileRequest;
import com.example.profile_service.dto.response.ProfileResponse;
import com.example.profile_service.service.ProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileController {
    ProfileService profileService;

    @PostMapping
    ApiResponse<ProfileResponse> createProfile(@RequestBody ProfileRequest request) {
        return ApiResponse.<ProfileResponse>builder()
                .code(201)
                .result(profileService.createProfile(request))
                .build();
    }

    @GetMapping
    ApiResponse<PageResponse<ProfileResponse>> getAllProfiles(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "sortBy", required = false, defaultValue = "username") String sortBy,
            @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
            @RequestParam(value = "search", required = false, defaultValue = "") String search) {
        return ApiResponse.<PageResponse<ProfileResponse>>builder()
                .code(200)
                .result(profileService.getAllProfiles(page, size, sortBy, order, search))
                .build();
    }

    @GetMapping("/{profileId}")
    ApiResponse<ProfileResponse> getProfile(@PathVariable String profileId) {
        return ApiResponse.<ProfileResponse>builder()
                .code(200)
                .result(profileService.getProfile(profileId))
                .build();
    }

    @PutMapping("/{profileId}")
    ApiResponse<ProfileResponse> updateProfile(@PathVariable String profileId,
                                               @RequestBody ProfileRequest request) {
        return ApiResponse.<ProfileResponse>builder()
                .code(200)
                .result(profileService.updateProfile(profileId, request))
                .build();
    }

    @DeleteMapping("/{profileId}")
    ApiResponse<String> deleteProfile(@PathVariable String profileId) {
        return ApiResponse.<String>builder()
                .code(200)
                .result(profileService.deleteProfile(profileId))
                .build();
    }
}
