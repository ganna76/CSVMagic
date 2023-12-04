package org.steven;

import java.util.Scanner;

public class InterviewConsoleApp {

    public static void main(String[] args) {

        System.out.println("Welcome to CSV reader.");

        InterviewAppProperties properties = new InterviewAppProperties();

        Scanner scanner = new Scanner(System.in);
        MyCSVReader csvReader = new MyCSVReader(properties);

        while (true) {
            showMenu();

            // Read user choice
            int choice = getUserChoice(scanner);

            // Process user choice
            switch (choice) {
                case 1:
                    System.out.println("Reading from CSV file and uploading to DB...");
                    csvReader.processCSV();
                    csvReader.storeCustomers();
                    break;
                case 2:
                    System.out.println("Displaying all customers...");
                    csvReader.getAllCustomers();
                    break;
                case 3:
                    System.out.println("Displaying customer based on customer reference, please enter reference number below");
                    int idToRetrieve = getUserChoice(scanner);
                    csvReader.getCustomerBasedOnID(idToRetrieve);
                    break;
                case 4:
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }




    }

    public static void showMenu(){

        System.out.println("");
        System.out.println("Menu Options:");
        System.out.println("");
        System.out.println("1. Read from CSV file and upload to DB");
        System.out.println("2. Display all customers");
        System.out.println("3. Display customer based on ID");
        System.out.println("4. Exit");
    }

    private static int getUserChoice(Scanner scanner) {
        System.out.print("Enter your choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Consume the invalid input
        }
        return scanner.nextInt();
    }
}