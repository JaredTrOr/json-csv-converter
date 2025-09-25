package org.digitalnao.jared.trujillo.classes;

import org.digitalnao.jared.trujillo.handlers.CsvHandlerFactory;
import org.digitalnao.jared.trujillo.handlers.JsonHandlerFactory;
import org.digitalnao.jared.trujillo.interfaces.CsvHandler;
import org.digitalnao.jared.trujillo.interfaces.JsonHandler;

import java.util.List;
import java.util.Scanner;

public class Views {

    Scanner scanner = new Scanner(System.in);
    JsonHandler jsonHandler = JsonHandlerFactory.createJsonHandler();

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

    private int getMenuChoice() {
        try {
            return Integer.parseInt(this.scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private String getJsonFilepath() {
        System.out.println("Enter JSON file path:");
        return this.scanner.nextLine().trim();
    }

    private String getCsvNewFilepath() {
        System.out.println("Enter CSV file path:");
        return this.scanner.next().trim();
    }

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
