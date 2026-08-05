package com.ktxfood.dto;

public class PaymentRequest {
    private String method; // "WALLET", "CASH", "BANK_TRANSFER"

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
}