package com.fishdicg.workspace_service.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@Document(value = "workspace")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Workspace {
    @MongoId
    String id;
    String userId;
    String name;
    LocalDateTime createdDate;
    String description;
}
