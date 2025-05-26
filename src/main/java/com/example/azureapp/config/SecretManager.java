package com.example.azureapp.config;

import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;

public class SecretManager {

    private final SecretClient client;

    public SecretManager() {
        String keyVaultUrl = "https://userregistrationvault.vault.azure.net/";
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

