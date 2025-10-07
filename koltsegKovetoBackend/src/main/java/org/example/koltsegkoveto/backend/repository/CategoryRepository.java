package org.example.koltsegkoveto.backend.repository;

import org.example.koltsegkoveto.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name); // kell a bevétel/kiadás felismeréshez
}
