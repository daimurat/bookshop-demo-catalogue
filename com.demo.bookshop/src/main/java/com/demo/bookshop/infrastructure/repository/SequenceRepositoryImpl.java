package com.demo.bookshop.infrastructure.repository;

import com.demo.bookshop.domain.repository.SequenceRepository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

public class SequenceRepositoryImpl implements SequenceRepository {
    @Inject
    EntityManager em;

    private static final String NEXT_SEQUENCE_SQL =
        "UPDATE sequences SET sequence = (LAST_INSERT_ID(sequence) + 1) WHERE name = :name";

    @Override
    @Transactional
    public String nextBookId() {
        String name = "id";
        int updated = em.createNativeQuery(NEXT_SEQUENCE_SQL)
            .setParameter("name", name)
            .executeUpdate();

        if (updated == 0) {
            throw new RuntimeException("Failed to update sequence");
        }

        // MySQLのLAST_INSERT_ID()を取得
        Long next = ((Number) em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult()).longValue();

        if (next > 999999) {
            throw new RuntimeException("id generation reached to limit");
        }
        return String.format("%07d", next);
    }
}
