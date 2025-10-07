package org.example.koltsegkoveto.frontend.service;

import org.example.koltsegkoveto.frontend.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class CategoryService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "http://localhost:8080/categories";

    public List<Category> getAllCategories() {
        ResponseEntity<Category[]> response =
                restTemplate.getForEntity(BASE_URL, Category[].class);
        Category[] body = response.getBody();
        return body == null ? List.of() : Arrays.asList(body);
    }

    public Category createCategory(Category category) {
        return restTemplate.postForObject(BASE_URL, category, Category.class);
    }

    public void updateCategory(Long id, Category category) {
        restTemplate.put(BASE_URL + "/" + id, category);
    }

    public void deleteCategory(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
