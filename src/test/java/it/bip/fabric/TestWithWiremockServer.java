package it.bip.fabric;

import com.google.gson.Gson;
import it.bip.fabric.config.WireMockInitializer;
import it.bip.fabric.config.WireMockStubs;
import it.bip.fabric.controller.RestApiController;
import it.bip.fabric.model.AccountBalance;
import it.bip.fabric.model.AccountTransactionPayload;
import it.bip.fabric.model.MoneyTransfer;
import it.bip.fabric.model.MoneyTransferRequest;
import it.bip.fabric.utils.TestJsonDocumentLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

import static org.springframework.http.HttpStatus.OK;

@ContextConfiguration(initializers = WireMockInitializer.class)
@SpringBootTest
@ActiveProfiles("test-wiremock")
class TestWithWiremockServer {

    @Autowired
    RestApiController restApiController;

    @Autowired
    WireMockStubs wireMockStubs;

    @Test
    void testGetBalance(){
        this.wireMockStubs.generateStubForBalance();
        ResponseEntity<AccountBalance> balance = restApiController.getBalance("Europe/Rome", "14537780");
        Assertions.assertEquals(new BigDecimal("7.27"), Objects.requireNonNull(balance.getBody()).getAvailableBalance());
    }

    @Test
    void testGetTransactions(){
        this.wireMockStubs.generateStubForTransactions();
        ResponseEntity<AccountTransactionPayload> balance = restApiController.getTransactions("Europe/Rome", "14537780", Date.valueOf("2019-01-01"), Date.valueOf("2019-12-30"));
        Assertions.assertEquals(3, Objects.requireNonNull(balance.getBody()).getList().size());
    }

    @Test
    void testMoneyTransfer(){
        this.wireMockStubs.generateStubForMoneyTransfer();
        String jsonData = TestJsonDocumentLoader.loadTestJson("../../../__files/createMoneyTransferClientRequest.json", TestWithMockito.class);
        MoneyTransferRequest requestObj = new Gson().fromJson(jsonData, MoneyTransferRequest.class);
        ResponseEntity<MoneyTransfer> response = restApiController.createMoneyTransfer("Europe/Rome", "14537780", requestObj);
        Assertions.assertEquals(OK.value(), response.getStatusCode().value());
        Assertions.assertEquals("EXECUTED", Objects.requireNonNull(response.getBody()).getStatus());
    }
}
