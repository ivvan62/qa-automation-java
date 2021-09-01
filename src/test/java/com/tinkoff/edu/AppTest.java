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
    private LoanCalcController loanCalcControllerForTestsLoanCalcController;
    private static final int ANY_REQUEST_ID = 20;
    private static final int ANY_RESPONSE_ID = 30;

    @BeforeEach
    public void init() {
        response = new LoanResponse(LoanResponseType.DECLINED);
        request = new LoanRequest(LoanType.IP, 3, 500);
        loanCalcControllerForTestGetIncrementedId =
                new LoanCalcController(new VariableLoanCalcRepository(ANY_REQUEST_ID, ANY_RESPONSE_ID));
        loanCalcControllerForTestsLoanCalcController =
                new LoanCalcController(new VariableLoanCalcRepository(0, 0));
        loanCalcControllerForTestGetId1 = new LoanCalcController(new VariableLoanCalcRepository());
    }

    @Test
    public void shouldGetId1WhenFirstCall() {
        response = loanCalcControllerForTestGetId1.createRequest(request);
        int responseId = loanCalcControllerForTestGetId1.createResponse(response);
        assertEquals(1, response.getRequestId());
        assertEquals(1, responseId);
    }

    @Test
    public void shouldGetIncrementedIdWhenAnyCall() {
        response = loanCalcControllerForTestGetIncrementedId.createRequest(request);
        int responseId = loanCalcControllerForTestGetIncrementedId.createResponse(response);
        assertEquals(ANY_REQUEST_ID + 1, response.getRequestId());
        assertEquals(ANY_RESPONSE_ID + 1, responseId);
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForPersonApproved() {
        request = new LoanRequest(LoanType.PERSON, 5, 2000);
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.APPROVED, 1), loanResponse);
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForPersonDeclined() {
        request = new LoanRequest(LoanType.PERSON, 13, 12000);
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, 1), loanResponse);
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForPersonDeclinedBadRequest() {
        request = new LoanRequest(LoanType.PERSON, 10, 12000);
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, -1), loanResponse);
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForOooDeclined() {
        request = new LoanRequest(LoanType.OOO, 36, 9000);
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, 1), loanResponse);
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForOooApprovedWhere10Months() {
        request = new LoanRequest(LoanType.OOO, 10, 20000);
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.APPROVED, 1), loanResponse);
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForOooDeclinedWhere14Months() {
        request = new LoanRequest(LoanType.OOO, 14, 12000);
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, 1), loanResponse);
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForIp() {
        request = new LoanRequest(LoanType.IP, 1, 1111);
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, 1), loanResponse);
    }

    @Test
    public void shouldGetErrorWhenApplyNullRequest() {
        request = new LoanRequest(null, 1, 1111);
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, -1), loanResponse);
    }

    @Test
    public void shouldGetErrorWhenApplyZeroOrNegativeAmountRequestForOoo() {
        request = new LoanRequest(LoanType.OOO, 1, -1111);
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, -1), loanResponse);
    }

    @Test
    public void shouldGetErrorWhenApplyZeroOrNegativeAmountRequestForPerson() {
        request = new LoanRequest(LoanType.PERSON, 1, -1111);
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, -1), loanResponse);
    }

    @Test
    public void shouldGetErrorWhenApplyZeroOrNegativeMonthsRequestForOoo() {
        request = new LoanRequest(LoanType.OOO, 0, -1111);
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, -1), loanResponse);
    }

    @Test
    public void shouldGetErrorWhenApplyZeroOrNegativeMonthsRequestForPerson() {
        request = new LoanRequest(LoanType.PERSON, 0, -1111);
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, -1), loanResponse);
    }
}
