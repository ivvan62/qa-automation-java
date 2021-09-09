package com.tinkoff.edu.app;

import java.util.UUID;

public class UuidArrayLoanCalcRepository implements LoanCalcRepository {
    private int requestId;
    private LoanRequest[] arrayLoanRequest;

    public LoanRequest[] getArrayLoanRequest() {
        return arrayLoanRequest;
    }

    public UuidArrayLoanCalcRepository(int requestId) {
        this.requestId = requestId;
    }

    public UuidArrayLoanCalcRepository() {

    }

    @Override
    public int saveRequest(LoanRequest request) {
        return ++requestId;
    }

    @Override
    public LoanRequest[] saveResponse(LoanResponse response) {
        arrayLoanRequest = new LoanRequest[10];
        arrayLoanRequest[0] = new LoanRequest("11111111-1111-1111-1111-111100001111", LoanType.OOO, 11, 13000, "OOO IVANOV IVAN IVAN", LoanResponseType.DECLINED);
        arrayLoanRequest[1] = new LoanRequest("11111111-1111-1111-1111-111122221111", LoanType.PERSON, 15, 1000, "IVANOV IVAN IVAN", LoanResponseType.APPROVED);
        for (int i = 0; i < arrayLoanRequest.length; i++) {
            if (arrayLoanRequest[i] == null) {
                UUID uuid = UUID.randomUUID();
                LoanRequest loanRequest = new LoanRequest(uuid.toString(), LoanType.PERSON, 10, 1000, "OOO IVANOV IVAN IVAN");
                LoanCalcController loanCalcController = new LoanCalcController(new UuidArrayLoanCalcRepository());
                LoanResponse loanResponse = loanCalcController.createRequest(loanRequest);
                arrayLoanRequest[i] = new LoanRequest(uuid.toString(),
                        loanRequest.getType(),
                        loanRequest.getMonths(),
                        loanRequest.getAmount(),
                        loanRequest.getFullName(),
                        loanResponse.getResponseType());
            }
        }
        return arrayLoanRequest;
    }
}
