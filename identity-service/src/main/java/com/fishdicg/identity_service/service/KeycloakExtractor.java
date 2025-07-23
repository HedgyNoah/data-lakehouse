package com.fishdicg.identity_service.service;

import com.fishdicg.identity_service.dto.identity.TokenExchangeParam;
import com.fishdicg.identity_service.repository.keycloakclient.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KeycloakExtractor {
    IdentityClient identityClient;

    @Value("${idp.client-id}")
    @NonFinal
    String clientId;

    @Value("${idp.client-secret}")
    @NonFinal
    String clientSecret;

    public String extractKeycloakId(ResponseEntity<?> response) {
        String location = response.getHeaders().get("Location").getFirst();
        String[] splited =  location.split("/");
        return splited[splited.length - 1];
    }

    public String extractClientAccessToken() {
        var token = identityClient.exchangeToken(TokenExchangeParam.builder()
                .grant_type("client_credentials")
                .client_id(clientId)
                .client_secret(clientSecret)
                .scope("openid")
                .build());
        return "Bearer " + token.getAccessToken();
    }
}
