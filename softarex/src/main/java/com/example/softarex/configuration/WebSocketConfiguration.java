package com.example.softarex.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.example.softarex.properties.UiProperties;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private final UiProperties uiProperties;

    public WebSocketConfiguration(UiProperties uiProperties) {
        this.uiProperties = uiProperties;
    }

    public void configureMessageBroker(MessageBrokerRegistry confi) {
        confi.enableSimpleBroker("/quest");
        confi.setApplicationDestinationPrefixes("/app");
    }

    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins(uiProperties.getUrl()).withSockJS();
    }
}
