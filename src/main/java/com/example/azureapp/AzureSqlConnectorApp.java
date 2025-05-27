package com.example.azureapp;

import com.example.azureapp.config.SecretManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
public class AzureSqlConnectorApp {

    @Autowired
    private SecretManager secretManager;

    @Value("${azure.keyvault.secret.username}")
    private String usernameSecretName;

    @Value("${azure.keyvault.secret.password}")
    private String passwordSecretName;

    @Value("${azure.keyvault.secret.dburl}")
    private String dbUrlSecretName;

    public static void main(String[] args) {
        SpringApplication.run(AzureSqlConnectorApp.class, args);
    }

    @PostConstruct
    public void init() {
        try {
            String username = secretManager.getSecret(usernameSecretName);
            String password = secretManager.getSecret(passwordSecretName);
            String dbUrl = secretManager.getSecret(dbUrlSecretName);

            Connection conn = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("✅ Connected to Azure SQL DB successfully!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
