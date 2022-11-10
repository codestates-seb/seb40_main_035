package com.codestates.mainproject.domain.answer.repository;

import com.codestates.mainproject.domain.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
