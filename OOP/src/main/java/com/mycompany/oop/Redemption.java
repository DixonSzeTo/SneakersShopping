package com.mycompany.oop;

import java.util.*;
import java.util.Scanner;

public class Redemption {

    public static Map<Integer, Boolean> redeemedRewards = new HashMap<>();
    private static Cart cart = new Cart(); // Initialize the cart object
    public static Object nextLine;

    static {
        redeemedRewards.put(1, false); // 5% discount
        redeemedRewards.put(2, false); // Free sneaker cleaning service
        redeemedRewards.put(3, false); // Free sneaker customization service
        redeemedRewards.put(4, false); // RM100 voucher
        redeemedRewards.put(5, false); // 20% discount on next purchase
        redeemedRewards.put(6, false); // RM200 voucher
    }

    public static void displayRedemptionOptions(User user, Scanner scanner, double totalPrice, int quantity, String itemDescription) {
        if (user.getPoints() < 5) {
            if (!user.pay(totalPrice)) {
                System.out.println("\n $$ Insufficient balance. Please top-up your wallet. $$  ");
            } else {
                System.out.println("\n !! Payment successful! Thank you for your purchase !! :D");
                System.out.print("\n########################################################") ; 
                System.out.print("Would you like to give us feedback for your purchase? (yes/no): ");
                String feedbackChoice = scanner.nextLine().trim().toLowerCase();
                if (feedbackChoice.equals("yes")) {
                    System.out.print("\n Please provide your feedback: ");
                    String feedbackMessage = scanner.nextLine();
                    Feedback feedback = new Feedback(user.getUsername(), feedbackMessage);
                    user.addFeedback(feedback);
                    user.saveToFile();
                    System.out.println("Thank you for your feedback! :D");
                }
                String purchaseData = "Purchase: " + quantity + "x " + itemDescription + " for RM" + totalPrice;
                user.addToOrderHistory(purchaseData, totalPrice, false, "Empty/Null"); // Updated to true
                addLoyaltyPoints(user, totalPrice); // Add loyalty points after the purchase
            }

            user.saveToFile(); // Save user data to file after the purchase

            System.out.println("++ You have insufficient loyalty points to claim any rewards. ++ ");
            return;
        }

        System.out.println(" \n Do you want to redeem your loyalty points for rewards? (yes/no)");
        String redeemChoice = scanner.nextLine().trim().toLowerCase();
        if (!redeemChoice.equals("yes")) {
            if (!user.pay(totalPrice)) {
                System.out.println("$$ Insufficient balance. Please top-up your wallet. $$");
            } else {
                System.out.println("!! Payment successful! Thank you for your purchase :3 !! ");
                System.out.print("\n########################################################") ; 
                System.out.print("Would you like to give us feedback for your purchase? (yes/no): ");
                String feedbackChoice = scanner.nextLine().trim().toLowerCase();
                if (feedbackChoice.equals("yes")) {
                    System.out.print("\n Please provide your feedback: ");
                    String feedbackMessage = scanner.nextLine();
                    Feedback feedback = new Feedback(user.getUsername(), feedbackMessage);
                    user.addFeedback(feedback);
                    user.saveToFile();
                    System.out.println(" !! Thank you for your feedback !! :3");
                }
                String purchaseData = "Purchase: " + quantity + "x " + itemDescription + " for RM" + totalPrice;
                user.addToOrderHistory(purchaseData, totalPrice, false, "Empty/Null"); // Updated to true
                addLoyaltyPoints(user, totalPrice); // Add loyalty points after the purchase
            }
            System.out.println("~~~ No rewards redeemed.");
            user.saveToFile();
            return;
        }

        System.out.println("Redemption Options:");
        for (Map.Entry<Integer, Boolean> entry : redeemedRewards.entrySet()) {
            int pointsNeeded = getPointsNeeded(entry.getKey());
            if (user.getPoints() >= pointsNeeded) {
                System.out.println(entry.getKey() + ". " + getRedemptionDescription(entry.getKey()) + " - " + pointsNeeded + " points");
            }
        }

        System.out.print(" \n Enter the reward number to redeem (0 to skip): ");
        int rewardNumber;
        try {
            rewardNumber = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(" \n || Invalid input. Please enter a valid number. ;( || ");
            return;
        }

        if (rewardNumber == 0) {
            return;
        }

        if (!redeemedRewards.containsKey(rewardNumber)) {
            System.out.println("\n || Invalid reward number. Please choose a valid option. ;( || ");
            return;
        }

        int pointsNeeded = getPointsNeeded(rewardNumber);
        if (user.getPoints() >= pointsNeeded) {
            redeemReward(user, rewardNumber, totalPrice, quantity, itemDescription);
        } else {
            System.out.println("\n ++ You do not have enough points to claim this reward. >:( , so sad ~~~~~~ ++  ");
        }
    }

    private static int getPointsNeeded(int rewardNumber) {
        switch (rewardNumber) {
            case 1:
                return 5;
            case 2:
                return 10;
            case 3:
                return 20;
            case 4:
                return 30;
            case 5:
                return 50;
            case 6:
                return 200;
            default:
                return 0;
        }
    }

    private static String getRedemptionDescription(int rewardNumber) {
        switch (rewardNumber) {
            case 1:
                return "5% Discount ";
            case 2:
                return "Free Sneaker Cleaning Service";
            case 3:
                return "Free Sneaker Customization Service";
            case 4:
                return "RM100 Voucher";
            case 5:
                return "20% Discount ";
            case 6:
                return "RM200 Voucher";
            default:
                return "";
        }
    }

    public static double redeemReward(User user, int rewardNumber, double totalPrice, int quantity, String itemDescription) {
        int pointsNeeded = getPointsNeeded(rewardNumber);
        user.deductPoints(pointsNeeded);
        redeemedRewards.put(rewardNumber, true);
        String orderDescription = "";

        switch (rewardNumber) {
            case 1:
                if (user.getWalletBalance() >= totalPrice) {
                    totalPrice *= 0.95;
                    user.setWalletBalance(user.getWalletBalance() - totalPrice);
                    System.out.println("\n !! Payment successful! Thank you for your purchase !! :)  ");
                    System.out.print("\n########################################################") ; 
                    System.out.print("Would you like to give us feedback for your purchase? (yes/no): ");
                    Scanner scanner = new Scanner(System.in);
                    String feedbackChoice = scanner.nextLine().trim().toLowerCase();
                    if (feedbackChoice.equals("yes")) {
                        System.out.print("\n Please provide your feedback: ");
                        String feedbackMessage = scanner.nextLine();
                        Feedback feedback = new Feedback(user.getUsername(), feedbackMessage);
                        user.addFeedback(feedback);
                        user.saveToFile();
                        System.out.println(" !! Thank you for your feedback !! :D");
                    }

                    String purchaseData = "Purchase: " + quantity + "x " + itemDescription + " for RM" + totalPrice;
                    user.addToOrderHistory(purchaseData, totalPrice, true, "5% Discount"); // Updated to true
                    user.saveToFile(); // Save user data to file after the purchase

                } else {
                    System.out.println("\n $$ Insufficient balance. Please top-up your wallet. $$");
                }
                break;

            // Free sneaker of your choice
            // Add order to history directly
            case 2:

                orderDescription = "Free Sneaker Cleaning Service";
                if (!user.pay(totalPrice)) {
                    System.out.println("\n $$ Insufficient balance. Please top-up your wallet. $$");
                } else {
                    System.out.println("!! Payment successful! Thank you for your purchase !! ;D");
                    System.out.print("\n########################################################") ; 
                    System.out.print("Would you like to give us feedback for your purchase? (yes/no): ");
                    Scanner scanner = new Scanner(System.in);
                    String feedbackChoice = scanner.nextLine().trim().toLowerCase();
                    if (feedbackChoice.equals("yes")) {
                        System.out.print("\n Please provide your feedback: ");
                        String feedbackMessage = scanner.nextLine();
                        Feedback feedback = new Feedback(user.getUsername(), feedbackMessage);
                        user.addFeedback(feedback);
                        user.saveToFile();
                        System.out.println("!! Thank you for your feedback !! :3");
                    }
                    String purchaseData = "Purchase: " + quantity + "x " + itemDescription + " for RM" + totalPrice;
                    user.addToOrderHistory(purchaseData, totalPrice, true, orderDescription); // Updated to true
                }

                break;
            case 3:
                orderDescription ="Free Sneaker Customization Service";
                if (!user.pay(totalPrice)) {
                    System.out.println("\n $$ Insufficient balance. Please top-up your wallet $$");
                } else {
                    System.out.println("!! Payment successful! Thank you for your purchase !! :> ");
                    System.out.print("\n########################################################") ; 
                    System.out.print("Would you like to give us feedback for your purchase? (yes/no): ");
                    Scanner scanner = new Scanner(System.in);
                    String feedbackChoice = scanner.nextLine().trim().toLowerCase();
                    if (feedbackChoice.equals("yes")) {
                        System.out.print("\n Please provide your feedback: ");
                        String feedbackMessage = scanner.nextLine();
                        Feedback feedback = new Feedback(user.getUsername(), feedbackMessage);
                        user.addFeedback(feedback);
                        user.saveToFile();
                        System.out.println("!! Thank you for your feedback !! ^_^");
                    }

                    String purchaseData = "Purchase: " + quantity + "x " + itemDescription + " for RM" + totalPrice;
                    user.addToOrderHistory(purchaseData, totalPrice, true, orderDescription);

                }
                break;

            case 4:
                if (user.getWalletBalance() >= totalPrice) {
                    totalPrice -= 100;
                    user.setWalletBalance(user.getWalletBalance() - totalPrice);
                    System.out.println("!! Payment successful! Thank you for your purchase !! ;D");
                    System.out.print("\n########################################################") ; 
                    System.out.print("Would you like to give us feedback for your purchase? (yes/no): ");
                    Scanner scanner = new Scanner(System.in);
                    String feedbackChoice = scanner.nextLine().trim().toLowerCase();
                    if (feedbackChoice.equals("yes")) {
                        System.out.print("\n Please provide your feedback: ");
                        String feedbackMessage = scanner.nextLine();
                        Feedback feedback = new Feedback(user.getUsername(), feedbackMessage);
                        user.addFeedback(feedback);
                        user.saveToFile();
                        System.out.println("!! Thank you for your feedback !! ;D ");
                    }
                    String purchaseData = "Purchase: " + quantity + "x " + itemDescription + " for RM" + totalPrice;
                    user.addToOrderHistory(purchaseData, totalPrice, true, "RM100 off"); // Updated to true
                    user.saveToFile(); // Save user data to file after the purchase
                } else {
                    System.out.println("\n $$ Insufficient balance. Please top-up your wallet. $$ ");
                }
                break;
            case 5:
                if (user.getWalletBalance() >= totalPrice) {
                    totalPrice *= 0.8;
                    user.setWalletBalance(user.getWalletBalance() - totalPrice);
                    System.out.println("!! Payment successful! Thank you for your purchase !! ;D");
                    System.out.print("\n########################################################") ; 
                    System.out.print("Would you like to give us feedback for your purchase? (yes/no): ");
                    Scanner scanner = new Scanner(System.in);
                    String feedbackChoice = scanner.nextLine().trim().toLowerCase();
                    if (feedbackChoice.equals("yes")) {
                        System.out.print("\n Please provide your feedback: ");
                        String feedbackMessage = scanner.nextLine();
                        Feedback feedback = new Feedback(user.getUsername(), feedbackMessage);
                        user.addFeedback(feedback);
                        user.saveToFile();
                        System.out.println("!! Thank you for your feedback !! :D ");
                    }
                    String purchaseData = "Purchase: " + quantity + "x " + itemDescription + " for RM" + totalPrice;
                    user.addToOrderHistory(purchaseData, totalPrice, true, "20% Discount "); // Updated to true
                    user.saveToFile(); // Save user data to file after the purchase
                } else {
                    System.out.println(" $$ Insufficient balance. Please top-up your wallet $$");
                }
                break;
            case 6:
                if (user.getWalletBalance() >= totalPrice) {
                    totalPrice -= 200;
                    user.setWalletBalance(user.getWalletBalance() - totalPrice);
                    System.out.println("!! Payment successful! Thank you for your purchase !! ;D ");
                    System.out.print("\n########################################################") ; 
                    System.out.print("Would you like to give us feedback for your purchase? (yes/no): ");
                    Scanner scanner = new Scanner(System.in);
                    String feedbackChoice = scanner.nextLine().trim().toLowerCase();
                    if (feedbackChoice.equals("yes")) {
                        System.out.print("\n Please provide your feedback: ");
                        String feedbackMessage = scanner.nextLine();
                        Feedback feedback = new Feedback(user.getUsername(), feedbackMessage);
                        user.addFeedback(feedback);
                        user.saveToFile();
                        System.out.println("!! Thank you for your feedback !! ;D");
                    }
                    String purchaseData = "Purchase: " + quantity + "x " + itemDescription + " for RM" + totalPrice;
                    user.addToOrderHistory(purchaseData, totalPrice, true, "RM200 off"); // Updated to true
                    user.saveToFile(); // Save user data to file after the purchase
                } else {
                    System.out.println(" \n $$ Insufficient balance. Please top-up your wallet. $$ ");
                }
                break;
            default:
                break;
        }

        return 0;
    }

    private static void addLoyaltyPoints(User user, double purchaseAmount) {
        int loyaltyPoints = (int) (purchaseAmount / 10); // 1 loyalty point for every $10 spent
        user.addPoints(loyaltyPoints); // Grant loyalty points to the user
    }

    public static void displayRedemptionOptions2(User user, Cart cart, Scanner scanner, double totalPrice) {
        {

            if (cart == null) {
                System.out.println("\n ~~~ Cart is empty.");
                return;
            }

            if (user.getPoints() < 5) {
                if (!user.pay(totalPrice)) {
                    System.out.println("$$ Insufficient balance. Please top-up your wallet. $$");
                } else {
                    System.out.println("!! Payment successful! Thank you for your purchase !! ;D");
                    System.out.print("\n########################################################") ; 
                    System.out.print("Would you like to give us feedback for your purchase? (yes/no): ");
                    String feedbackChoice = scanner.nextLine().trim().toLowerCase();
                    if (feedbackChoice.equals("yes")) {
                        System.out.print("\nPlease provide your feedback: ");
                        String feedbackMessage = scanner.nextLine();
                        Feedback feedback = new Feedback(user.getUsername(), feedbackMessage);
                        user.addFeedback(feedback);
                        user.saveToFile();
                        System.out.println("!! Thank you for your feedback !! ;D ");
                    }
                    // Add each cart item to order history
                    for (Sneaker sneaker : cart.getSneakers()) {
                        String purchaseData = "Purchase: 1x " + sneaker.toString() + " for RM" + sneaker.getPrice();
                        user.addToOrderHistory(purchaseData, totalPrice, false, "Empty/Null");
                    }
                    addLoyaltyPoints(user, totalPrice);

                }
                user.saveToFile();
                System.out.println("\n ++ You have insufficient loyalty points to claim any rewards. ++ ");
                cart.clear();
                return;
            }

            System.out.println("\n Do you want to redeem your loyalty points for rewards? (yes/no)");
            String redeemChoice = scanner.nextLine().trim().toLowerCase();
            if (!redeemChoice.equals("yes")) {
                if (!user.pay(totalPrice)) {
                    System.out.println(" $$ Insufficient balance. Please top-up your wallet. $$");
                } else {
                    for (Sneaker sneaker : cart.getSneakers()) {
                        String purchaseData = "Purchase: 1x " + sneaker.toString() + " for RM" + sneaker.getPrice();
                        user.addToOrderHistory(purchaseData, totalPrice, false, "Empty/Null");
                    }
                    addLoyaltyPoints(user, totalPrice);
                }
                user.saveToFile();
                System.out.println("~~~~~ No rewards redeemed.");
                cart.clear();
                return;
            }

            System.out.println("Redemption Options:");
            for (Map.Entry<Integer, Boolean> entry : redeemedRewards.entrySet()) {
                int pointsNeeded = getPointsNeeded(entry.getKey());
                if (user.getPoints() >= pointsNeeded) {
                    System.out.println(entry.getKey() + ". " + getRedemptionDescription(entry.getKey()) + " - " + pointsNeeded + " points");
                }
            }

            System.out.print("\n Enter the reward number to redeem (0 to skip): ");
            int rewardNumber;
            try {
                rewardNumber = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("\n || Invalid input. Please enter a valid number. ;( || ");
                return;
            }

            if (rewardNumber == 0) {
                return;
            }

            if (!redeemedRewards.containsKey(rewardNumber)) {
                System.out.println("\n || Invalid reward number. Please choose a valid option. ;( ||");
                return;
            }

            int pointsNeeded = getPointsNeeded(rewardNumber);
            if (user.getPoints() >= pointsNeeded) {
                redeemReward2(user, rewardNumber, totalPrice, cart);
            } else {
                System.out.println("\n  ++ You do not have enough points to claim this reward. >:( , so sad ~~~~~~ ++  ");
            }
        }

    }

    public static double redeemReward2(User user, int rewardNumber, double totalPrice, Cart cart) {

        int pointsNeeded = getPointsNeeded(rewardNumber);
        user.deductPoints(pointsNeeded);
        redeemedRewards.put(rewardNumber, true);

        String orderDescription = ""; // Declare once outside the switch statement

        switch (rewardNumber) {
            case 1:
                if (user.getWalletBalance() >= totalPrice) {
                    totalPrice *= 0.95;
                    user.setWalletBalance(user.getWalletBalance() - totalPrice);
                    System.out.println("!! Payment successful! Thank you for your purchase !! ;D");
                     System.out.print("\n########################################################") ;
                    System.out.print("Would you like to give us feedback for your purchase? (yes/no): ");
                    Scanner scanner = new Scanner(System.in);
                    String feedbackChoice = scanner.nextLine().trim().toLowerCase();
                    if (feedbackChoice.equals("yes")) {
                        System.out.print("\nPlease provide your feedback: ");
                        String feedbackMessage = scanner.nextLine();
                        Feedback feedback = new Feedback(user.getUsername(), feedbackMessage);
                        user.addFeedback(feedback);
                        user.saveToFile();
                        System.out.println("!! Thank you for your feedback !! ;D");
                    }
                    // Add each cart item to order history
                    for (Sneaker sneaker : cart.getSneakers()) {
                        String purchaseData = "Purchase: 1x " + sneaker.toString() + " for RM" + sneaker.getPrice();
                        user.addToOrderHistory(purchaseData, totalPrice, true, "5% Discount"); // Updated to true
                    }
                    user.saveToFile(); // Save user data to file after the purchase
                    cart.clear();
                } else {
                    System.out.println("\n $$ Insufficient balance. Please top-up your wallet. $$");
                }
                break;
            case 2:
                orderDescription = "Free Sneaker Cleaning Service";
                if (!user.pay(totalPrice)) {
                    System.out.println("$$ Insufficient balance. Please top-up your wallet. $$ ");
                } else {
                    System.out.println("!! Payment successful! Thank you for your purchase !! ;D");
                    System.out.print("\n########################################################") ;
                    System.out.print("Would you like to give us feedback for your purchase? (yes/no): ");
                    Scanner scanner = new Scanner(System.in);
                    String feedbackChoice = scanner.nextLine().trim().toLowerCase();
                    if (feedbackChoice.equals("yes")) {
                        System.out.print("\nPlease provide your feedback: ");
                        String feedbackMessage = scanner.nextLine();
                        Feedback feedback = new Feedback(user.getUsername(), feedbackMessage);
                        user.addFeedback(feedback);
                        user.saveToFile();
                        System.out.println("!! Thank you for your feedback !! ;D");
                    }
                    // Add each cart item to order history
                    for (Sneaker sneaker : cart.getSneakers()) {
                        String purchaseData = "Purchase: 1x " + sneaker.toString() + " for RM" + sneaker.getPrice();
                        user.addToOrderHistory(purchaseData, totalPrice, true, orderDescription);
                    }
                }
                break;
            case 3:
                orderDescription = "Free Sneaker Customization Service";
                if (!user.pay(totalPrice)) {
                    System.out.println("$$ Insufficient balance. Please top-up your wallet. $$ ");
                } else {
                    System.out.println("!! Payment successful! Thank you for your purchase !! ;D ");
                    System.out.print("\n########################################################") ;
                    System.out.print("Would you like to give us feedback for your purchase? (yes/no): ");
                    Scanner scanner = new Scanner(System.in);
                    String feedbackChoice = scanner.nextLine().trim().toLowerCase();
                    if (feedbackChoice.equals("yes")) {
                        System.out.print("\n Please provide your feedback: ");
                        String feedbackMessage = scanner.nextLine();
                        Feedback feedback = new Feedback(user.getUsername(), feedbackMessage);
                        user.addFeedback(feedback);
                        user.saveToFile();
                        System.out.println("!! Thank you for your feedback !! ;D");
                    }
                    // Add each cart item to order history
                    for (Sneaker sneaker : cart.getSneakers()) {
                        String purchaseData = "Purchase: 1x " + sneaker.toString() + " for RM" + sneaker.getPrice();
                        user.addToOrderHistory(purchaseData, totalPrice, true, orderDescription);
                    }
                }
                break;
            case 4:
                if (user.getWalletBalance() >= totalPrice) {
                    totalPrice -= 100;
                    user.setWalletBalance(user.getWalletBalance() - totalPrice);
                    System.out.println("!! Payment successful! Thank you for your purchase !! ;D ");
                    System.out.print("\n########################################################") ;
                    System.out.print("Would you like to give us feedback for your purchase? (yes/no): ");
                    Scanner scanner = new Scanner(System.in);
                    String feedbackChoice = scanner.nextLine().trim().toLowerCase();
                    if (feedbackChoice.equals("yes")) {
                        System.out.print("\n Please provide your feedback: ");
                        String feedbackMessage = scanner.nextLine();
                        Feedback feedback = new Feedback(user.getUsername(), feedbackMessage);
                        user.addFeedback(feedback);
                        user.saveToFile();
                        System.out.println("!! Thank you for your feedback !! ;D ");
                    }
                    // Add each cart item to order history
                    for (Sneaker sneaker : cart.getSneakers()) {
                        String purchaseData = "Purchase: 1x " + sneaker.toString() + " for RM" + sneaker.getPrice();
                        user.addToOrderHistory(purchaseData, totalPrice, true, "RM100 off ");
                    }
                    user.saveToFile(); // Save user data to file after the purchase
                    cart.clear();
                } else {
                    System.out.println("\n $$ Insufficient balance. Please top-up your wallet. $$ ");
                }
                break;
            case 5:
                if (user.getWalletBalance() >= totalPrice) {
                    totalPrice *= 0.8;
                    user.setWalletBalance(user.getWalletBalance() - totalPrice);
                    System.out.println("!! Payment successful! Thank you for your purchase. !! ;D");
                     System.out.print("\n########################################################") ;
                    System.out.print("Would you like to give us feedback for your purchase? (yes/no): ");
                    Scanner scanner = new Scanner(System.in);
                    String feedbackChoice = scanner.nextLine().trim().toLowerCase();
                    if (feedbackChoice.equals("yes")) {
                        System.out.print("\n Please provide your feedback: ");
                        String feedbackMessage = scanner.nextLine();
                        Feedback feedback = new Feedback(user.getUsername(), feedbackMessage);
                        user.addFeedback(feedback);
                        user.saveToFile();
                        System.out.println("!! Thank you for your feedback !! ;D ");
                    }
                    // Add each cart item to order history
                    for (Sneaker sneaker : cart.getSneakers()) {
                        String purchaseData = "Purchase: 1x " + sneaker.toString() + " for RM" + sneaker.getPrice();
                        user.addToOrderHistory(purchaseData, totalPrice, true, "20% Discount");
                    }
                    user.saveToFile(); // Save user data to file after the purchase
                    cart.clear();
                } else {
                    System.out.println("$$ Insufficient balance. Please top-up your wallet. $$");
                }
                break;
            case 6:
                if (user.getWalletBalance() >= totalPrice) {
                    totalPrice -= 200;
                    user.setWalletBalance(user.getWalletBalance() - totalPrice);
                    System.out.println("!! Payment successful! Thank you for your purchase !! ;D ");
                     System.out.print("\n########################################################") ;
                    System.out.print("Would you like to give us feedback for your purchase? (yes/no): ");
                    Scanner scanner = new Scanner(System.in);
                    String feedbackChoice = scanner.nextLine().trim().toLowerCase();
                    if (feedbackChoice.equals("yes")) {
                        System.out.print("\n Please provide your feedback: ");
                        String feedbackMessage = scanner.nextLine();
                        Feedback feedback = new Feedback(user.getUsername(), feedbackMessage);
                        user.addFeedback(feedback);
                        user.saveToFile();
                        System.out.println("!! Thank you for your feedback !! ;D ");
                    }
                    // Add each cart item to order history
                    for (Sneaker sneaker : cart.getSneakers()) {
                        String purchaseData = "Purchase: 1x " + sneaker.toString() + " for RM" + sneaker.getPrice();
                        user.addToOrderHistory(purchaseData, totalPrice, true, "RM200 off");
                    }
                    user.saveToFile(); // Save user data to file after the purchase
                    cart.clear();
                } else {
                    System.out.println("$$ Insufficient balance. Please top-up your wallet. $$ ");
                }
                break;
            default:
                break;
        }
        return 0;
    }
}
