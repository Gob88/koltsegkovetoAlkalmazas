package org.example.koltsegkoveto.backend.service;

import org.example.koltsegkoveto.backend.entity.Category;
import org.example.koltsegkoveto.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Összes kategória lekérése
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Egy kategória lekérése ID alapján
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    // Új kategória létrehozása
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Kategória frissítése
    public Optional<Category> updateCategory(Long id, Category categoryDetails) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(categoryDetails.getName());
            return categoryRepository.save(category);
        });
    }

    // Kategória törlése
    public boolean deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
