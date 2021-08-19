package com.tinkoff.edu.app;

public class LoanCalcRepository {

    private int requestId;

    public LoanCalcRepository(int requestId) {
        this.requestId = requestId;
    }


    public int getRequestId() {
        return requestId;
    }

    public int save() {
        return ++requestId;
    }
}
