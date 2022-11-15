package com.codestates.mainproject.domain.article.entity;

import com.codestates.mainproject.domain.skill.entity.Skill;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ArticleSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long articleSkillId;

    @ManyToOne
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "SKILL_ID")
    private Skill skill;

    public void addArticle(Article article) {
        this.article = article;
        if (!this.article.getArticleSkills().contains(this)) {
            this.article.getArticleSkills().add(this);
        }
    }
}
