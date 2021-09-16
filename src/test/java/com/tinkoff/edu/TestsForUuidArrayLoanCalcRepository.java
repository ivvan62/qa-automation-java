package com.tinkoff.edu;


import com.tinkoff.edu.app.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for simple App.
 */
public class TestsForUuidArrayLoanCalcRepository {
    private LoanRequest request;
    private LoanRequest[] loanRequest;
    private LoanResponse response;
    private LoanResponse loanResponse;
    private LoanResponseType responseType;
    private LoanCalcController loanCalcControllerForTestGetId1;
    private LoanCalcController loanCalcControllerForTestGetIncrementedId;
    private LoanCalcController loanCalcControllerForTestsLoanCalcController;
    private LoanCalcController loanCalcControllerForTestsAny;
    private static final int ANY_REQUEST_ID = 20;

    @BeforeEach
    public void init() {
        response = new LoanResponse(LoanResponseType.DECLINED);
        request = new LoanRequest(UUID.randomUUID().toString(), LoanType.IP, 3, 500, "Ivanov Ivan");
        loanCalcControllerForTestsAny = new LoanCalcController(new UuidArrayLoanCalcRepository(ANY_REQUEST_ID));
        loanCalcControllerForTestGetIncrementedId =
                new LoanCalcController(new UuidArrayLoanCalcRepository(ANY_REQUEST_ID));
        loanCalcControllerForTestsLoanCalcController =
                new LoanCalcController(new UuidArrayLoanCalcRepository(0));
        loanCalcControllerForTestGetId1 = new LoanCalcController(new UuidArrayLoanCalcRepository());
    }

    @Test
    public void shouldGetErrorWhenApplyNullForLoanType() {
        assertThrows(NullPointerException.class,
                () -> loanCalcControllerForTestsAny.createRequest(new LoanRequest(UUID.randomUUID().toString(),null, 3, 100500, "Ivanov Ivan")));
    }

    @Test
    public void shouldGetErrorWhenApplyNullOrEmptyForUUID() {
        assertThrows(NullPointerException.class,
                () -> loanCalcControllerForTestsAny.createRequest(new LoanRequest(null, LoanType.IP, 3, 100500, "Ivanov Ivan")));
        assertThrows(NullPointerException.class,
                () -> loanCalcControllerForTestsAny.createRequest(new LoanRequest("", LoanType.IP, 3, 100500, "Ivanov Ivan")));
    }

    @Test
    public void shouldGetErrorWhenCountMonthsInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> loanCalcControllerForTestsAny.createRequest(new LoanRequest(UUID.randomUUID().toString(), LoanType.IP, 0, 100500, "Ivanov Ivan")));
        assertThrows(IllegalArgumentException.class,
                () -> loanCalcControllerForTestsAny.createRequest(new LoanRequest(UUID.randomUUID().toString(), LoanType.IP, 101, 100500, "Ivanov Ivan")));
    }

    @Test
    public void shouldGetErrorWhenCountAmountsInvalid() {
        AmountValidationException thrown = assertThrows(AmountValidationException.class, () -> loanCalcControllerForTestsAny.
                createRequest(new LoanRequest(UUID.randomUUID().toString(), LoanType.IP, 3, 0.00100500, "Ivanov Ivan")));
        assertTrue(thrown.getMessage().
                contains("Сумма кредита должна быть не менее 0.01 и не более 999 999.99"));
    }

    @Test
    public void shouldGetErrorIncorrectFullName() {
        assertThrows(IllegalArgumentException.class,
                () -> loanCalcControllerForTestsAny.createRequest(new LoanRequest(UUID.randomUUID().toString(), LoanType.IP, 3, 100500, "Ivanov Ivan!")));
        assertThrows(IllegalArgumentException.class,
                () -> loanCalcControllerForTestsAny.createRequest(new LoanRequest(UUID.randomUUID().toString(), LoanType.IP, 3, 100500, "Дядя Петя, ты дурак?")));
    }

    @Test
    public void shouldGetErrorWhenApplyNullOrEmptyForFullName() {
        assertThrows(NullPointerException.class,
                () -> loanCalcControllerForTestsAny.createRequest(new LoanRequest(UUID.randomUUID().toString(), LoanType.IP, 3, 100500, "")));
        assertThrows(NullPointerException.class,
                () -> loanCalcControllerForTestsAny.createRequest(new LoanRequest(UUID.randomUUID().toString(), LoanType.IP, 3, 100500, null)));
    }

    @Test
    public void shouldGetErrorLengthFullNameInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> loanCalcControllerForTestsAny.createRequest(new LoanRequest(UUID.randomUUID().toString(), LoanType.IP, 3, 100500, "fio")));
        assertThrows(IllegalArgumentException.class,
                () -> loanCalcControllerForTestsAny.createRequest(new LoanRequest(UUID.randomUUID().toString(), LoanType.IP, 11, 100500,
                        "IvanovIvanIvanovichIvanovIvanIvanovichIvanovIvanIvanovichIvanovIvanIvanovichIvanovIvanIvanovichIvanovIvanIvanovichIvanovIvanIvanovichIvanovIvanIvanovich")));
    }

    @Test
    public void shouldGetResponseTypeForClient() {
        response = loanCalcControllerForTestGetId1.createRequest
                (new LoanRequest(UUID.randomUUID().toString(), LoanType.IP, 3, 2000, "Ivanfffov Ivan"));
        responseType = loanCalcControllerForTestsAny.getResponseTypeForClient(response, "11111111-1111-1111-1111-111100001111");
        assertEquals(LoanResponseType.DECLINED, responseType);
        assertNotNull(responseType);
    }

    @Test
    public void shouldSetResponseTypeForManager() {
        responseType = loanCalcControllerForTestsAny.setResponseTypeForManager(response, "11111111-1111-1111-1111-111100001111");
        assertEquals(LoanResponseType.APPROVED, responseType);
        assertNotNull(responseType);
    }

    @Test
    public void shouldGetId1WhenFirstCall() {
        loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest
                (new LoanRequest(UUID.randomUUID().toString(), LoanType.PERSON, 5, 2000, "Ivanov Ivan"));
        loanRequest = loanCalcControllerForTestGetId1.createResponse(loanResponse);
        assertEquals(1, loanResponse.getRequestId());
        assertNotNull(loanRequest[0].getUuid());
    }

    @Test
    public void shouldGetIncrementedIdWhenAnyCall() {
        response = loanCalcControllerForTestGetIncrementedId.createRequest(request);
        loanRequest = loanCalcControllerForTestGetId1.createResponse(response);
        assertEquals(ANY_REQUEST_ID + 1, response.getRequestId());
        assertNotNull(loanRequest[0].getUuid());
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForPersonApproved() {
        loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest
                (new LoanRequest(UUID.randomUUID().toString(), LoanType.PERSON, 5, 2000, "Ivanov Ivan"));
        assertEquals(new LoanResponse(LoanResponseType.APPROVED, 1), loanResponse);
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForPersonDeclined() {
        loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest
                (new LoanRequest(UUID.randomUUID().toString(), LoanType.PERSON, 13, 12000, "Ivanov Ivan"));
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, 1), loanResponse);
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForPersonDeclinedBadRequest() {
        loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest
                (new LoanRequest(UUID.randomUUID().toString(), LoanType.PERSON, 10, 12000, "Ivanov Ivan"));
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, -1), loanResponse);
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForOooDeclined() {
        loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest
                (new LoanRequest(UUID.randomUUID().toString(), LoanType.OOO, 36, 9000, "Ivanov Ivan"));
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, 1), loanResponse);
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForOooApprovedWhere10Months() {
        loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest
                (new LoanRequest(UUID.randomUUID().toString(), LoanType.OOO, 10, 20000, "Ivanov Ivan"));
        assertEquals(new LoanResponse(LoanResponseType.APPROVED, 1), loanResponse);
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForOooDeclinedWhere14Months() {
        loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest
                (new LoanRequest(UUID.randomUUID().toString(), LoanType.OOO, 14, 12000, "Ivanov Ivan"));
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, 1), loanResponse);
    }

    @Test
    public void shouldGetApprovedWhenValidRequestForIp() {
        loanResponse = loanCalcControllerForTestsLoanCalcController.createRequest
                (new LoanRequest(UUID.randomUUID().toString(), LoanType.IP, 1, 1111, "Ivanov Ivan"));
        assertEquals(new LoanResponse(LoanResponseType.DECLINED, 1), loanResponse);
    }
}