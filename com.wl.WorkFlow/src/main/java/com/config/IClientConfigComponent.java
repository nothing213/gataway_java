package com.config;

import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class IClientConfigComponent {
    @Bean
    public IClientConfig iClientConfig() {
        return new DefaultClientConfigImpl();
    }
}
