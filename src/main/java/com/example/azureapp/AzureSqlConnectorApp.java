package com.example.azureapp;

import com.example.azureapp.config.SecretManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
public class AzureSqlConnectorApp {

    public static void main(String[] args) {
        SpringApplication.run(AzureSqlConnectorApp.class, args);
    }

    @PostConstruct
    public void init() {
        try {
            SecretManager sm = new SecretManager();
            String username = sm.getSecret("sql-username");
            String password = sm.getSecret("sql-password");
            String dbUrl = "jdbc:sqlserver://userappsqlserver.database.windows.net:1433;"
                         + "database=userapp-sqldb-dev;encrypt=true;trustServerCertificate=false;loginTimeout=30;";

            Connection conn = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("✅ Connected to Azure SQL DB successfully!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

