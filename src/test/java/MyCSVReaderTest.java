import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.steven.Customer;
import org.steven.InterviewAppProperties;
import org.steven.MyCSVReader;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MyCSVReaderTest {

    InterviewAppProperties interviewAppProperties = new InterviewAppProperties();

    @Spy
    MyCSVReader myCSVReader = new MyCSVReader(interviewAppProperties);

    @Test
    void testCSVReading() {

        myCSVReader.processCSV();
        assert(myCSVReader).getListOfCustomers().size() > 0;
    }

    @Test
    public void testReadCsvFile() throws IOException {

        // Create a temporary CSV file with sample data
        Path tempFile = Files.createTempFile("myTempFile", ".csv");
        try (FileWriter writer = new FileWriter(String.valueOf(tempFile))) {
            writer.write("123,Mickey Mouse,1 High Street,Blah,New York,New York State,UK,19028\n");
            writer.write("456,Minnie Mouse,1 High Street,Blah,New York,New York State,UK,19028\n");
        }

        // Sneaky change of the CSV file
        myCSVReader.setCsvFile(String.valueOf(tempFile));

        // Read the temporary CSV file
        List<Customer> customers = myCSVReader.processCSV();

        // Asserts
        assertEquals(2, customers.size()); // Assuming two lines in the file
        assertEquals("Mickey Mouse", customers.get(0).getCustomerName());
    }

}
