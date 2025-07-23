package com.fishdicg.unit_service.mapper;

import com.fishdicg.unit_service.dto.request.UnitRequest;
import com.fishdicg.unit_service.dto.response.UnitResponse;
import com.fishdicg.unit_service.entity.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UnitMapper {
    Unit toUnit(UnitRequest request);

    UnitResponse toUnitResponse(Unit unit);

    void updateUnit(@MappingTarget Unit unit, UnitRequest request);
}
