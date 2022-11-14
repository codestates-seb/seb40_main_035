package com.codestates.mainproject.domain.industry.service;

import com.codestates.mainproject.domain.industry.entity.Industry;
import com.codestates.mainproject.domain.industry.repository.IndustryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class IndustryService {
    private final IndustryRepository industryRepository;

    public Industry createIndustry(Industry industry) {
        verifyExistingName(industry.getName());
        return industryRepository.save(industry);
    }

    public Industry findIndustry(long industryId) {

        return findVerifiedIndustry(industryId);
    }

    public List<Industry> findIndustries() {
        return industryRepository.findAll();
    }

    public void deleteIndustry(long industryId) {
        Industry industry = findVerifiedIndustry(industryId);
        industryRepository.delete(industry);
    }

    public Industry findVerifiedIndustry(long industryId) {
        Optional<Industry> optionalIndustry = industryRepository.findById(industryId);
        return optionalIndustry.orElseThrow(() -> new RuntimeException("존재하지 않는 산업군입니다."));
    }

    private void verifyExistingName(String name) {
        Optional<Industry> optionalIndustry = industryRepository.findByName(name);

        if (optionalIndustry.isPresent()) {
            throw new RuntimeException("이미 존재하는 이름입니다.");
        }
    }
}
