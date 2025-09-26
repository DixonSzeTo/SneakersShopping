package com.mycompany.oop;

import java.util.List;
import java.util.Scanner;

public class Login {
    private final List<User> users;

    public Login(List<User> users) {
        this.users = users;
    }

    public User authenticate(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();

        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                System.out.println("Login successful! Welcome, " + username + ".");
                return user;
            }
        }

        System.out.println("** Invalid credentials. Please try again. **");
        return null; 
    }
}