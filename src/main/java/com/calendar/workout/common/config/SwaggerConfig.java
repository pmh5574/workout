package com.calendar.workout.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "헬스 API 명세서",
                description = "헬스 서비스 API 명세서",
                version = "v1"))
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi openAPI() {
        String[] paths = {"/v1/**"};

        return GroupedOpenApi.builder()
                .group("헬스 API v1")
                .pathsToMatch(paths)
                .build();
    }
}
