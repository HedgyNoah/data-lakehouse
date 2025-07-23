package com.fishdicg.identity_service.dto.identity;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Credential {
    String type;

    @Size(min = 8, message = "PASSWORD_SIZE_INVALID")
    String value;

    boolean temporary;
}
