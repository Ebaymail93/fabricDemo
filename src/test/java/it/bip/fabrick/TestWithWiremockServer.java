package it.bip.fabrick;

import com.google.gson.Gson;
import it.bip.fabrick.config.WireMockInitializer;
import it.bip.fabrick.config.WireMockStubs;
import it.bip.fabrick.controller.RestApiController;
import it.bip.fabrick.model.dto.AccountBalanceResponse;
import it.bip.fabrick.model.dto.AccountTransactionResponse;
import it.bip.fabrick.model.dto.MoneyTransferRequest;
import it.bip.fabrick.model.dto.MoneyTransferResponse;
import it.bip.fabrick.model.entity.AccountTransactionEntity;
import it.bip.fabrick.service.AccountService;
import it.bip.fabrick.utils.TestJsonDocumentLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(initializers = WireMockInitializer.class)
@SpringBootTest
@ActiveProfiles("test-wiremock")
@AutoConfigureMockMvc
class TestWithWiremockServer {

    @Autowired
    RestApiController restApiController;

    @Autowired
    WireMockStubs wireMockStubs;

    @Autowired
    AccountService accountService;

    @Autowired
    MockMvc mockMvc;

    /**
     *  Test Wiremock balance recovery
     *
     */
    @Test
    void testGetBalance(){
        this.wireMockStubs.generateStubForBalance();
        ResponseEntity<AccountBalanceResponse> balance = restApiController.getBalance("Europe/Rome", "14537780");
        Assertions.assertEquals(new BigDecimal("7.27"), Objects.requireNonNull(balance.getBody()).getAvailableBalance());
    }

    /**
     *  Transaction recovery and db persist test with Wiremock
     *
     */
    @Test
    void testGetTransactions(){
        this.wireMockStubs.generateStubForTransactions();
        ResponseEntity<AccountTransactionResponse> balance = restApiController.getTransactions("Europe/Rome", "14537780", Date.valueOf("2019-01-01"), Date.valueOf("2019-12-30"));
        Assertions.assertEquals(3, Objects.requireNonNull(balance.getBody()).getList().size());
        List<AccountTransactionEntity> transactions = this.accountService.getTransactions();
        Assertions.assertEquals(3, transactions.size());
    }

    /**
     *  Test Wiremock money transfer
     *
     */
    @Test
    void testMoneyTransfer(){
        this.wireMockStubs.generateStubForMoneyTransfer();
        String jsonData = TestJsonDocumentLoader.loadTestJson("../../../__files/createMoneyTransferClientRequest.json", TestWithMockito.class);
        MoneyTransferRequest requestObj = new Gson().fromJson(jsonData, MoneyTransferRequest.class);
        ResponseEntity<MoneyTransferResponse> response = restApiController.createMoneyTransfer("Europe/Rome", "14537780", requestObj);
        Assertions.assertEquals(OK.value(), response.getStatusCode().value());
        Assertions.assertEquals("EXECUTED", Objects.requireNonNull(response.getBody()).getStatus());
    }


    /**
     *  Test Wiremock money transfer with invalid request body. ( Blank "description" field)
     *
     */
    @Test
    void testMoneyTransferWrongParams() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Time-Zone","Europe/Rome");
        String jsonData = TestJsonDocumentLoader.loadTestJson("../../../__files/wrongCreateMoneyTransferClientRequest.json", TestWithMockito.class);
        this.mockMvc.perform(post("/api/banking/v1.0/account/14537780/payments/money-transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData)
                        .headers(httpHeaders))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.violations[0].fieldName").value("description"));
    }
}
