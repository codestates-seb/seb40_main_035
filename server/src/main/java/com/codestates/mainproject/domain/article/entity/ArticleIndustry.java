package com.codestates.mainproject.domain.article.entity;

import com.codestates.mainproject.domain.industry.entity.Industry;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ArticleIndustry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long articleIndustryId;

    @ManyToOne
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "INDUSTRY_ID")
    private Industry industry;
}
