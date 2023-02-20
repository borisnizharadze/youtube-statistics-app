package com.assigment.api.model.properties;

import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Primary
@Configuration
public class RabbitMQProperties extends RabbitProperties {
    private Integer relayPort;

    public Integer getRelayPort() {
        return relayPort;
    }

    public void setRelayPort(Integer relayPort) {
        this.relayPort = relayPort;
    }
}
