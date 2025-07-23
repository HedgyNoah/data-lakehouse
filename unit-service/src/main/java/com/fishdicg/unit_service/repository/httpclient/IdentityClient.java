package com.fishdicg.unit_service.repository.httpclient;

import com.fishdicg.unit_service.configuration.AuthenticationRequestInterceptor;
import com.fishdicg.unit_service.dto.ApiResponse;
import com.fishdicg.unit_service.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "identity-service", url = "${app.services.identity.url}",
        configuration = {AuthenticationRequestInterceptor.class})
public interface IdentityClient {
    @GetMapping("/users/{id}")
    ApiResponse<UserResponse> getUser(@PathVariable String id);
}
