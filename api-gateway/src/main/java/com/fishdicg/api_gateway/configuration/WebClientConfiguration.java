//package com.fishdicg.api_gateway.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.reactive.CorsWebFilter;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.util.List;
//
//@Configuration
//public class WebClientConfiguration {
////    @Bean
////    WebClient webClient() {
////        return WebClient.builder()
////                .baseUrl("http://localhost:8080/identity")
////                .build();
////    }
//
////    @Bean
////    CorsWebFilter corsWebFilter() {
////        CorsConfiguration corsConfiguration = new CorsConfiguration();
////        corsConfiguration.setAllowedOrigins(List.of("*"));
////        corsConfiguration.setAllowedHeaders(List.of("*"));
////        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
////        corsConfiguration.setAllowCredentials(true);
////
////        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
////        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
////
////        return new CorsWebFilter(urlBasedCorsConfigurationSource);
////    }
//}
