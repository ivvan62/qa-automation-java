package com.tinkoff.edu.test;

import com.tinkoff.edu.app.LoanCalcController;
import com.tinkoff.edu.app.LoanRequest;
import com.tinkoff.edu.app.LoanType;

/**
 * Loan Calc Tests!
 */
public class LoanCalcTest {
    public static void main (String... args){
        LoanRequest request = new LoanRequest(LoanType.IP, 3,500);
        LoanCalcController loanCalcController = new LoanCalcController();
        int requestId = loanCalcController.createRequest(request);
        System.out.println("id = " + requestId);
    }
}
