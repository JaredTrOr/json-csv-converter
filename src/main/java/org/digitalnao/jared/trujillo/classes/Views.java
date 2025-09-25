package org.digitalnao.jared.trujillo.classes;

import org.digitalnao.jared.trujillo.handlers.CsvHandlerFactory;
import org.digitalnao.jared.trujillo.handlers.JsonHandlerFactory;
import org.digitalnao.jared.trujillo.interfaces.CsvHandler;
import org.digitalnao.jared.trujillo.interfaces.JsonHandler;

import java.util.List;
import java.util.Scanner;

/**
 * The Views class provides a console-based interface
 * to read JSON files and optionally convert them to CSV format.
 *
 * This class interacts with JsonHandler and CsvHandler
 * through their respective factories, and manages user input via a menu.
 *
 * Features include:
 * - Reading and displaying JSON object or array content
 * - Converting JSON data into CSV format and saving it to a file
 * - Console-based menu loop for user interaction
 *
 * Example usage:
 * Views view = new Views();
 * view.run();
 */
public class Views {

    /** Scanner instance to read console input from the user. */
    Scanner scanner = new Scanner(System.in);
    /** JSON handler instance created using the factory. */
    JsonHandler jsonHandler = JsonHandlerFactory.createJsonHandler();

    /**
     * Starts the main program loop, displaying a menu to the user
     * and performing actions based on their input until exit.
     */
    public void run() {

        int input;
        do {
            displayMenu();
            input = this.getMenuChoice();

            switch (input) {
                case 1: readJsonFile(); break;
                case 2: readJsonAndConvertToCsv(); break;
                case 3:
                    System.out.println("Bye bye");
                    break;

                default:
                    System.out.println("Invalid option, Please choose 1-3.");
                    break;
            }

        } while(input != 3);

    }

    /**
     * Displays the main application menu with available options:
     * 1. Read JSON file
     * 2. Read JSON file and convert to CSV
     * 3. Exit
     */
    private void displayMenu() {
        System.out.println("\n====================================");
        System.out.println("           JSON CONVERTER");
        System.out.println("====================================");
        System.out.println("1.- Read JSON file");
        System.out.println("2.- Read JSON file and convert to CSV");
        System.out.println("3.- Exit");
        System.out.println("====================================");
        System.out.print("Choose an option (1-3): ");
    }

    /**
     * Reads and validates the user menu choice.
     *
     * @return the chosen menu option as an integer,
     *         or -1 if the input is invalid
     */
    private int getMenuChoice() {
        try {
            return Integer.parseInt(this.scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Prompts the user to enter a JSON file path.
     *
     * @return the entered JSON file path as a string
     */
    private String getJsonFilepath() {
        System.out.println("Enter JSON file path:");
        return this.scanner.nextLine().trim();
    }

    /**
     * Prompts the user to enter the desired CSV file path.
     *
     * @return the entered CSV file path as a string
     */
    private String getCsvNewFilepath() {
        System.out.println("Enter CSV file path:");
        return this.scanner.next().trim();
    }

    /**
     * Reads a JSON file specified by the user and displays its content.
     * If the JSON file contains an array, all objects are printed.
     * If the JSON file contains a single object, that object is printed.
     */
    private void readJsonFile() {
        String filepath = this.getJsonFilepath();

        try {
            if (jsonHandler.isJsonArray(filepath)) {
                List<User> userList = jsonHandler.fromJsonList(filepath, User.class);
                System.out.println("\nJSON LIST CONTENT:");
                userList.forEach(user -> {
                    System.out.println(user.toString());
                });
            } else {
                User user = jsonHandler.fromJson(filepath, User.class);
                System.out.println("\nJSON OBJECT CONTENT:");
                System.out.println(user.toString());
            }
        } catch(Exception e) {
            System.out.println(e);
        }

        System.out.println("Enter to continue...");
        this.scanner.nextLine();
    }

    /**
     * Reads a JSON file and converts its content to a CSV file.
     * If the JSON file contains an array, all objects are written to the CSV file.
     * If the JSON file contains a single object, only that object is written.
     */
    private void readJsonAndConvertToCsv() {

        String filepath = this.getJsonFilepath();

        CsvHandler csvHandler = CsvHandlerFactory.createCsvHandler();
        String csvFilepath = this.getCsvNewFilepath();

        try {
            if (jsonHandler.isJsonArray(filepath)) {
                List<User> userList = jsonHandler.fromJsonList(filepath, User.class);
                csvHandler.writeToCsv(userList, User.class, csvFilepath);
            } else {
                User user = jsonHandler.fromJson(filepath, User.class);
                csvHandler.writeToCsv(user, User.class, csvFilepath);
            }
        } catch(Exception e) {
            System.out.println(e);
        }

        System.out.println("Enter to continue ...");
        this.scanner.nextLine();
        this.scanner.nextLine();
    }

}
