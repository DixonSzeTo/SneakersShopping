package com.mycompany.oop;

import java.io.*;
import java.util.*;

public class User {
    private final Map<Integer, Boolean> redeemedVouchers;
    private final String username;
    private final String password;
    private final String name;
    private final String contactNumber;
    private final String emailAddress;
    private double walletBalance;
    private final List<Sneaker> inventory;
    private final List<String> orderHistory;
    private List<Feedback> feedbackList;
    private int points;

    public User(String username, String password, String name, String contactNumber, String emailAddress, double walletBalance, int points) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.walletBalance = walletBalance;
        this.inventory = new ArrayList<>();
        this.orderHistory = new ArrayList<>();
        this.points = points;
        this.redeemedVouchers = new HashMap<>();
        this.feedbackList = new ArrayList<>();
        initializeRedeemedVouchers();
    }

    private void initializeRedeemedVouchers() {
        // Initialize redeemed vouchers map
        redeemedVouchers.put(1, false); // 5% discount
        redeemedVouchers.put(2, false); // Free sneaker cleaning service
        redeemedVouchers.put(3, false); // Free sneaker customization service
        redeemedVouchers.put(4, false); // RM10 voucher
        redeemedVouchers.put(5, false); // 20% discount on next purchase
        redeemedVouchers.put(6, false); // Free sneaker of your choice
        redeemedVouchers.put(7, false); // RM200 voucher
    }

    public Map<Integer, Boolean> getRedeemedVouchers() {
        return redeemedVouchers;
    }

    public void redeemVoucher(int voucherNumber) {
        redeemedVouchers.put(voucherNumber, true);
        saveToFile();
    }

    public boolean isVoucherRedeemed(int voucherNumber) {
        return redeemedVouchers.getOrDefault(voucherNumber, false);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
    
    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public double getWalletBalance() {
        return walletBalance;
    }
    
    public void addFeedback(Feedback feedback) {
        feedbackList.add(feedback);
        saveToFile();
    }

    public void addBalance(double amount) {
        this.walletBalance += amount;
    }

    public boolean pay(double amount) {
        if (walletBalance >= amount) {
            walletBalance -= amount;
            return true;
        }
        return false;
    }

    public List<Sneaker> getInventory() {
        return inventory;
    }

    public void addToInventory(Sneaker sneaker) {
        inventory.add(sneaker);
    }

    public List<String> getOrderHistory() {
        return orderHistory;
    }


  public void addToOrderHistory(String purchaseDetails, double amountPaid, boolean voucherRedeemed, String redeemedVoucher) {
    String voucherStatus = voucherRedeemed ? "Voucher Redeemed: " + "(" + redeemedVoucher + ")" : "No Voucher Redeemed";
    String paymentDetails = String.format(" - Amount Paid: $%.2f", amountPaid);
    String orderEntry = purchaseDetails + paymentDetails + " - " + voucherStatus;
    orderHistory.add(orderEntry);
    saveToFile();
}

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
        saveToFile();
    }
    
    public void setWalletBalance(double newBalance) {
        this.walletBalance = newBalance;
    }

    public void redeemPoints(int pointsToRedeem) {
        if (this.points >= pointsToRedeem) {
            this.points -= pointsToRedeem;
            this.walletBalance += pointsToRedeem;
            saveToFile();
        }
    }

    public void displayLoyaltyPoints() {
        System.out.println("Loyalty Points: " + points);
    }

    public void saveToFile() {
        String fileName = "user_data/" + this.username + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println(this.toText());
        } catch (IOException e) {
            System.err.println("!! Error saving user data to file: " + e.getMessage());
        }
    }

    public void deductPoints(int points) {
        this.points -= points;
        if (this.points < 0) {
            this.points = 0;
        }
        saveToFile();
    }

    public String toText() {
        StringBuilder builder = new StringBuilder();
        builder.append(username).append(";")
                .append(password).append(";")
                .append(name).append(";")
                .append(contactNumber).append(";")
                .append(emailAddress).append(";")
                .append(walletBalance).append(";")
                .append(points).append(";")
                .append(String.join(",", orderHistory));
        return builder.toString();
    }

    public static User fromText(String text) {
        String[] parts = text.split(";");
        if (parts.length >= 7) {
            return new User(
                    parts[0],
                    parts[1],
                    parts[2],
                    parts[3],
                    parts[4],
                    Double.parseDouble(parts[5]),
                    Integer.parseInt(parts[6])
            );
        } else {
            return null;
        }
    }
}