package com.fishdicg.unit_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "UNIT")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "UNIT_ID", unique = true, nullable = false, updatable = false)
    String id;

    @Column(name = "UNIT_NAME")
    String name;

    @Column(name = "USER_ID")
    Set<String> userId;
}
