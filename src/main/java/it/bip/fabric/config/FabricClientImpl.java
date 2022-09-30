package it.bip.fabric.config;

import it.bip.fabric.mapper.TransactionEntityMapper;
import it.bip.fabric.mapper.ClientModelMapper;
import it.bip.fabric.model.dto.*;
import it.bip.fabric.model.dto.clientresponse.AccountBalanceClientResponse;
import it.bip.fabric.model.dto.clientresponse.AccountTransactionClientResponse;
import it.bip.fabric.model.dto.clientresponse.MoneyTransferClientResponse;
import it.bip.fabric.model.entity.AccountTransactionEntity;
import it.bip.fabric.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FabricClientImpl implements FabricClient {

    @Value("${external-api.url.baseUrl}")
    private String url;
    @Value("${external-api.url.balance}")
    private String urlBalance;
    @Value("${external-api.url.transaction}")
    private String urlTransactions;
    @Value("${external-api.url.moneyTransfer}")
    private String urlMoneyTransfer;

    private static final String X_TIME_ZONE = "X-Time-Zone";

    private static final String FROM_DATE = "fromAccountingDate";

    private static final String TO_DATE = "toAccountingDate";

    private final RestTemplate restTemplate;

    private final AccountService accountService;


    @Autowired
    public FabricClientImpl(RestTemplate restTemplate, AccountService accountService) {
        this.restTemplate = restTemplate;
        this.accountService = accountService;
    }

    @Override
    public AccountBalance getBalance(String timeZone, String accountId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        String completeUrl = url + accountId + urlBalance;
        if (StringUtils.isNotEmpty(timeZone)) {
            httpHeaders.set(X_TIME_ZONE, timeZone);
        }
        HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<AccountBalanceClientResponse> response = restTemplate.exchange(
                completeUrl, HttpMethod.GET, requestEntity, AccountBalanceClientResponse.class);
        ClientModelMapper<AccountBalance> mapper = new ClientModelMapper<>();
        return Objects.requireNonNull(mapper.entityToResource(response.getBody()));
    }

    @Override
    public MoneyTransfer createMoneyTransfer(String timeZone, String accountId, MoneyTransferRequest moneyTransferRequest) throws HttpClientErrorException {
        String completeUrl = url + accountId + urlMoneyTransfer;
        HttpHeaders httpHeaders = new HttpHeaders();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(completeUrl);
        if (StringUtils.isNotEmpty(timeZone)) {
            httpHeaders.set(X_TIME_ZONE, timeZone);
        }
        HttpEntity<MoneyTransferRequest> requestEntity = new HttpEntity<>(moneyTransferRequest, httpHeaders);
        ResponseEntity<MoneyTransferClientResponse> response = restTemplate.postForEntity(
                builder.toUriString(), requestEntity.toString(), MoneyTransferClientResponse.class);
        ClientModelMapper<MoneyTransfer> mapper = new ClientModelMapper<>();
        return Objects.requireNonNull(mapper.entityToResource(response.getBody()));
    }

    @Override
    @Transactional
    public AccountTransactionPayload getTransactions(String timeZone, String accountId, Date fromAccountingDate, Date toAccountingDate) {
        String completeUrl = url + accountId + urlTransactions;
        HttpHeaders httpHeaders = new HttpHeaders();
        Map<String, String> params = new HashMap<>();
        params.put(FROM_DATE, fromAccountingDate.toString());
        params.put(TO_DATE, toAccountingDate.toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(completeUrl);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }
        if (StringUtils.isNotEmpty(timeZone)) {
            httpHeaders.set(X_TIME_ZONE, timeZone);
        }
        HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<AccountTransactionClientResponse> response = restTemplate.exchange(
                builder.toUriString(), HttpMethod.GET, requestEntity, AccountTransactionClientResponse.class);
        List<AccountTransaction> list = Objects.requireNonNull(response.getBody()).getPayload().getList();
        List<AccountTransactionEntity> entities = list.stream().map(TransactionEntityMapper::toEntity).collect(Collectors.toList());
        entities.forEach(k->k.setAccountId(accountId));
        accountService.saveTransactions(entities);
        ClientModelMapper<AccountTransactionPayload> mapper = new ClientModelMapper<>();
        return Objects.requireNonNull(mapper.entityToResource(response.getBody()));
    }

}
