package com.codestates.mainproject.domain.skill.entity;

import com.codestates.mainproject.domain.article.entity.ArticleSkill;
import com.codestates.mainproject.domain.member.entity.MemberSkill;
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
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long skillId;

    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SkillSort skillSort;

    public enum SkillSort {
        BACKEND("백엔드"),
        FRONTEND("프론트엔드"),
        MOBILE("모바일"),
        ETC("기타");

        @Getter
        private String sortName;

        SkillSort(String sortName) {
            this.sortName = sortName;
        }
    }

    @OneToMany(mappedBy = "skill", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MemberSkill> memberSkills = new ArrayList<>();

    public void addMemberSkill(MemberSkill memberSkill) {
        memberSkills.add(memberSkill);
    }

    @OneToMany(mappedBy = "skill", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ArticleSkill> articleSkills = new ArrayList<>();

    public void addArticleSkill(ArticleSkill articleSkill) {
        articleSkills.add(articleSkill);
    }
}
