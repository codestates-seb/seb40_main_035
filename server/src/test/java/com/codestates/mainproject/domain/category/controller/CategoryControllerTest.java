package com.codestates.mainproject.domain.category.controller;

import com.codestates.mainproject.domain.category.mapper.CategoryMapper;
import com.codestates.mainproject.domain.category.service.CategoryService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(value = CategoryController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class CategoryControllerTest {
    @MockBean
    private CategoryService commentService;

    @MockBean
    private CategoryMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    void postCategory() throws Exception {

    }

    @Test
    void patchCategory() {
    }

    @Test
    void getCategory() {
    }

    @Test
    void getCategories() {
    }

    @Test
    void deleteCategory() {
    }
}