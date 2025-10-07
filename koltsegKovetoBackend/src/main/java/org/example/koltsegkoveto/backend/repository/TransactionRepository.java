package org.example.koltsegkoveto.backend.repository;

import org.example.koltsegkoveto.backend.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t LEFT JOIN FETCH t.category")
    List<Transaction> findAllWithCategory();
}

