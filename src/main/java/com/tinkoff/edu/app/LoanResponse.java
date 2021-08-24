package com.tinkoff.edu.app;

public class LoanResponse {
    private final LoanResponseType responseType;

    public LoanResponse(LoanResponseType responseType) {
        this.responseType = responseType;
    }

    public LoanResponseType getResponseType() {
        return responseType;
    }
}
