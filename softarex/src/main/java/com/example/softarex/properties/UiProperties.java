package com.example.softarex.properties;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Validated
@ConfigurationProperties("server-ui")
@Getter
@Setter
public class UiProperties {

    @NotBlank
    private String url;
}
