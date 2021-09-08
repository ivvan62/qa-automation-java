package com.tinkoff.edu.app;

import java.util.UUID;

public class LoanRequest {

    private final LoanType type;
    private final int months;
    private final double amount;
    private final String fullName;
    private String uuid;
    private LoanResponseType loanResponseType;

    public LoanRequest(String uuid, LoanType type, int months, double amount, String fullName, LoanResponseType loanResponseType) {
        this.uuid = uuid;
        this.type = type;
        this.months = months;
        this.amount = amount;
        this.fullName = fullName;
        this.loanResponseType = loanResponseType;
    }

    public LoanRequest(String uuid, LoanType type, int months, double amount, String fullName) {
        this.uuid = uuid;
        this.type = type;
        this.months = months;
        this.amount = amount;
        this.fullName = fullName;
    }

    public LoanRequest(LoanType type, int months, double amount, String fullName) {
        this.type = type;
        this.months = months;
        this.amount = amount;
        this.fullName = fullName;
    }

    public LoanRequest setLoanResponseType(LoanResponseType loanResponseType) {
        this.loanResponseType = loanResponseType;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public int getMonths() {
        return months;
    }

    public double getAmount() {
        return amount;
    }

    public LoanType getType() {
        return type;
    }

    public String getFullName() {
        return fullName;
    }

    public LoanResponseType getLoanResponseType() {
        return loanResponseType;
    }

    @Override
    public String toString() {
        return "LoanRequest{" +
                "type=" + type +
                ", months=" + months +
                ", amount=" + amount +
                ", fullName='" + fullName + '\'' +
                ", uuid=" + uuid +
                ", loanResponseType=" + loanResponseType +
                '}';
    }
}
