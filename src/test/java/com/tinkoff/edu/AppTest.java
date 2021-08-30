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
    private static final int ANY_REQUEST_ID = 20;
    private static final int ANY_RESPONSE_ID = 30;

    @BeforeEach
    public void init() {
        request = new LoanRequest(LoanType.IP, 3, 500);
        response = new LoanResponse(LoanResponseType.APPROVED);
        loanCalcControllerForTestGetIncrementedId = new LoanCalcController
                (new VariableLoanCalcRepository(ANY_REQUEST_ID, ANY_RESPONSE_ID));
        loanCalcControllerForTestGetId1 = new LoanCalcController(new VariableLoanCalcRepository());
    }

    @Test
    public void shouldGetId1WhenFirstCall(){
        int requestId = loanCalcControllerForTestGetId1.createRequest(request);
        int responseId = loanCalcControllerForTestGetId1.createResponse(response);
        assertEquals(1, requestId);
        assertEquals(2, responseId);
    }

    @Test
    public void shouldGetIncrementedIdWhenAnyCall(){
        int requestId = loanCalcControllerForTestGetIncrementedId.createRequest(request);
        int responseId = loanCalcControllerForTestGetIncrementedId.createResponse(response);
        assertEquals(ANY_REQUEST_ID+1, requestId);
        assertEquals(ANY_RESPONSE_ID+2, responseId);
    }
}
