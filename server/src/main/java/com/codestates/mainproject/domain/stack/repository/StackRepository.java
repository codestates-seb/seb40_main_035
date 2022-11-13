package com.codestates.mainproject.domain.stack.repository;

import com.codestates.mainproject.domain.industry.entity.Industry;
import com.codestates.mainproject.domain.stack.entity.Stack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StackRepository extends JpaRepository<Stack, Long> {
    Optional<Stack> findByName(String name);
}
