package com.example.azureapp.config;

import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SecretManager {

    @Value("${azure.keyvault.url}")
    private String keyVaultUrl;

    private SecretClient client;

    @PostConstruct
    public void init() {
        client = new SecretClientBuilder()
                .vaultUrl(keyVaultUrl)
                .credential(new ManagedIdentityCredentialBuilder().build())
                .buildClient();
    }

    public String getSecret(String secretName) {
        KeyVaultSecret secret = client.getSecret(secretName);
        return secret.getValue();
    }
}
