package com.tinkoff.edu;


import com.tinkoff.edu.app.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private LoanRequest request;
    private LoanResponse response;
    private LoanCalcController loanCalcController;
    private LoanCalcController loanCalcController2;
    private static final int requestIdForTest = 20;
    private static final int responseIdForTest = 30;

    @BeforeEach
    public void init () {
        request = new LoanRequest(LoanType.IP, 3, 500);
        response = new LoanResponse(LoanResponseType.APPROVED);
        loanCalcController = new LoanCalcController(new StaticVariableLoanCalcRepository(requestIdForTest, responseIdForTest));
        loanCalcController2 = new LoanCalcController(new StaticVariableLoanCalcRepository());
    }

    @Test
    public void shouldGetId1WhenFirstCall (){
        int requestId = loanCalcController2.createRequest(request);
        int responseId = loanCalcController2.createResponse(response);
        assertEquals(requestId, 1);
        assertEquals(responseId, 2);
    }

    @Test
    public void shouldGetIncrementedIdWhenAnyCall (){
        int requestId = loanCalcController.createRequest(request);
        int responseId = loanCalcController.createResponse(response);
        assertEquals(requestId, requestIdForTest+1);
        assertEquals(responseId, responseIdForTest+2);
    }

    @Test
    @Disabled
    public void shouldAnswerWithFalse () {
        assertTrue (true);
    }
}
