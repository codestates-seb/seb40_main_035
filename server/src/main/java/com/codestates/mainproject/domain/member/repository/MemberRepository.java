package com.codestates.mainproject.domain.member.repository;

import com.codestates.mainproject.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByName(String name);

    Optional<Member> findByGithub(String github);
}
