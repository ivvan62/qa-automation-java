package com.tinkoff.edu.app;

public class LoanCalcController {

    public int createRequest(LoanRequest request) {
        LoanCalcService loanCalcService = new LoanCalcService();
        return loanCalcService.createRequest(request);
    }
}
