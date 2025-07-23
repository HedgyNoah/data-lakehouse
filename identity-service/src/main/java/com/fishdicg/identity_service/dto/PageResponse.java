package com.fishdicg.identity_service.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> {
    int currentPage;
    int totalPage;
    int pageSize;
    long totalElements;
    String sortBy;
    String order;
    String search;

    @Builder.Default
    private List<T> data = Collections.emptyList();
}
