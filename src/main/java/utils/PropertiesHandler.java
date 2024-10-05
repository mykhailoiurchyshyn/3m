package utils;

import java.io.*;
import java.util.Properties;

public class PropertiesHandler {

    /**
     * Reads value of the specified property.
     * @param env - specifies the environment
     * @param propertyKey - specifies the property
     * @return {@code String}
     * @throws IOException
     */
    public static String getPropertyValue(Environment env, String propertyKey) {
        String filePath = String.format("%s/src/test/resources/%s.properties",
                System.getProperty("user.dir"), env.toString());
        return readPropertyValue(filePath, propertyKey);
    }

    /**
     * Records specified value to the specified property.
     * @param propertyPath - specifies path to the respective property
     * @param property - specifies the property
     * @param propertyValue - specifies the value which will be recorded as the value of the respective property
     * @throws IOException
     */
    public static void setProperty(String propertyPath, String property, String propertyValue) {
        Properties prop = new Properties();
        try (FileInputStream in = new FileInputStream(new File(propertyPath));
             FileOutputStream out = new FileOutputStream(new File(propertyPath))) {

            prop.load(in);
            prop.setProperty(property, propertyValue);
            prop.store(out, null);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads value of the specified property.
     * @param propertyPath - specifies path to the respective property
     * @param property - specifies the property
     * @return {@code String}
     * @throws IOException
     */
    public static String readPropertyValue(String propertyPath, String property) {
        Properties prop = new Properties();
        try (FileInputStream in = new FileInputStream(propertyPath)) {
            prop.load(in);
            return prop.getProperty(property);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
