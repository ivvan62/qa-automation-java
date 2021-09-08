package com.tinkoff.edu.app;


public interface LoanCalcRepository {
    int saveRequest(LoanRequest request);
    LoanRequest[] saveResponse(LoanResponse response);
}
