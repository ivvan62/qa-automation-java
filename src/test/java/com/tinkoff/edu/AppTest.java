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
    private LoanCalcController loanCalcControllerForTestGetId1;
    private LoanCalcController loanCalcControllerForTestGetIncrementedId;
    private static final int requestIdForTestGetIncrementedId = 20;
    private static final int responseIdForTestGetIncrementedId = 30;

    @BeforeEach
    public void init () {
        request = new LoanRequest(LoanType.IP, 3, 500);
        response = new LoanResponse(LoanResponseType.APPROVED);
        loanCalcControllerForTestGetIncrementedId = new LoanCalcController
                (new StaticVariableLoanCalcRepository(requestIdForTestGetIncrementedId, responseIdForTestGetIncrementedId));
        loanCalcControllerForTestGetId1 = new LoanCalcController(new StaticVariableLoanCalcRepository());
    }

    @Test
    public void shouldGetId1WhenFirstCall (){
        int requestId = loanCalcControllerForTestGetId1.createRequest(request);
        int responseId = loanCalcControllerForTestGetId1.createResponse(response);
        assertEquals(requestId, 1);
        assertEquals(responseId, 2);
    }

    @Test
    public void shouldGetIncrementedIdWhenAnyCall (){
        int requestId = loanCalcControllerForTestGetIncrementedId.createRequest(request);
        int responseId = loanCalcControllerForTestGetIncrementedId.createResponse(response);
        assertEquals(requestId, requestIdForTestGetIncrementedId+1);
        assertEquals(responseId, responseIdForTestGetIncrementedId+2);
    }

    @Test
    @Disabled
    public void shouldAnswerWithFalse () {
        assertTrue (true);
    }
}
