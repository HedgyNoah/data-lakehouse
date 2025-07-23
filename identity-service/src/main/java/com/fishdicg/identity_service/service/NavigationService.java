package com.fishdicg.identity_service.service;

import com.fishdicg.identity_service.constant.PredefinedNavigation;
import com.fishdicg.identity_service.dto.response.NavigationResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NavigationService {
    List<NavigationResponse> navigationResponses = PredefinedNavigation.NAVIGATION_LIST;

    public List<NavigationResponse> getAll() {
        var authorities = SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities();

        log.info("authorities: {}", authorities);

        return navigationResponses.stream().filter(
                response -> hasAuthority(authorities, response.getPermission()))
                .map(response -> NavigationResponse.builder()
                        .name(response.getName())
                        .path(response.getPath())
                        .icon(response.getIcon())
                        .permission(response.getPermission())
                        .build())
                .toList();
    }

    private boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String authority) {
        return authorities.stream().anyMatch(auth -> auth.getAuthority().equals(authority));
    }
}
