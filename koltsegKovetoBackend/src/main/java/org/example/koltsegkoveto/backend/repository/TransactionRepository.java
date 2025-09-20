package org.example.koltsegkoveto.backend.repository;

import org.example.koltsegkoveto.backend.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Itt majd lehet egyedi lekérdezéseket írni, de most elég az alap
}
