package org.example.repository;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnectionProvider {

    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;

    // LLM : "Статический блок инициализации — выполнится ровно ОДИН РАЗ при первом обращении к классу"
    static {
        try {
            // 1. Активація драйвера
            Class.forName("org.postgresql.Driver");

            // 2. Шлях до файлу з параметрами підключення до БД
            Properties props = new Properties();
            try (InputStream input = DbConnectionProvider.class.getClassLoader().getResourceAsStream("settings.properties")) {
                if (input == null) {
                    throw new RuntimeException("Sorry, unable to find settings file");
                }
                props.load(input);
            }
            dbUrl = props.getProperty("db.url");
            dbUser = props.getProperty("db.user");
            dbPassword = props.getProperty("db.password");

        } catch (Exception e) {
            throw new RuntimeException("Initial Database configuration failed.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
