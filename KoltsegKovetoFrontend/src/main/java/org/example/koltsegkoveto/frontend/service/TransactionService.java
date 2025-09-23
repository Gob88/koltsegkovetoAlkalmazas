package org.example.koltsegkoveto.frontend.service;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.koltsegkoveto.frontend.model.Transaction;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class TransactionService {
    private static final String BASE_URL = "http://localhost:8080/transactions";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public Transaction createTransaction(Transaction transaction) {
        try {
            return restTemplate.postForObject(BASE_URL, transaction, Transaction.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public TransactionService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())              // LocalDate támogatás
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // ISO-8601 formátum
    }

    public List<Transaction> getAllTransactions() {
        try {
            String response = restTemplate.getForObject(BASE_URL, String.class);
            return objectMapper.readValue(response, new TypeReference<List<Transaction>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
