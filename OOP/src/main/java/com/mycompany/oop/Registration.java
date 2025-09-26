package com.mycompany.oop;

import java.io.*;
import java.util.*;

public final class Registration {

    private static final String USER_DIR = "user_data/";

    private final List<User> users;

    public Registration() {
        this.users = loadUsers();
    }

    public void register(Scanner scanner) {
        System.out.println("\nStarting registration...");

        //Prompt user to register
        System.out.print("Enter a username to register: ");
        String username = scanner.nextLine().trim();

        //check if username already exist
        if (isUserExists(username)) {
            System.out.println("** This username is already registered. Please log in. ;( **");
            return;
        }

        //Password length and confirmation
        String password;
        while (true) {
            System.out.print("Enter a password (at least 8 characters): ");
            password = scanner.nextLine().trim();

            if (password.length() < 8) {
                System.out.println("** Password must be at least 8 characters long. **");
            } else {
                System.out.print("Confirm your password: ");
                String confirmPassword = scanner.nextLine().trim();

                if (!password.equals(confirmPassword)) {
                    System.out.println("** Passwords do not match. Please try again. **");
                } else {
                    break; // Exit the loop if password is valid and confirmed
                }
            }
        }

        
        String phoneNumber;
        while (true) {
            System.out.print("Enter your contact number (starting with 01 and 11-12 digits): ");
            phoneNumber = scanner.nextLine().trim();

            if (phoneNumber.length() > 12 && phoneNumber.length() < 11) {
                System.out.println("** Invalid phone number format. Phone number must start with 01 and have 11-12 digits. **");
            } else {
                break; // Exit the loop if phone number is valid
            }
        }

        
        String name;
        while (true) {
            System.out.print("Enter your name: ");
            name = scanner.nextLine().trim();

            if (!isValidName(name)) {
                System.out.println("** Invalid name format. Name should not contain digits or special characters. **");
            } else {
                break; // Exit the loop if name is valid
            }
        }

        String emailAddress;
        while (true) {
            System.out.print("Enter your email address: ");
            emailAddress = scanner.nextLine().trim();

            if (!isValidEmail(emailAddress)) {
                System.out.println("** Invalid email format. Email should contain '@' but not at the beginning or end. **");
            } else {
                break; // Exit the loop if email address is valid
            }
        }

        User newUser = new User(username, password, name, phoneNumber, emailAddress, 0.0, 0);

        users.add(newUser);

        System.out.println("Registration successful! :3 \nProceeding to the next step...");

        saveUserToFile(newUser);
    }

    
    //Display all users
    private List<User> loadUsers() {
        File userDir = new File(USER_DIR);
        if (!userDir.exists()) {
            userDir.mkdirs();
        }

        List<User> users = new ArrayList<>();
        File[] userFiles = userDir.listFiles();

        if (userFiles != null) {
            for (File userFile : userFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
                    String line = reader.readLine();
                    if (line != null) {
                        users.add(User.fromText(line));
                    }
                } catch (IOException e) {
                    System.err.println("!! Error loading user data from " + userFile.getName() + ": " + e.getMessage());
                }
            }
        }
        return users;
    }

    
    //Save user info to files
    private void saveUserToFile(User user) {
        File userFile = new File(USER_DIR + user.getUsername() + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            writer.write(user.toText());
        } catch (IOException e) {
            System.err.println("!! Error saving user data: " + e.getMessage());
        }
    }

    
    //Input formats and checking
    private boolean isUserExists(String username) {
        return users.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }
    
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^01\\d{9,10}$");
    }

    private boolean isValidName(String name) {
        return !name.matches(".*\\d.*") && !name.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }

    private boolean isValidEmail(String email) {
        return email.matches("^(?!@)[^@]+@[^@.]+\\.[^@]+$");
    }

    
    //Getter
    public List<User> getUsers() {
        return users;
    }
}
