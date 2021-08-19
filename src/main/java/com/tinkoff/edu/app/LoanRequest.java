package com.tinkoff.edu.app;

public class LoanRequest {
    private final LoanType type;
    private final int months;
    private final int amount;

    public LoanRequest(LoanType type, int months, int amount) {
        this.type = type;
        this.months = months;
        this.amount = amount;
    }

    public int getMonths() {
        return months;
    }

    public int getAmount() {
        return amount;
    }



}
