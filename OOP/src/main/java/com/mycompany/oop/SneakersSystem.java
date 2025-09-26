package com.mycompany.oop;

import java.util.List;
import java.util.Scanner;

public class SneakersSystem {

    private static Cart cart;

    public static void main(String args[]) {
        try (Scanner scanner = new Scanner(System.in)) {
            Registration registration = new Registration();
            List<User> users = registration.getUsers();
            cart = new Cart();
            SneakerManager sneakerManager = new SneakerManager();

            System.out.println("=========================================");
            System.out.println("Welcome to S.T.J Sneakers Shop !");
            System.out.println("                                                                                        \n"
                    + "                                                    ,-.                                 \n"
                    + "                                                ,--/ /|                                 \n"
                    + "                 ,---,                        ,--. :/ |             __  ,-.             \n"
                    + "  .--.--.    ,-+-. /  |                       :  : ' /            ,' ,'/ /|  .--.--.    \n"
                    + " /  /    '  ,--.'|'   |   ,---.     ,--.--.   |  '  /      ,---.  '  | |' | /  /    '   \n"
                    + "|  :  /`./ |   |  ,\"' |  /     \\   /       \\  '  |  :     /     \\ |  |   ,'|  :  /`./   \n"
                    + "|  :  ;_   |   | /  | | /    /  | .--.  .-. | |  |   \\   /    /  |'  :  /  |  :  ;_     \n"
                    + " \\  \\    `.|   | |  | |.    ' / |  \\__\\/: . . '  : |. \\ .    ' / ||  | '    \\  \\    `.  \n"
                    + "  `----.   \\   | |  |/ '   ;   /|  ,\" .--.; | |  | ' \\ \\'   ;   /|;  : |     `----.   \\ \n"
                    + " /  /`--'  /   | |--'  '   |  / | /  /  ,.  | '  : |--' '   |  / ||  , ;    /  /`--'  / \n"
                    + "'--'.     /|   |/      |   :    |;  :   .'   \\;  |,'    |   :    | ---'    '--'.     /  \n"
                    + "  `--'---' '---'        \\   \\  / |  ,     .-./'--'       \\   \\  /            `--'---'   \n"
                    + "                         `----'   `--`---'                `----'             ");
            System.out.println("=========================================");
            System.out.println();
            System.out.println("[[ Your one-stop shop for all things sneakers! ]] ");
            System.out.println();
            System.out.println();

            boolean exit = false;
            while (!exit) {
                System.out.println("Are you a member?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.println("3. Exit");
                System.out.print("\nChoose an option: ");

                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("\n || Invalid option. Please enter 1, 2, or 3. ;( ||  " );
                    continue;
                }

                switch (choice) {
                    case 1:
                        Login login = new Login(users);
                        User loggedInUser = login.authenticate(scanner);
                        if (loggedInUser != null) {
                            displayUserInfo(loggedInUser);
                            displayMenu(loggedInUser, sneakerManager, scanner);
                        }
                        break;
                    case 2:
                        registration.register(scanner);
                        break;
                    case 3:
                        exit = true;
                        break;
                    default:
                        System.out.println("\n || Invalid choice. Please choose a valid option. ;( || ");
                        break;
                }

                System.out.println();
            }

            System.out.println("Thank you for visiting!");
        }
    }

    private static void displayUserInfo(User user) {
        System.out.println("\n=========================================");
        System.out.println("Member Information:");
        System.out.println("Name: " + user.getName());
        System.out.println("Contact Number: " + user.getContactNumber());
        System.out.println("Email Address: " + user.getEmailAddress());
        System.out.println("Wallet Balance: $" + user.getWalletBalance());
        System.out.println("Loyalty Points: " + user.getPoints());
        System.out.println();
    }

    private static void displayMenu(User user, SneakerManager sneakerManager, Scanner scanner) {

        boolean exit = false;

        while (!exit) {
            System.out.println("\n=========================================");
            System.out.println("Main Menu:");
            System.out.println("-----------------------------------");
            System.out.println("1. Sneakers Brands");
            System.out.println("2. Point Redemption");
            System.out.println("3. Order History");
            System.out.println("4. Top Up Wallet");
            System.out.println("5. Cart for Payment");
            System.out.println("6. Policy");
            System.out.println("7. Feedback");
            System.out.println("8. Check Your Personal Details ");
            System.out.println("9. Exit");
            System.out.print("\nChoose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("\n || Invalid option. Please enter a number between 1 and 7. ;( ||  ");
                continue;
            }
            
            System.out.println("");
            switch (choice) {
                case 1:
                    displaySneakerBrands(scanner, user, sneakerManager);
                    break;
                case 2:
                    displayPointRedemption();
                    break;
                case 3:
                    displayOrderHistory(user);
                    break;
                case 4:
                    topUpWallet(user, scanner);
                    break;
                case 5:
                    processCartPayment(cart, user, scanner, sneakerManager);
                    break;
                case 6:
                    displayPolicy();
                    break;
                case 7:
                    reviewFeedback(user);
                    break;
                case 8:
                    displayPersonalDetails(user, scanner);
                case 9:
                    exit = true;
                    break;
                default:
                    System.out.println("|| Invalid choice. Please choose a valid number. ||");
                    break;
            }

            System.out.println();
        }
    }

    private static void displaySneakerBrands(Scanner scanner, User user, SneakerManager sneakerManager) {
        System.out.println("=========================================");
        System.out.println("\nSneakers Brands:");
        System.out.println("---------------------");
        System.out.println("1. Nike");
        System.out.println("2. Adidas");
        System.out.println("3. Puma");
        System.out.println("4. Converse");
        System.out.print("\nChoose a brand that you are interested : ");

        int brandChoice;
        try {
            brandChoice = Integer.parseInt(scanner.nextLine().trim()); // Ensure valid input
        } catch (NumberFormatException e) {
            System.out.println("\n || Invalid input. Please enter a number between 1 and 4. ;( || ");
            return; // Retry if there's a parsing error
        }

        String selectedBrand;
        switch (brandChoice) {
            case 1:
                selectedBrand = "Nike";
                break;
            case 2:
                selectedBrand = "Adidas";
                break;
            case 3:
                selectedBrand = "Puma";
                break;
            case 4:
                selectedBrand = "Converse";
                break;
            default:
                System.out.println("## Invalid choice. Please choose a valid brand. ##");
                return;
        }

        List<Sneaker> selectedBrandSneakers = sneakerManager.getSneakersByBrand(selectedBrand);
        if (selectedBrandSneakers.isEmpty()) {
            System.out.println("\n || No sneakers available for the selected brand. :( ||");
            return;
        }

        displaySneakerModels(selectedBrandSneakers, scanner, user);
    }

    private static void displaySneakerModels(List<Sneaker> sneakers, Scanner scanner, User user) {
        System.out.println("=========================================");
        System.out.println("\nAvailable Sneakers:");
        for (int i = 0; i < sneakers.size(); i++) {
            System.out.println((i + 1) + ". " + sneakers.get(i).toString());
        }

        System.out.print("Choose a sneaker or type 0 to go back: ");

        int modelChoice;
        try {
            modelChoice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("\n || Invalid input. Please enter a number between 0 and " + sneakers.size() + " ;( ||" );
            return;
        }

        if (modelChoice == 0) {
            return;
        }

        if (modelChoice < 1 || modelChoice > sneakers.size()) {
            System.out.println("\n || Invalid choice. Please choose a valid sneaker. ;( ||");
            return;
        }

        Sneaker selectedSneaker = sneakers.get(modelChoice - 1);
        displaySneakerActions(selectedSneaker, scanner, user);
    }

    private static void displaySneakerActions(Sneaker sneaker, Scanner scanner, User user) {
        System.out.println("=========================================");
        System.out.println("\nSneaker Details: " + sneaker.toString());
        System.out.println("\n------------------------------");
        System.out.println("---------------------");
        System.out.println("| 1. Add to Cart    |");
        System.out.println("| 2. Direct Payment |");
        System.out.println("| 3. Go Back        |");
        System.out.println("---------------------");
        System.out.print("\nChoose an action: ");

        int actionChoice;
        try {
            actionChoice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("\n || Invalid input. Please enter a number between 1 and 3. ;( || ");
            return;
        }

        switch (actionChoice) {
            case 1:
                cart.addSneaker(sneaker);
                System.out.println(sneaker.toString() + " added to cart.");
                break;
            case 2:
                PaymentProcessor.processPayment(sneaker, user, scanner);
                break;
            case 3:
                return;
            default:
                System.out.println("\n || Invalid action. Please choose a valid option. ;( ||");
                break;
        }

    }

    private static void displayOrderHistory(User user) {
        List<String> orderHistory = user.getOrderHistory();
        System.out.println("\nOrder History:");
        if (orderHistory.isEmpty()) {
            System.out.println(" { No orders placed yet. ;( }");
        } else {
            for (String order : orderHistory) {
                System.out.println("  " + order);
            }
        }
    }

    private static void topUpWallet(User user, Scanner scanner) {
        boolean validInput = false;
        double topUpAmount = 0.0;

        while (!validInput) {
            System.out.println("=========================================");
            System.out.println("\nChoose an option to top-up:");
            System.out.println("-----------------");
            System.out.println("1. RM100");
            System.out.println("2. RM500");
            System.out.println("3. RM1000");
            System.out.println("4. RM2000");
            System.out.println("5. RM5000");
            System.out.println("------------------------");
            System.out.print("\n Enter your choice (1-5): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        topUpAmount = 100.0;
                        validInput = true;
                        break;
                    case 2:
                        topUpAmount = 500.0;
                        validInput = true;
                        break;
                    case 3:
                        topUpAmount = 1000.0;
                        validInput = true;
                        break;
                    case 4:
                        topUpAmount = 2000.0;
                        validInput = true;
                        break;
                    case 5:
                        topUpAmount = 5000.0;
                        validInput = true;
                        break;
                    default:
                        System.out.println("\n || Invalid option. Please choose a number between 1 and 5. ;( || ");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n || Invalid input. Please enter a valid number. ;( ||");
            }
        }

        user.addBalance(topUpAmount);
        System.out.println(" !! Top-up successful!! :D \nNew balance: RM" + user.getWalletBalance()) ;
    }

    private static void processCartPayment(Cart cart, User user, Scanner scanner, SneakerManager sneakerManager) {
        // Check if the cart is empty
        if (cart.getSneakers().isEmpty()) {

            System.out.println("\n Returning to the main menu...... ");
            displayMenu(user, sneakerManager, scanner); // Return to the main menu
            return;
        }

        System.out.println(cart.toString());
        System.out.println("\n < Total Cost: $" + cart.getTotalCost() + ">");
        System.out.println();

        // Display cart items with indices
        List<Sneaker> cartItems = cart.getSneakers();
        for (int i = 0; i < cartItems.size(); i++) {
            System.out.println((i + 1) + ". " + cartItems.get(i).toString());
        }

        System.out.println("Do you want to remove any item from the cart?");
        System.out.println("Enter the number of the item to remove, or 0 to proceed with payment:");
        System.out.print("\nYour choice: ");

        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("\n || Invalid input. Please enter a valid number. ;( || ");
            return;
        }

        if (choice == 0) {
            // Proceed with payment
            PaymentProcessor.processCartPayment(cart, user, scanner);
        } else if (choice > 0 && choice <= cartItems.size()) {

            cart.removeSneaker(choice - 1);
            System.out.println("\n Item removed from the cart....");

            processCartPayment(cart, user, scanner, sneakerManager);
        } else {
            System.out.println("\n || Invalid choice. Please enter a number within the range. ;( || ");
        }
    }

    private static void displayPolicy() {
        System.out.println("Policy (Take Note): ");
        System.out.println("Loyalty points expire after 6 months from the date of acquisition.\n"
                + "The company have the ultimate right to make amendments to the redemptions made.\n"
                + "Points redeemed is not refundable. \n");
    }

    private static final Scanner scanner = new Scanner(System.in);

    private static void displayPointRedemption() {
        while (true) {
            System.out.println("==================================================");
            System.out.println("Point Redemption Options:");
            System.out.println("1. 5% Discount - 5 points");
            System.out.println("2. Free Sneaker Cleaning Service - 10 points");
            System.out.println("3. Free Sneaker Customization Service - 20 points");
            System.out.println("4. RM10 Voucher - 30 points");
            System.out.println("5. 20% Discount on Next Purchase - 50 points");
            System.out.println("6. RM200 Voucher - 200 points");
            System.out.println();
            System.out.print("For viewing only , Enter 0 (Exit to the Main Menu) ");

            int optionNumber;
            try {
                optionNumber = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("\n || Invalid input. Please enter a valid number. ;( ||");
                continue; // Ask for input again
            }

            if (optionNumber == 0) {
                // Exit to main menu
                break;
            } else {
                System.out.println("\n || Invalid option. Please enter 0 to exit to the main menu. ;( || ");
            }

        }

    }

    public static void reviewFeedback(User user) {
        List<Feedback> feedbackList = user.getFeedbackList();
        System.out.println("Feedback from user:");
        System.out.println("======================================================");
        for (var feedback : feedbackList) {
            System.out.println("Username: " + feedback.getUsername());
            System.out.println("Feedback: " + feedback.getMessage());
            System.out.println("==================================================");
            user.saveToFile();
        }
    }

    private static void displayPersonalDetails(User user, Scanner scanner) {
        System.out.println("======================");
        System.out.println("Personal Details:");
        System.out.println("Name: " + user.getName());
        System.out.println("Contact Number: " + user.getContactNumber());
        System.out.println("Email Address: " + user.getEmailAddress());
        System.out.println("Wallet Balance: $" + user.getWalletBalance());
        System.out.println("Loyalty Points: " + user.getPoints());
        System.out.println("\n\nFor viewing only, enter any key to return to the Main Menu.");

        // Wait for user input to return to main menu
        System.out.print("\n Press Enter to continue.......");
        scanner.nextLine(); // Wait for the user to press Enter
    }

}
