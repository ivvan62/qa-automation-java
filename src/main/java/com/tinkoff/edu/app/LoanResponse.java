package com.tinkoff.edu.app;

public class LoanResponse {
    private final LoanResponseType responseType;
    private int responseId;

    public LoanResponse(LoanResponseType responseType) {
        this.responseType = responseType;
    }

    public int save () {
        return ++responseId;
    }

}
