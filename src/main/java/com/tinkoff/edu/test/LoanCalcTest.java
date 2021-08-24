package com.tinkoff.edu.test;

import com.tinkoff.edu.app.*;

/**
 * Loan Calc Tests!
 */
public class LoanCalcTest {
    public static void main (String... args){
        LoanRequest request = new LoanRequest(LoanType.IP, 3, 500);
        LoanCalcController loanCalcController = new LoanCalcController(new StaticVariableLoanCalcRepository());
        int requestId = loanCalcController.createRequest(request);
        System.out.println("request_id = " + requestId);

        LoanResponse response = new LoanResponse(LoanResponseType.APPROVED);
        int responseId = loanCalcController.createResponse(response);
        System.out.println("response_id = " + responseId);
    }
}
