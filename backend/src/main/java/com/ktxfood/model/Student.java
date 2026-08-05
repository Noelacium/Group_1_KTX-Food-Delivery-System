package com.ktxfood.model;

public class Student extends User {
    private String roomAddress;
    private double balance;

    public Student() {
        super();
    }

    public Student(String id, String fullName, String phoneNumber, String roomAddress, double balance) {
        super(id, fullName, phoneNumber);
        this.roomAddress = roomAddress;
        this.balance = balance;
    }

    public String getRoomAddress() {
        return roomAddress;
    }

    public void setRoomAddress(String roomAddress) {
        this.roomAddress = roomAddress;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Polymorphism: override method abstract của User
    @Override
    public String getRole() {
        return "Sinh viên";
    }
}