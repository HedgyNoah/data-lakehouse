package com.fishdicg.identity_service.mapper;

import com.fishdicg.identity_service.dto.request.UserCreationRequest;
import com.fishdicg.identity_service.dto.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(UserCreationRequest user);
}
