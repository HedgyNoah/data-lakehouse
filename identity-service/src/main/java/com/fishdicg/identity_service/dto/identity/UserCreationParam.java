package com.fishdicg.identity_service.dto.identity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationParam {
    String firstName;
    String lastName;
    String email;
    String username;

    boolean enabled;
    boolean emailVerified;

    List<Credential> credentials;
}
