package com.tinkoff.edu.app;

public class StaticVariableLoanCalcRepository implements LoanCalcRepository {

    private int requestId;
    private int responseId;

    @Override
    public int saveRequest(LoanRequest request) {
        return ++requestId;
    }

    @Override
    public int saveResponse(LoanResponse response) {
        return ++responseId;
    }

}
