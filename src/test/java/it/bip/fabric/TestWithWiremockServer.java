package it.bip.fabric;

import com.google.gson.Gson;
import it.bip.fabric.config.WireMockInitializer;
import it.bip.fabric.config.WireMockStubs;
import it.bip.fabric.controller.RestApiController;
import it.bip.fabric.model.dto.AccountBalance;
import it.bip.fabric.model.dto.AccountTransactionPayload;
import it.bip.fabric.model.dto.MoneyTransfer;
import it.bip.fabric.model.dto.MoneyTransferRequest;
import it.bip.fabric.repository.AccountTransactionRepository;
import it.bip.fabric.utils.TestJsonDocumentLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Date;
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

    @MockBean
    private AccountTransactionRepository transactionRepository;

    @Autowired
    MockMvc mockMvc;

    /**
     *  Test recupero saldo con Wiremock
     *
     */
    @Test
    void testGetBalance(){
        this.wireMockStubs.generateStubForBalance();
        ResponseEntity<AccountBalance> balance = restApiController.getBalance("Europe/Rome", "14537780");
        Assertions.assertEquals(new BigDecimal("7.27"), Objects.requireNonNull(balance.getBody()).getAvailableBalance());
    }

    /**
     *  Test recupero transazioni con Wiremock
     *
     */
    @Test
    void testGetTransactions(){
        this.wireMockStubs.generateStubForTransactions();
        ResponseEntity<AccountTransactionPayload> balance = restApiController.getTransactions("Europe/Rome", "14537780", Date.valueOf("2019-01-01"), Date.valueOf("2019-12-30"));
        Assertions.assertEquals(3, Objects.requireNonNull(balance.getBody()).getList().size());
    }

    /**
     *  Test trasferimento denaro con Wiremock
     *
     */
    @Test
    void testMoneyTransfer(){
        this.wireMockStubs.generateStubForMoneyTransfer();
        String jsonData = TestJsonDocumentLoader.loadTestJson("../../../__files/createMoneyTransferClientRequest.json", TestWithMockito.class);
        MoneyTransferRequest requestObj = new Gson().fromJson(jsonData, MoneyTransferRequest.class);
        ResponseEntity<MoneyTransfer> response = restApiController.createMoneyTransfer("Europe/Rome", "14537780", requestObj);
        Assertions.assertEquals(OK.value(), response.getStatusCode().value());
        Assertions.assertEquals("EXECUTED", Objects.requireNonNull(response.getBody()).getStatus());
    }


    /**
     *  Test trasferimento denaro con Wiremock; Case body request non valida.
     *
     */
    @Test
    void testMoneyTransferWrongParams() throws Exception {
        this.wireMockStubs.generateStubForMoneyTransfer();
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
