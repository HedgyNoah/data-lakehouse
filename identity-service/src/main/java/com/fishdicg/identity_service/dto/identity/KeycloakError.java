package com.fishdicg.identity_service.dto.identity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KeycloakError {
    String errorMessage;
}
