package com.fishdicg.identity_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String firstName;
    String lastName;

    @Email(message = "EMAIL_INVALID")
    String email;

    @Size(min = 8, message = "PASSWORD_SIZE_INVALID")
    String password;
}
