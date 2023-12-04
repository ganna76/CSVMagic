package org.steven;

import java.io.InputStream;
import java.util.Properties;

public class InterviewAppProperties {

    private Properties properties;

    public InterviewAppProperties(){
        this.properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                System.err.println("Cannot load application.properties file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCSVFilePath() {
        return properties.getProperty("path.to.file");
    }

    public String getURLForGetCustomers() {
        return properties.getProperty("url.for.get.customers");
    }

    public String getURLForPostCustomers() {
        return properties.getProperty("url.for.post.customers");
    }

    public String getURLForGetCustomerByRef() {
        return properties.getProperty("url.for.get.customers.by.ref");
    }
}
