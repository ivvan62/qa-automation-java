package com.tinkoff.edu.app;

import java.util.Objects;

import static com.tinkoff.edu.app.LoanType.*;

public class LoanCalcController {
    private LoanCalcService loanCalcService;

    public LoanCalcController(LoanCalcRepository repo) {
        loanCalcService = new LoanCalcService(repo);
    }

    public LoanResponse createRequest(LoanRequest request) {
        if (!(request.getMonths() <= 0) && !(request.getAmount() <= 0)) {
            if (request.getType() == PERSON) {
                if ((request.getAmount() <= 10_000.0d) && (request.getMonths() <= 12)) {
                    return new LoanResponse(LoanResponseType.APPROVED, loanCalcService.createRequest(request));
                } else if ((request.getAmount() > 10_000.0d) && (request.getMonths() > 12)) {
                    return new LoanResponse(LoanResponseType.DECLINED, loanCalcService.createRequest(request));
                } else {
                    return new LoanResponse(LoanResponseType.DECLINED, -1);
                }
            } else if (request.getType() == OOO) {
                if (request.getAmount() <= 10_000.0d) {
                    return new LoanResponse(LoanResponseType.DECLINED, loanCalcService.createRequest(request));
                } else if ((request.getAmount() > 10_000.0d) && (request.getMonths() < 12)) {
                    return new LoanResponse(LoanResponseType.APPROVED, loanCalcService.createRequest(request));
                } else {
                    return new LoanResponse(LoanResponseType.DECLINED, loanCalcService.createRequest(request));
                }
            } else if (request.getType() == IP) {
                return new LoanResponse(LoanResponseType.DECLINED, loanCalcService.createRequest(request));
            } else {
                return new LoanResponse(LoanResponseType.DECLINED, -1);
            }
        } else {
            return new LoanResponse(LoanResponseType.DECLINED, -1);
        }

    }

    public int createResponse(LoanResponse response) {
        return loanCalcService.createResponse(response);
    }
}
