package com.tinkoff.edu.app;

public class LoanCalcService {
    public int createRequest(LoanRequest request) {

        LoanCalcRepository loanCalcRepository = new LoanCalcRepository (20);
        return  loanCalcRepository.save();
    }
}
