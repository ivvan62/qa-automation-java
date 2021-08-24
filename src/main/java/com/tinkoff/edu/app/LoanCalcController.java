package com.tinkoff.edu.app;

public class LoanCalcController {
    private LoanCalcService loanCalcService;

    public LoanCalcController(LoanCalcRepository repo) {
        loanCalcService = new LoanCalcService(repo);
    }

    public int createRequest(LoanRequest request) {
        return loanCalcService.createRequest(request);
    }

    public int createResponse(LoanResponse response) {
        return loanCalcService.createResponse(response);
    }
}
