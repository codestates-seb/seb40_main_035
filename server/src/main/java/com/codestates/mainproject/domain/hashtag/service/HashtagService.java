package com.codestates.mainproject.domain.hashtag.service;

import com.codestates.mainproject.domain.hashtag.entity.Hashtag;
import com.codestates.mainproject.domain.hashtag.repository.HashtagRepository;
import com.codestates.mainproject.exception.BusinessLogicException;
import com.codestates.mainproject.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    public Hashtag createHashtag(Hashtag hashtag) {
        verifyExistingName(hashtag.getName());
        return hashtagRepository.save(hashtag);
    }

    public Hashtag findHashtag(long hashtagId) {
        return findVerifiedHashtag(hashtagId);
    }

    public List<Hashtag> findHashtags() {
        return hashtagRepository.findAll();
    }

    public void deleteHashtag(long hashtagId) {
        Hashtag hashtag = findVerifiedHashtag(hashtagId);
        hashtagRepository.delete(hashtag);
    }

    public Hashtag findVerifiedHashtag(long hashtagId) {
        Optional<Hashtag> optionalHashtag = hashtagRepository.findById(hashtagId);
        return optionalHashtag.orElseThrow(() -> new BusinessLogicException(ExceptionCode.HASHTAG_NOT_FOUND));
    }

    public Hashtag findVerifiedHashtag(String name) {
        Optional<Hashtag> optionalHashtag = hashtagRepository.findByName(name);
        if (optionalHashtag.isPresent()) {
            return optionalHashtag.get();
        } else {
            Hashtag hashtag = new Hashtag();
            hashtag.setName(name);
            return hashtagRepository.save(hashtag);
        }
    }

    private void verifyExistingName(String name) {
        Optional<Hashtag> optionalHashtag = hashtagRepository.findByName(name);

        if (optionalHashtag.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.HASHTAG_ALREADY_EXISTS);
        }
    }
}
