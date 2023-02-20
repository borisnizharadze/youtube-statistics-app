package com.assigment.api.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jks")
public class JksProperties {

    private String issuer;
    private String privateKeyPassphrase;
    private String keyStorePath;
    private String keyAlias;
    private String keyStorePassword;

}
