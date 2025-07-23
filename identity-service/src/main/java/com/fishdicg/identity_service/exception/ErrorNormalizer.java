package com.fishdicg.identity_service.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishdicg.identity_service.dto.identity.KeycloakError;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class ErrorNormalizer {
    private static final Logger log = LoggerFactory.getLogger(ErrorNormalizer.class);
    private ObjectMapper objectMapper;
    private final Map<String, ErrorCode> errorCodeMap;

    public ErrorNormalizer() {
        objectMapper = new ObjectMapper();

        errorCodeMap = new HashMap<>();

        errorCodeMap.put("User exists with same username", ErrorCode.USER_EXISTED);
        errorCodeMap.put("User exists with same email", ErrorCode.EMAIL_EXISTED);
        errorCodeMap.put("User name is missing", ErrorCode.USERNAME_MISSING);
    }

    public AppException handleKeycloakException(FeignException exception) {
        try {
            var response = objectMapper.readValue(exception.contentUTF8(), KeycloakError.class);

            if(Objects.nonNull(response.getErrorMessage())
                    && Objects.nonNull(errorCodeMap.get(response.getErrorMessage()))) {
                return new AppException(errorCodeMap.get(response.getErrorMessage()));
            }
        } catch (JsonProcessingException JsonException) {
            log.info("Can not deserialize content", JsonException);
        }

        return new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }
}
