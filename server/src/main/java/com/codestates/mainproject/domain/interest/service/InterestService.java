package com.codestates.mainproject.domain.interest.service;

import com.codestates.mainproject.domain.hashtag.entity.Hashtag;
import com.codestates.mainproject.domain.hashtag.repository.HashtagRepository;
import com.codestates.mainproject.domain.interest.entity.Interest;
import com.codestates.mainproject.domain.interest.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class InterestService {
    private final InterestRepository repository;

    public Interest createInterest(Interest interest) {
        verifyExistingName(interest.getName());
        return repository.save(interest);
    }

    public Interest updateInterest(Interest interest) {
        Interest findInterest = findVerifiedInterest(interest.getInterestId());

        Optional.ofNullable(interest.getName())
                .ifPresent(name -> findInterest.setName(name));

        return repository.save(findInterest);
    }

    public Interest findInterest(long interestId) {
        return findVerifiedInterest(interestId);
    }

    public List<Interest> findInterests() {
        return repository.findAll();
    }

    public void deleteInterest(long interestId) {
        Interest interest = findVerifiedInterest(interestId);
        repository.delete(interest);
    }

    public Interest findVerifiedInterest(long interestId) {
        Optional<Interest> optionalInterest = repository.findById(interestId);
        return optionalInterest.orElseThrow(() -> new RuntimeException("존재하지 않는 관심분야입니다."));
    }

    public Interest findVerifiedInterest(String name) {
        Optional<Interest> optionalInterest = repository.findByName(name);
        return optionalInterest.orElseThrow(() -> new RuntimeException("존재하지 않는 관심분야입니다."));
    }

    private void verifyExistingName(String name) {
        Optional<Interest> optionalInterest = repository.findByName(name);

        if (optionalInterest.isPresent()) {
            throw new RuntimeException("이미 존재하는 관심분야입니다.");
        }
    }
}
