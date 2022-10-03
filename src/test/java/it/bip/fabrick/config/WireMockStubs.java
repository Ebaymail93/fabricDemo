package it.bip.fabrick.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@ConditionalOnBean(WireMockServer.class)
@Component
public class WireMockStubs {

        private final WireMockServer wireMockServer;

        public WireMockStubs(WireMockServer wireMockServer) {
                this.wireMockServer = wireMockServer;
        }

        public void generateStubForBalance() {
                this.wireMockServer.stubFor(get(urlEqualTo("/api/gbs/banking/v4.0/accounts/14537780/balance"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBodyFile("getBalanceClientResponse.json")));
        }

        public void generateStubForTransactions() {
                this.wireMockServer.stubFor(get(urlEqualTo("/api/gbs/banking/v4.0/accounts/14537780/transactions?fromAccountingDate=2019-01-01&toAccountingDate=2019-12-30"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBodyFile("getTransactionsClientResponse.json")));
        }

        public void generateStubForMoneyTransfer() {
                this.wireMockServer.stubFor(post(urlEqualTo("/api/gbs/banking/v4.0/accounts/14537780/payments/money-transfers"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBodyFile("createMoneyTransferClientResponse.json")));
        }
}