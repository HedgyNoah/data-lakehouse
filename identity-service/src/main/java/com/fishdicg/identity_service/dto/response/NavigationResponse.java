package com.fishdicg.identity_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NavigationResponse {
    String name;
    String path;
    String icon;
    String permission;
}
