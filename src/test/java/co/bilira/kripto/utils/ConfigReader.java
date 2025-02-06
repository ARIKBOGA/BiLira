package co.bilira.kripto.utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties PROPERTIES;
    private static final Properties SECRET_DATA_PROPERTIES;
    private static final String PATH = "src/test/resources/properties/config.properties";
    private static final String SECRET_DATA_PATH = "src/test/resources/properties/secret_data.properties";

    static {
        PROPERTIES = new Properties();
        SECRET_DATA_PROPERTIES = new Properties();
        loadProperties();
    }

    /**
     * Loads the properties from the config.properties and secret_data.properties files.
     */
    private static void loadProperties() {

        try {
            // Load the config.properties file
            InputStream input = new FileInputStream(PATH);
            PROPERTIES.load(input);
            input.close();

            // Load the secret_data.properties file
            InputStream secretInput = new FileInputStream(SECRET_DATA_PATH);
            SECRET_DATA_PROPERTIES.load(secretInput);
            secretInput.close();
        } catch (IOException e) {
            // If there is an error while loading the properties, print the stack trace
            e.printStackTrace();
        }

    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static String getSecretValue(String key) {
        return SECRET_DATA_PROPERTIES.getProperty(key);
    }


}
