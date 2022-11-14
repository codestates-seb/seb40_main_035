package com.codestates.mainproject.domain.category.service;

import com.codestates.mainproject.domain.category.entity.Category;
import com.codestates.mainproject.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category createCategory(Category category) {

        verifyExistingName(category.getName());

        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category) {
        Category findCategory = findVerifiedCategory(category.getCategoryId());

        Optional.ofNullable(category.getName())
                .ifPresent(name -> findCategory.setName(name));

        return categoryRepository.save(findCategory);
    }

    public Category findCategory(long categoryId) {
        return findVerifiedCategory(categoryId);
    }

    public List<Category> findCategories() {
        return categoryRepository.findAll();
    }

    public void deleteCategory(long categoryId) {
        Category findCategory = findVerifiedCategory(categoryId);
        categoryRepository.delete(findCategory);
    }

    public Category findVerifiedCategory(long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        return optionalCategory.orElseThrow(() -> new RuntimeException("존재하지 않는 카테고리입니다."));
    }

    private void verifyExistingName(String name) {
        Optional<Category> optionalCategory = categoryRepository.findByName(name);

        if (optionalCategory.isPresent()) {
            throw new RuntimeException("이미 존재하는 카테고리 이름입니다.");
        }
    }
}
