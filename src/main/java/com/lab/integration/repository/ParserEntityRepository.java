package com.lab.integration.repository;

import com.lab.integration.model.ParserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParserEntityRepository extends JpaRepository<ParserEntity, Long> {
    ParserEntity findByName(String name);
}
