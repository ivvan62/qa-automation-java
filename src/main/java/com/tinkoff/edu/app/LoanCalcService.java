package com.tinkoff.edu.app;

import java.util.UUID;

public class LoanCalcService implements CalcService {
    private LoanCalcRepository repo;

    public LoanCalcService(LoanCalcRepository repo) {
        this.repo = repo;
    }

    @Override
    public int createRequest(LoanRequest request) {
        return  repo.saveRequest(request);
    }

    public LoanRequest[] createResponse(LoanResponse response) {
        return  repo.saveResponse(response);
    }
}
