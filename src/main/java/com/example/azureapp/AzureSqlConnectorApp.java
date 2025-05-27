package com.example.azureapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class AzureSqlConnectorApp {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ✅ Inject values from Key Vault (or fallback)
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPass;

    public static void main(String[] args) {
        SpringApplication.run(AzureSqlConnectorApp.class, args);
    }

    @PostConstruct
    public void runQuery() {
        // ✅ Print resolved values to verify if Key Vault worked
        System.out.println("Resolved DB URL: " + dbUrl);
        System.out.println("DB Username: " + dbUser);
        System.out.println("DB Password: " + dbPass);

        try {
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            System.out.println("✅ Connected! Query result = " + result);
        } catch (Exception e) {
            System.err.println("❌ DB Connection failed:");
            e.printStackTrace();
        }
    }
}
