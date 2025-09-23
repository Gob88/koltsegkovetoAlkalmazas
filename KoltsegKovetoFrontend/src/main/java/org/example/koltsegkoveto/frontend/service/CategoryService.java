package org.example.koltsegkoveto.frontend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.koltsegkoveto.frontend.model.Category;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class CategoryService {
    private static final String BASE_URL = "http://localhost:8080/categories";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CategoryService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public Category createCategory(Category category) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.postForObject(BASE_URL, category, Category.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Category> getAllCategories() {
        try {
            String response = restTemplate.getForObject(BASE_URL, String.class);
            return objectMapper.readValue(response, new TypeReference<List<Category>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
