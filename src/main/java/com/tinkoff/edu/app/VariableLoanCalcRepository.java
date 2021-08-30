package com.tinkoff.edu.app;

public class VariableLoanCalcRepository implements LoanCalcRepository {
    private int requestId;
    private int responseId;

    public VariableLoanCalcRepository(int requestId, int responseId) {
        this.responseId = responseId;
        this.requestId = requestId;
    }

    public VariableLoanCalcRepository() {

    }

    @Override
    public int saveRequest(LoanRequest request) {
        return ++requestId;
    }

    @Override
    public int saveResponse(LoanResponse response) {
        return responseId += 2;
    }
}
