package com.tinkoff.edu.app;

public class LoanCalcController {
    private LoanCalcService loanCalcService;
    private LoanRequest[] loanRequest;

    public LoanCalcController(LoanCalcRepository repo) {
        loanCalcService = new LoanCalcService(repo);
    }

    public LoanResponse createRequest(LoanRequest request) {

        if (request.getFullName() == null || request.getFullName().equals("")) {
            throw new NullPointerException("ФИО Клиента не заполнено");
        }

        if (request.getFullName().length() < 10 || request.getFullName().length() > 100) {
            throw new IllegalArgumentException("Длина ФИО должна быть не менее 10 и не более 100 символов");
        }

        if (!(request.getFullName().matches("^[a-zA-Z -]+"))) {
            throw new IllegalArgumentException("Некорректное ФИО");
        }

        if (request.getUuid() == null || request.getUuid().equals("")) {
            throw new NullPointerException("UUID Клиента не заполнено");
        }

        if (request.getMonths() < 1 || request.getMonths() > 100) {
            throw new IllegalArgumentException("Количество месяцев должно быть не менее 1 и не более 100");
        }

        if (request.getAmount() < 0.01 || request.getAmount() > 999_999.99) {
            throw new IllegalArgumentException("Сумма кредита должна быть не менее 0.01 и не более 999 999.99");
        }

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
    }

    public LoanRequest[] createResponse(LoanResponse response) {
        return loanCalcService.createResponse(response);
    }

    public LoanResponseType getResponseTypeForClient(LoanResponse response, String uuid) {
        LoanRequest request = new LoanRequest(uuid, LoanType.IP, 3, 500, "Ivanfffov Ivan");
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
        LoanRequest request = new LoanRequest(uuid, LoanType.IP, 3, 500, "Ivanfffov Ivan");
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
