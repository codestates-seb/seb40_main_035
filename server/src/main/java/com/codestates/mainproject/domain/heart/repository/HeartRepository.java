package com.codestates.mainproject.domain.heart.repository;

import com.codestates.mainproject.domain.heart.entity.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {
}
