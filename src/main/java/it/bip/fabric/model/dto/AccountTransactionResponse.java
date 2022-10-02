package it.bip.fabric.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AccountTransactionResponse {
    private List<AccountTransaction> list = new ArrayList<>();
}

