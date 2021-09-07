package com.tinkoff.edu;


import com.tinkoff.edu.app.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class TestsForUuidArrayLoanCalcRepository {
    private LoanRequest request;
    private LoanResponse response;
    private LoanCalcController loanCalcControllerForTestGetId1;
    private LoanCalcController loanCalcControllerForTestGetIncrementedId;
    private LoanCalcController loanCalcControllerForTestsLoanCalcController;
    private static final int ANY_REQUEST_ID = 20;

    @BeforeEach
    public void init() {
        response = new LoanResponse(LoanResponseType.DECLINED);
        request = new LoanRequest(LoanType.IP, 3, 500, "fio");
        loanCalcControllerForTestGetIncrementedId =
                new LoanCalcController(new UuidArrayLoanCalcRepository(ANY_REQUEST_ID));
        loanCalcControllerForTestsLoanCalcController =
                new LoanCalcController(new UuidArrayLoanCalcRepository(0));
        loanCalcControllerForTestGetId1 = new LoanCalcController(new UuidArrayLoanCalcRepository());
    }

    @Test
    public void shouldGetResponseTypeForClient() {
        response = loanCalcControllerForTestGetId1.createRequest(request);
        LoanResponseType responseType = loanCalcControllerForTestGetId1.getResponseTypeForClient(response, "11111111-1111-1111-1111-111100001111");
        assertEquals(LoanResponseType.DECLINED, responseType);
        assertNotNull(responseType);
    }

    @Test
    public void shouldSetResponseTypeForManager() {
        LoanResponseType responseType = loanCalcControllerForTestGetId1.setResponseTypeForManager(response, "11111111-1111-1111-1111-111100001111");
        assertEquals(LoanResponseType.APPROVED, responseType);
        assertNotNull(responseType);
    }

    @Test
    public void shouldGetId1WhenFirstCall() {
        response = loanCalcControllerForTestGetId1.createRequest(request);
        LoanRequest[] loanRequest = loanCalcControllerForTestGetId1.createResponse(response);
        assertEquals(1, response.getRequestId());
        assertNotNull(loanRequest[0].getUuid());
    }

    @Test
    public void shouldGetIncrementedIdWhenAnyCall() {
        response = loanCalcControllerForTestGetIncrementedId.createRequest(request);
        LoanRequest[] loanRequest = loanCalcControllerForTestGetId1.createResponse(response);
        assertEquals(ANY_REQUEST_ID + 1, response.getRequestId());
        assertNotNull(loanRequest[0].getUuid());
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForPersonApproved() {
        request = new LoanRequest(UUID.randomUUID().toString(), LoanType.PERSON, 5, 2000, "fio");
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.APPROVED, 1), loanResponse);
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForPersonDeclined() {
        request = new LoanRequest(UUID.randomUUID().toString(), LoanType.PERSON, 13, 12000, "fio");
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, 1), loanResponse);
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForPersonDeclinedBadRequest() {
        request = new LoanRequest(UUID.randomUUID().toString(), LoanType.PERSON, 10, 12000, "fio");
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, -1), loanResponse);
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForOooDeclined() {
        request = new LoanRequest(UUID.randomUUID().toString(), LoanType.OOO, 36, 9000, "fio");
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, 1), loanResponse);
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForOooApprovedWhere10Months() {
        request = new LoanRequest(UUID.randomUUID().toString(), LoanType.OOO, 10, 20000, "fio");
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.APPROVED, 1), loanResponse);
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForOooDeclinedWhere14Months() {
        request = new LoanRequest(UUID.randomUUID().toString(), LoanType.OOO, 14, 12000, "fio");
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, 1), loanResponse);
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForIp() {
        request = new LoanRequest(UUID.randomUUID().toString(), LoanType.IP, 1, 1111, "fio");
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, 1), loanResponse);
    }

    @Test
    public void shouldGetErrorWhenApplyNullRequest() {
        request = new LoanRequest(UUID.randomUUID().toString(), null, 1, 1111, "fio");
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, -1), loanResponse);
    }

    @Test
    public void shouldGetErrorWhenApplyZeroOrNegativeAmountRequestForOoo() {
        request = new LoanRequest(UUID.randomUUID().toString(), LoanType.OOO, 1, -1111, "fio");
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, -1), loanResponse);
    }

    @Test
    public void shouldGetErrorWhenApplyZeroOrNegativeAmountRequestForPerson() {
        request = new LoanRequest(UUID.randomUUID().toString(), LoanType.PERSON, 1, -1111, "fio");
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, -1), loanResponse);
    }

    @Test
    public void shouldGetErrorWhenApplyZeroOrNegativeMonthsRequestForOoo() {
        request = new LoanRequest(UUID.randomUUID().toString(), LoanType.OOO, 0, -1111, "fio");
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, -1), loanResponse);
    }

    @Test
    public void shouldGetErrorWhenApplyZeroOrNegativeMonthsRequestForPerson() {
        request = new LoanRequest(UUID.randomUUID().toString(), LoanType.PERSON, 0, -1111, "fio");
        LoanResponse loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest(request);
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, -1), loanResponse);
    }
}
