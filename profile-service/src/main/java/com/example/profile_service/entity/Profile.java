package com.example.profile_service.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Node("profile")
public class Profile {
    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    @Property(value = "PROFILE_ID", readOnly = true)
    String id;

    @Property("USER_ID")
    String userId;

    @Property("USER_PHONE")
    String phone;

    @Property("IMG_URL")
    String imgUrl;

    @Property("DATE_OF_BIRTH")
    LocalDate dob;
}
