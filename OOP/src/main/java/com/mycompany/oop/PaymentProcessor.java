package com.mycompany.oop;

import java.util.Scanner;

public class PaymentProcessor {

    public static void processPayment(Sneaker item, User user, Scanner scanner) {
        System.out.print("Enter the quantity of " + item.toString() + " you want to purchase: ");
        int quantity;
        try {
            quantity = Integer.parseInt(scanner.nextLine().trim());
            if (quantity <= 0) {
                System.out.println("## Invalid quantity. Please enter a number greater than 0. ##");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("## Invalid input. Please enter a valid number. ##");
            return;
        }

        double totalPrice = item.getPrice() * quantity;
       
        displayLoyaltyPointsBalance(user);
        Redemption.displayRedemptionOptions(user, scanner, totalPrice, quantity, item.toString());

        
    }

public static void processCartPayment(Cart cart, User user, Scanner scanner) {
    double totalPrice = cart.getTotalCost();
    
    displayLoyaltyPointsBalance(user);
    Redemption.displayRedemptionOptions2(user, cart, scanner, totalPrice); 

}



    private static void displayLoyaltyPointsBalance(User user) {
        System.out.println("Loyalty Points Balance: " + user.getPoints() + " (1 point = RM10)"); // Display loyalty points balance
    }
}
