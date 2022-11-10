package com.codestates.mainproject.domain.category.mapper;

import com.codestates.mainproject.domain.category.dto.CategoryPatchDto;
import com.codestates.mainproject.domain.category.dto.CategoryPostDto;
import com.codestates.mainproject.domain.category.dto.CategoryResponseDto;
import com.codestates.mainproject.domain.category.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category categoryPostDtoToCategory(CategoryPostDto postDto);

    Category categoryPatchDtoToCategory(CategoryPatchDto patchDto);

    CategoryResponseDto categoryToCategoryResponseDto(Category category);

    List<CategoryResponseDto> categoriesToCategoryResponseDtos(List<Category> categories);
}
