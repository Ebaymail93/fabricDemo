package it.bip.fabrick;

import com.google.gson.Gson;
import it.bip.fabrick.controller.RestApiController;
import it.bip.fabrick.model.dto.clientresponse.AccountBalanceClientResponse;
import it.bip.fabrick.model.dto.clientresponse.AccountTransactionClientResponse;
import it.bip.fabrick.model.dto.clientresponse.MoneyTransferClientResponse;
import it.bip.fabrick.model.entity.AccountTransactionEntity;
import it.bip.fabrick.service.AccountService;
import it.bip.fabrick.utils.TestJsonDocumentLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test-mockito")
@AutoConfigureMockMvc
class TestWithMockito {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    RestApiController restApiController;

    @Autowired
    AccountService accountService;

    @Autowired
    MockMvc mockMvc;


    /**
     * Test Mockito balance recovery
     *
     * @throws Exception
     */
    @Test
    void testGetBalance() throws Exception {
        String jsonObject = TestJsonDocumentLoader.loadTestJson("../../../__files/getBalanceClientResponse.json", TestWithMockito.class);
        ResponseEntity<AccountBalanceClientResponse> myEntity = ResponseEntity.ok(new Gson().fromJson(String.valueOf(jsonObject), AccountBalanceClientResponse.class));
        Mockito.when(restTemplate.exchange(
                Mockito.matches("https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/([0-9]*)/balance"),
                Mockito.eq(HttpMethod.GET),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<AccountBalanceClientResponse>>any())
        ).thenReturn(myEntity);

        /* MOCK MVC */
        this.mockMvc.perform(get("/api/banking/v1.0/account/14537780/getBalance")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.availableBalance").value(new BigDecimal("7.27")));

        /* NO MOCK MVC */
        /*
        ResponseEntity<AccountBalance> balance = restApiController.getBalance("Europe/Rome", "14537780");
        Assertions.assertEquals(new BigDecimal("7.27"), Objects.requireNonNull(balance.getBody()).getAvailableBalance());
        *
        */
    }

    /**
     * Test Mockito money transfer
     *
     * @throws Exception
     */
    @Test
    void testCreateMoneytransfer() throws Exception {
        String jsonRequestMock = TestJsonDocumentLoader.loadTestJson("../../../__files/createMoneyTransferClientRequest.json", TestWithMockito.class);
        String jsonResponseMock = TestJsonDocumentLoader.loadTestJson("../../../__files/createMoneyTransferClientResponse.json", TestWithMockito.class);
        MoneyTransferClientResponse responseObjMock = new Gson().fromJson(jsonResponseMock, MoneyTransferClientResponse.class);
        Mockito.when(restTemplate.postForEntity(
                Mockito.eq("https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/14537780/payments/money-transfers"),
                ArgumentMatchers.any(),
                ArgumentMatchers.any())
        ).thenReturn(ResponseEntity.ok(responseObjMock));

        /* MOCK MVC */
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Time-Zone","Europe/Rome");
        this.mockMvc.perform(post("/api/banking/v1.0/account/14537780/payments/money-transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestMock)
                        .headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("EXECUTED"));

        /* NO MOCK MVC */
        /*
        MoneyTransferRequest requestObjMock = new Gson().fromJson(jsonRequestMock, MoneyTransferRequest.class);
        ResponseEntity<MoneyTransfer> balance = restApiController.createMoneyTransfer("Europe/Rome", "14537780", requestObjMock);
        Assertions.assertEquals("EXECUTED", Objects.requireNonNull(balance.getBody()).getStatus());
        *
        */
    }


    /**
     * Transaction recovery and db persist test with Wiremock
     *
     * @throws Exception
     */
    @Test
    void getTransactions() throws Exception {
        String jsonData = TestJsonDocumentLoader.loadTestJson("../../../__files/getTransactionsClientResponse.json", TestWithMockito.class);
        ResponseEntity<AccountTransactionClientResponse> myEntity = ResponseEntity.ok(new Gson().fromJson(jsonData, AccountTransactionClientResponse.class));
        Mockito.when(restTemplate.exchange(
                Mockito.eq("https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/14537780/transactions?fromAccountingDate=2019-01-01&toAccountingDate=2019-12-30"),
                Mockito.eq(HttpMethod.GET),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<AccountTransactionClientResponse>>any())
        ).thenReturn(myEntity);

        /* MOCK MVC*/
        this.mockMvc.perform(get("/api/banking/v1.0/account/14537780/getTransactions?fromAccountingDate=2019-01-01&toAccountingDate=2019-12-30")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.list").isNotEmpty())
                .andExpect(jsonPath("$.list.size()").value(3))
                .andExpect(jsonPath("$.list[0].amount").value(new BigDecimal("330.0")));

        /* NO MOCK MVC */
        /*
        ResponseEntity<AccountTransactionPayload> balance = restApiController.getTransactions("Europe/Rome", "14537780", Date.valueOf("2019-01-01"), Date.valueOf("2019-12-30"));
        Assertions.assertEquals(3, Objects.requireNonNull(balance.getBody()).getList().size());
        *
        */

        /* Test salvataggio su DB **/
        List<AccountTransactionEntity> transactions = this.accountService.getTransactions();
        Assertions.assertEquals(3, transactions.size());

    }

    /**
     * Mockito money transfer test with invalid request body. ( Blank "description" field)
     * @throws Exception
     */
    @Test
    void testCreateMoneytransferWrong() throws Exception {
        String jsonRequestMock = TestJsonDocumentLoader.loadTestJson("../../../__files/wrongCreateMoneyTransferClientRequest.json", TestWithMockito.class);

        /* MOCK MVC */
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Time-Zone","Europe/Rome");
        this.mockMvc.perform(post("/api/banking/v1.0/account/14537780/payments/money-transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestMock)
                        .headers(httpHeaders))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.violations[0].fieldName").value("description"));
    }


}
