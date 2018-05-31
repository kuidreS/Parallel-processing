package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Created by vserdiuk
 */


public class Config {

    final static Logger logger = Logger.getLogger(Config.class);

    public String getPropertyValue(String propertyName) {
        String propertyValue = "";
        Properties properties = new Properties();
        try (InputStream inputStream = this.getClass().getResourceAsStream("/config.properties")) {
            properties.load(inputStream);
            propertyValue = properties.getProperty(propertyName);
        } catch (IOException e) {
            logger.error("Getting config property error: " + e.getMessage());
        }
        return propertyValue;
    }

}
