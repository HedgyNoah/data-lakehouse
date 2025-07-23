package com.example.profile_service.repository.httpclient;

import com.example.profile_service.configuration.AuthenticationRequestInterceptor;
import com.example.profile_service.dto.ApiResponse;
import com.example.profile_service.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "identity-service", url = "${app.services.identity.url}",
        configuration = {AuthenticationRequestInterceptor.class})
public interface IdentityClient {
    @GetMapping("/users/{userId}")
    ApiResponse<UserResponse> getUsser(@PathVariable String userId);
}
