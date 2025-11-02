package com.sabre.hotelbooker.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    public static String getProperty(String key) {
        if(properties == null) {
         properties = new Properties();
        String env = System.getProperty("env", "DEV").toUpperCase();
        String propFile = String.format("src/test/resources/env/%s.properties", env);
        try (FileInputStream fis = new FileInputStream(propFile)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Could not load properties file for environment: " + env + " (" + propFile + ")", e);
        }
    }
        return properties.getProperty(key);
    }
}