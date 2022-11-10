package com.codestates.mainproject.domain.article.controller;

import com.codestates.mainproject.domain.article.dto.ArticlePostDto;
import com.codestates.mainproject.domain.article.mapper.ArticleMapper;
import com.codestates.mainproject.domain.article.service.ArticleService;
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

@WebMvcTest(value = ArticleController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class ArticleControllerTest {
    @MockBean
    private ArticleService articleService;

    @MockBean
    private ArticleMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;


    @Test
    void postArticle() throws Exception{
    }

    @Test
    void patchArticle() {
    }

    @Test
    void getArticle() {
    }

    @Test
    void getArticles() {
    }

    @Test
    void deleteArticle() {
    }
}