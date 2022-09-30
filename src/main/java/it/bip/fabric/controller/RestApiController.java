package it.bip.fabric.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.bip.fabric.config.FabricClientImpl;
import it.bip.fabric.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/banking/v1.0")
@Validated
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "I parametri inseriti non sono corretti",
                content = {@Content(mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = "403", description = "Non possiedi i permessi necessari alla richiesta",
                content = {@Content(mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Errore tecnico. La condizione BP049 non e' prevista per il conto",
                content = {@Content(mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = "504", description = "Timeout durante la ricezione di una risposta dal server",
                content = {@Content(mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class))})
})
public class RestApiController {

    @Autowired
    FabricClientImpl fabricClientImpl;

    
    @Operation(summary = "Retrieves the balance of a specific cash account")
    @ApiResponse(responseCode = "200", description = "Transazioni recuperate",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AccountBalance.class))})
    @GetMapping("/account/{accountId}/getBalance")
    public ResponseEntity<AccountBalance> getBalance(@Parameter(description = "The time zone that is used to provide the response date fields, using Java TimeZone ID format", in = ParameterIn.HEADER, required = false)
                                             @RequestHeader(value = "X-Time-Zone", required = false) String timeZone,
                                                     @Parameter(description = "The ID of the account", required = true)
                                             @PathVariable(value = "accountId") String accountId) {
        AccountBalance balance = fabricClientImpl.getBalance(timeZone, accountId);
        return ResponseEntity.ok(balance);
    }


    @Operation(summary = "Creates a new money transfer")
    @ApiResponse(responseCode = "200", description = "Operazione eseguita",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MoneyTransfer.class))})
    @PostMapping(value = "/account/{accountId}/payments/money-transfers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MoneyTransfer> createMoneyTransfer(@Parameter(description = "The time zone that is used to provide the response date fields, using Java TimeZone ID format", in = ParameterIn.HEADER, required = true)
                                                     @RequestHeader(value = "X-Time-Zone") String timeZone,
                                                             @Parameter(description = "The ID of the account", required = true)
                                                     @PathVariable(value = "accountId") String accountId,
                                                             @Parameter(description = "Request body", required = true)
                                                     @Valid @RequestBody MoneyTransferRequest moneyTransferRequest) {
        MoneyTransfer moneyTransfer = fabricClientImpl.createMoneyTransfer(timeZone, accountId, moneyTransferRequest);
        return ResponseEntity.ok(moneyTransfer);
    }


    
    @Operation(summary = "Retrieves the transactions of a specific cash account")
    @ApiResponse(responseCode = "200", description = "Transazioni recuperate",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AccountTransactionPayload.class))})
    @GetMapping("/account/{accountId}/getTransactions")
    public ResponseEntity<AccountTransactionPayload> getTransactions(@Parameter(description = "The time zone that is used to provide the response date fields, using Java TimeZone ID format", in = ParameterIn.HEADER)
                                                      @RequestHeader(value = "X-Time-Zone", required = false) String timeZone,
                                                                     @Parameter(description = "The ID of the account", required = true) @PathVariable(value = "accountId") String accountId,
                                                                     @Parameter(description = "The accounting date from which transactions should be fetched", required = true) @Valid @RequestParam(value = "fromAccountingDate") Date fromAccountingDate,
                                                                     @Parameter(description = "The accounting date to which transactions should be fetched", required = true) @Valid @RequestParam(value = "toAccountingDate") Date toAccountingDate) {
        AccountTransactionPayload transactions = fabricClientImpl.getTransactions(timeZone, accountId, fromAccountingDate, toAccountingDate);
        return ResponseEntity.ok().body(transactions);
    }


}