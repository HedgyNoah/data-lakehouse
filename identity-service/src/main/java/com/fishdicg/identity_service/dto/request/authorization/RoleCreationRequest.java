package com.fishdicg.identity_service.dto.request.authorization;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleCreationRequest {
    String name;
    String description;
}
