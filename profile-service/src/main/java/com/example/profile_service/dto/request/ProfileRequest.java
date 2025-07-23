package com.example.profile_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileRequest {
    String userId;
    String phone;
    String imgUrl;
    LocalDate dob;
}
