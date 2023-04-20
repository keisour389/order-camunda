package com.camunda.demo.config;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.ZeebeClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class CamundaConfig {
    @Value("${zeebe.client.security.plaintext:false}")
    private boolean usePlainText;

    @Value("${zeebe.client.broker.gatewayAddress}")
    private String gatewayAddress;

    @Bean
    ZeebeClientBuilder zeebeClientBuilder() {
        if (usePlainText) {
            return ZeebeClient.newClientBuilder().gatewayAddress(gatewayAddress).usePlaintext();
        } else {
            return ZeebeClient.newClientBuilder().gatewayAddress(gatewayAddress);
        }
    }
}
