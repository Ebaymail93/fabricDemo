package it.bip.fabric.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AccountTransactionPayload {
    private List<AccountTransaction> list = new ArrayList<>();
}
