package com.tinkoff.edu.app;

import java.util.Objects;

public class LoanResponse {
    private final LoanResponseType responseType;
    private int requestId;

    public LoanResponse(LoanResponseType responseType) {
        this.responseType = responseType;
    }

    public LoanResponse(LoanResponseType responseType, int requestId) {
        this.responseType = responseType;
        this.requestId = requestId;
    }

    public LoanResponseType getResponseType() {
        return responseType;
    }

    public int getRequestId() {
        return requestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
        if (o == null || getClass() != o.getClass()) return false;
        LoanResponse that = (LoanResponse) o;
        return getRequestId() == that.getRequestId() && getResponseType() == that.getResponseType();
    }
}
