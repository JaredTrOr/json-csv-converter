package org.digitalnao.jared.trujillo;

import org.digitalnao.jared.trujillo.handlers.CsvHandlerFactory;
import org.digitalnao.jared.trujillo.handlers.JsonHandlerFactory;
import org.digitalnao.jared.trujillo.classes.User;
import org.digitalnao.jared.trujillo.interfaces.CsvHandler;
import org.digitalnao.jared.trujillo.interfaces.JsonHandler;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        JsonHandler jsonHandler = JsonHandlerFactory.createJsonHandler();
        CsvHandler csvHandler = CsvHandlerFactory.createCsvHandler();

        try {
            User user = jsonHandler.fromJson("user.json", User.class);
            List<User> userList = jsonHandler.fromJsonList("users-list.json", User.class);
            System.out.println(user.toString());

            for (User userFromList : userList) {
                System.out.println(userFromList.toString());
            }

            csvHandler.writeToCsv(user, User.class, "user");
            csvHandler.writeToCsv(userList, User.class, "users");
        } catch(Exception e) {
            System.err.println(e);
        }

    }
}