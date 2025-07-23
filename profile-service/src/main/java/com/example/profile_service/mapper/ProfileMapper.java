package com.example.profile_service.mapper;

import com.example.profile_service.dto.request.ProfileRequest;
import com.example.profile_service.dto.response.ProfileResponse;
import com.example.profile_service.dto.response.UserResponse;
import com.example.profile_service.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toProfile(ProfileRequest request);

    ProfileResponse toProfileResponse(Profile profile);

    void updateProfile(@MappingTarget Profile profile, ProfileRequest request);

    void updateProfileResponse(@MappingTarget ProfileResponse profileResponse, UserResponse response);
}
