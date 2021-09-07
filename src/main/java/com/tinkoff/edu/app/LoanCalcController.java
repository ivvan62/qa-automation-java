package com.tinkoff.edu.app;

import java.util.Random;
import java.util.UUID;

public class LoanCalcController {
    private LoanCalcService loanCalcService;
    private String uuid;
    private LoanRequest[] loanRequest;

    public LoanCalcController(LoanCalcRepository repo) {
        loanCalcService = new LoanCalcService(repo);
    }

    public LoanResponse createRequest(LoanRequest request) {
        if (request.getType() == null) {
            return new LoanResponse(LoanResponseType.DECLINED, -1);
        }
        if (!(request.getMonths() <= 0) && !(request.getAmount() <= 0)) {
            switch (request.getType()) {
                default:
                case PERSON:
                    if ((request.getAmount() <= 10_000.0d) && (request.getMonths() <= 12)) {
                        return new LoanResponse(LoanResponseType.APPROVED, loanCalcService.createRequest(request));
                    } else if ((request.getAmount() > 10_000.0d) && (request.getMonths() > 12)) {
                        return new LoanResponse(LoanResponseType.DECLINED, loanCalcService.createRequest(request));
                    } else {
                        return new LoanResponse(LoanResponseType.DECLINED, -1);
                    }
                case OOO:
                    if (request.getAmount() <= 10_000.0d) {
                        return new LoanResponse(LoanResponseType.DECLINED, loanCalcService.createRequest(request));
                    } else if ((request.getAmount() > 10_000.0d) && (request.getMonths() < 12)) {
                        return new LoanResponse(LoanResponseType.APPROVED, loanCalcService.createRequest(request));
                    } else {
                        return new LoanResponse(LoanResponseType.DECLINED, loanCalcService.createRequest(request));
                    }
                case IP:
                    return new LoanResponse(LoanResponseType.DECLINED, loanCalcService.createRequest(request));
            }
        } else {
            return new LoanResponse(LoanResponseType.DECLINED, -1);
        }
    }

    public LoanRequest[] createResponse(LoanResponse response) {
        return loanCalcService.createResponse(response);
    }

    public LoanResponseType getResponseTypeForClient(LoanResponse response, String uuid) {
        LoanRequest request = new LoanRequest(uuid, LoanType.IP, 3, 500, "fio");
        LoanCalcController loanCalcController = new LoanCalcController(new UuidArrayLoanCalcRepository());
        response = loanCalcController.createRequest(request);
        loanRequest = loanCalcController.createResponse(response);
        LoanResponseType saveResponseTypeStatus = LoanResponseType.DECLINED;
        for (LoanRequest value : loanRequest) {
            if (uuid.equals(value.getUuid())) {
                saveResponseTypeStatus = value.getLoanResponseType();
                break;
            }
        }
        return saveResponseTypeStatus;
    }

    public LoanResponseType setResponseTypeForManager(LoanResponse response, String uuid) {
        LoanRequest request = new LoanRequest(uuid, LoanType.IP, 3, 500, "fio");
        LoanCalcController loanCalcController = new LoanCalcController(new UuidArrayLoanCalcRepository());
        response = loanCalcController.createRequest(request);
        loanRequest = loanCalcController.createResponse(response);
        int indexOfArrayWithResponseType = 0;
        for (int i = 0; i < loanRequest.length; i++) {
            if (uuid.equals(loanRequest[i].getUuid())) {
                indexOfArrayWithResponseType = i;
                break;
            }
        }
        LoanRequest result = loanRequest[indexOfArrayWithResponseType].setLoanResponseType(LoanResponseType.APPROVED);
        return result.getLoanResponseType();
    }
}
