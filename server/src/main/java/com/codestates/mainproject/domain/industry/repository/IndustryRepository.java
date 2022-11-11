package com.codestates.mainproject.domain.industry.repository;

import com.codestates.mainproject.domain.industry.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndustryRepository extends JpaRepository<Industry, Long> {
    Optional<Industry> findByName(String name);
}
