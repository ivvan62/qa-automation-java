package com.tinkoff.edu.app;

public class LoanCalcService implements LoanCalcServiceInterface {
    private LoanCalcRepository repo;

    public LoanCalcService(LoanCalcRepository repo) {
        this.repo = repo;
    }

    @Override
    public int createRequest(LoanRequest request) {
        return  repo.saveRequest(request);
    }

    public int createResponse(LoanResponse response) {
        return  repo.saveResponse(response);
    }
}
