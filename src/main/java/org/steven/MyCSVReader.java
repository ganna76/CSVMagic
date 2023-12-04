package org.steven;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyCSVReader {

    @Setter
    private String csvFile;
    private String urlToGetCustomers;
    private String urlToPutCustomers;
    private String urlToGetCustomerByRef;

    @Getter
    private List<Customer> listOfCustomers = new ArrayList<>();

    public MyCSVReader(InterviewAppProperties interviewAppProperties) {

        System.out.println("We will be reading from: " + interviewAppProperties.getCSVFilePath());
        this.csvFile = interviewAppProperties.getCSVFilePath();
        this.urlToGetCustomers = interviewAppProperties.getURLForGetCustomers();
        this.urlToPutCustomers = interviewAppProperties.getURLForPostCustomers();
        this.urlToGetCustomerByRef = interviewAppProperties.getURLForGetCustomerByRef();

    }


    public List<Customer> processCSV() {

        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {

            listOfCustomers = new ArrayList<>();
            List<String[]> linesInFile = reader.readAll();

            for (String[] line : linesInFile) {
                System.out.println("Customer Ref  : " + line[0]);
                System.out.println("Customer Name : " + line[1]);
                System.out.println("Address Line 1: " + line[2]);
                System.out.println("Address Line 2: " + line[3]);
                System.out.println("Town          : " + line[4]);
                System.out.println("County        : " + line[5]);
                System.out.println("Country       : " + line[6]);
                System.out.println("Postcode      : " + line[7]);
                System.out.println("================");

                Customer customer = Customer.builder()
                        .customerRef(line[0])
                        .customerName(line[1])
                        .addressLine1(line[2])
                        .addressLine2(line[3])
                        .town(line[4])
                        .county(line[5])
                        .country(line[6])
                        .postcode(line[7])
                        .build();

                listOfCustomers.add(customer);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }

        return listOfCustomers;
    }

    public void storeCustomers() {

        try {
            URL url = new URL(urlToPutCustomers);

            // Set up the connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            for (Customer customer: listOfCustomers) {

                // Convert POJO to JSON
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonPayload = objectMapper.writeValueAsString(customer);

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonPayload.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Get the response code
                int responseCode = connection.getResponseCode();
                System.out.println("Response Code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getAllCustomers() {
        genericGetCall(urlToGetCustomers);
    }

    public void getCustomerBasedOnID(int idToRetrieve) {
        genericGetCall(urlToGetCustomerByRef + idToRetrieve);
    }

    public void genericGetCall(String getUrl) {
        try {
            URL url = new URL(getUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read the response data
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                System.out.println("Customer List: " + response.toString());
            } else {
                System.out.println("GET request failed");
            }

            // Close the connection
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
