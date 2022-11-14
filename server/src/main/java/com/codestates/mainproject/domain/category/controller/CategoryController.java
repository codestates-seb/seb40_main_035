package com.codestates.mainproject.domain.category.controller;

import com.codestates.mainproject.domain.category.dto.CategoryPatchDto;
import com.codestates.mainproject.domain.category.dto.CategoryPostDto;
import com.codestates.mainproject.domain.category.dto.CategoryResponseDto;
import com.codestates.mainproject.domain.category.entity.Category;
import com.codestates.mainproject.domain.category.mapper.CategoryMapper;
import com.codestates.mainproject.domain.category.service.CategoryService;
import com.codestates.mainproject.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/categories")
@Validated
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper mapper;

    @PostMapping
    public ResponseEntity postCategory(@Valid @RequestBody CategoryPostDto postDto) {

        Category category = mapper.categoryPostDtoToCategory(postDto);
        Category createdCategory = categoryService.createCategory(category);
        CategoryResponseDto responseDto = mapper.categoryToCategoryResponseDto(createdCategory);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{category-id}")
    public ResponseEntity patchCategory(@PathVariable("category-id") @Positive long categoryId,
                                       @Valid @RequestBody CategoryPatchDto patchDto) {
        patchDto.setCategoryId(categoryId);

        Category category = mapper.categoryPatchDtoToCategory(patchDto);
        Category updatedCategory = categoryService.updateCategory(category);
        CategoryResponseDto responseDto = mapper.categoryToCategoryResponseDto(updatedCategory);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping("/{category-id}")
    public ResponseEntity getCategory(@PathVariable("category-id") @Positive long categoryId) {

        Category category = categoryService.findCategory(categoryId);
        CategoryResponseDto responseDto = mapper.categoryToCategoryResponseDto(category);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getCategories() {

        List<Category> categories = categoryService.findCategories();
        List<CategoryResponseDto> responseDtos = mapper.categoriesToCategoryResponseDtos(categories);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDtos), HttpStatus.OK);
    }

    @DeleteMapping("/{category-id}")
    public ResponseEntity deleteCategory(@PathVariable("category-id") @Positive long categoryId) {

        categoryService.deleteCategory(categoryId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

