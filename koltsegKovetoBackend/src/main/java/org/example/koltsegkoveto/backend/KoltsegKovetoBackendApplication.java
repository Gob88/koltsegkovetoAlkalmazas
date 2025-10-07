package org.example.koltsegkoveto.backend;

import org.example.koltsegkoveto.backend.entity.Category;
import org.example.koltsegkoveto.backend.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KoltsegKovetoBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(KoltsegKovetoBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(CategoryRepository categoryRepository) {
        return args -> {
            if (categoryRepository.count() == 0) {
                categoryRepository.save(new Category("Bevétel"));
                categoryRepository.save(new Category("Kiadás"));
            }
        };
    }
}
