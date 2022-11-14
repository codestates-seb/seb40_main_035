package com.codestates.mainproject.domain.category.entity;

import com.codestates.mainproject.audit.Auditable;
import com.codestates.mainproject.domain.article.entity.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Article> articles = new ArrayList<>();
}
