package com.fishdicg.unit_service.controller;

import com.fishdicg.unit_service.dto.ApiResponse;
import com.fishdicg.unit_service.dto.PageResponse;
import com.fishdicg.unit_service.dto.request.UnitRequest;
import com.fishdicg.unit_service.dto.response.UnitResponse;
import com.fishdicg.unit_service.service.UnitService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UnitController {
    UnitService unitService;

    @PostMapping
    ApiResponse<UnitResponse> createUnit(@RequestBody UnitRequest request) {
        return ApiResponse.<UnitResponse>builder()
                .code(201)
                .result(unitService.createUnit(request))
                .build();
    }

    @GetMapping
    ApiResponse<PageResponse<UnitResponse>> getAllUnits(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "sortBy", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
            @RequestParam(value = "search", required = false, defaultValue = "") String search) {
        return ApiResponse.<PageResponse<UnitResponse>>builder()
                .code(200)
                .result(unitService.getAllUnits(page, size, sortBy, order, search))
                .build();
    }

    @GetMapping("/{unitId}")
    ApiResponse<UnitResponse> getUnit(@PathVariable String unitId) {
        return ApiResponse.<UnitResponse>builder()
                .code(200)
                .result(unitService.getUnit(unitId))
                .build();
    }

    @PutMapping("/{unitId}")
    ApiResponse<UnitResponse> updateUnit(@PathVariable String unitId, @RequestBody UnitRequest request) {
        return ApiResponse.<UnitResponse>builder()
                .code(200)
                .result(unitService.updateUnit(unitId, request))
                .build();
    }

    @DeleteMapping("/{unitId}")
    ApiResponse<String> deleteUnit(@PathVariable String unitId) {
        return ApiResponse.<String>builder()
                .code(200)
                .result(unitService.deleteUnit(unitId))
                .build();
    }
}
