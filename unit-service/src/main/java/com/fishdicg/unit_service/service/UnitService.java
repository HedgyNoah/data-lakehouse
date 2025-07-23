package com.fishdicg.unit_service.service;

import com.fishdicg.unit_service.dto.PageResponse;
import com.fishdicg.unit_service.dto.request.UnitRequest;
import com.fishdicg.unit_service.dto.response.UnitResponse;
import com.fishdicg.unit_service.dto.response.UserResponse;
import com.fishdicg.unit_service.entity.Unit;
import com.fishdicg.unit_service.exception.AppException;
import com.fishdicg.unit_service.exception.ErrorCode;
import com.fishdicg.unit_service.mapper.UnitMapper;
import com.fishdicg.unit_service.repository.UnitRepository;
import com.fishdicg.unit_service.repository.httpclient.IdentityClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UnitService {
    UnitMapper unitMapper;
    UnitRepository unitRepository;
    IdentityClient identityClient;

    public UnitResponse createUnit(UnitRequest request) {
        Unit unit = unitMapper.toUnit(request);
        return unitMapper.toUnitResponse(unitRepository.save(unit));
    }

    public UnitResponse updateUnit(String unitId, UnitRequest request) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new AppException(ErrorCode.UNIT_NOT_EXIST));
        unitMapper.updateUnit(unit, request);

        return unitMapper.toUnitResponse(unitRepository.save(unit));
    }

    public String deleteUnit(String unitId) {
        unitRepository.deleteById(unitId);
        return "Unit has been deleted";
    }

    public PageResponse<UnitResponse> getAllUnits(int page, int size, String sortBy,
                                                  String order, String search) {
        Sort sort = "asc".equalsIgnoreCase(order) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<Unit> pageData;
        if(search != null && !search.isEmpty())
            pageData = unitRepository.findByNameContaining(search, pageable);
        else
            pageData = unitRepository.findAll(pageable);

        List<UnitResponse> unitResponseList = pageData.getContent().stream().map(unit -> {
            var unitResponse = unitMapper.toUnitResponse(unit);

            try{
                List<UserResponse> userResponseList = unit.getUserId().stream().map(userId -> {
                    return identityClient.getUser(userId).getResult();
                }).limit(5).toList();
                unitResponse.setUsers(userResponseList);
            } catch (FeignException exception) {
                throw new AppException(ErrorCode.USER_NOT_EXIST);
            }
            return unitResponse;
        }).toList();

        return PageResponse.<UnitResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPage(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .sortBy(sortBy)
                .order(order)
                .search(search)
                .data(unitResponseList)
                .build();
    }

    public UnitResponse getUnit(String unitId) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new AppException(ErrorCode.UNIT_NOT_EXIST));
        UnitResponse unitResponse = unitMapper.toUnitResponse(unit);

        try{
            List<UserResponse> userResponseList = unit.getUserId().stream().map(userId -> {
                return identityClient.getUser(userId).getResult();
            }).toList();
            unitResponse.setUsers(userResponseList);
        } catch (FeignException exception) {
            throw new AppException(ErrorCode.USER_NOT_EXIST);
        }

        return unitResponse;
    }
}