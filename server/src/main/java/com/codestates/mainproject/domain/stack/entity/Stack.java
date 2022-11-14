package com.codestates.mainproject.domain.stack.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Stack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long stackId;

    @Column(nullable = false, unique = true)
    private String name;
}
