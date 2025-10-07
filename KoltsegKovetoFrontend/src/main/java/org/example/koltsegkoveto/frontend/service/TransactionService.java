package org.example.koltsegkoveto.frontend.service;

import org.example.koltsegkoveto.frontend.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class TransactionService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "http://localhost:8080/transactions";

    // --- LIST ---
    public List<Transaction> getAllTransactions() {
        ResponseEntity<Transaction[]> response =
                restTemplate.getForEntity(BASE_URL, Transaction[].class);
        return Arrays.asList(response.getBody());
    }

    // --- CREATE ---
    public Transaction createTransaction(Transaction transaction) {
        return restTemplate.postForObject(BASE_URL, transaction, Transaction.class);
    }

    // --- UPDATE ---
    public void updateTransaction(Long id, Transaction transaction) {
        restTemplate.put(BASE_URL + "/" + id, transaction);
    }

    // --- DELETE ---
    public void deleteTransaction(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
