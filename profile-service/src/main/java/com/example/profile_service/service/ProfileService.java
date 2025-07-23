package com.example.profile_service.service;

import com.example.profile_service.dto.PageResponse;
import com.example.profile_service.dto.request.ProfileRequest;
import com.example.profile_service.dto.response.ProfileResponse;
import com.example.profile_service.dto.response.UserResponse;
import com.example.profile_service.entity.Profile;
import com.example.profile_service.exception.AppException;
import com.example.profile_service.exception.ErrorCode;
import com.example.profile_service.mapper.ProfileMapper;
import com.example.profile_service.repository.ProfileRepository;
import com.example.profile_service.repository.httpclient.IdentityClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProfileService {
    ProfileRepository profileRepository;
    ProfileMapper profileMapper;
    IdentityClient identityClient;

    @Value("${file.upload-dir}")
    @NonFinal
    String uploadDir;

    public ProfileResponse createProfile(ProfileRequest request) {
        Profile profile = profileMapper.toProfile(request);
        return profileMapper.toProfileResponse(profileRepository.save(profile));
    }

    public ProfileResponse getProfile(String profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_EXIST));
        ProfileResponse profileResponse = profileMapper.toProfileResponse(profile);

        UserResponse userResponse = identityClient.getUsser(profile.getUserId()).getResult();
        profileMapper.updateProfileResponse(profileResponse, userResponse);
        return profileResponse;
    }

    public PageResponse<ProfileResponse> getAllProfiles(int page, int size, String sortBy,
                                                        String order, String search) {
        Sort sort = "asc".equalsIgnoreCase(order) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<Profile> pageData;
        if(search != null && !search.isEmpty())
            pageData = profileRepository.findByNameContaining(search, pageable);
        else
            pageData = profileRepository.findAll(pageable);

        List<ProfileResponse> profileResponseList = pageData.getContent().stream().map(profile -> {
            var profileResponse = profileMapper.toProfileResponse(profile);

            try{
                UserResponse userResponse = identityClient.getUsser(profile.getUserId()).getResult();
                profileMapper.updateProfileResponse(profileResponse, userResponse);
            } catch (FeignException exception) {
                throw new AppException(ErrorCode.USER_NOT_EXIST);
            }
            return profileResponse;
        }).toList();

        return PageResponse.<ProfileResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPage(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .sortBy(sortBy)
                .order(order)
                .search(search)
                .data(profileResponseList)
                .build();
    }

    public ProfileResponse updateProfile(String profileId, ProfileRequest request) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new AppException(ErrorCode.PROFILE_NOT_EXIST));
        profileMapper.updateProfile(profile, request);

        return profileMapper.toProfileResponse(profileRepository.save(profile));
    }

    public String deleteProfile(String profileId) {
        profileRepository.deleteById(profileId);
        return "Profile has been deleted!";
    }
}
