package com.jh2.jonglog.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name ="parent")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> child = new ArrayList<>();

    private Long depth;

    @OneToMany(mappedBy = "category")
    private List<Post> postList = new ArrayList<>();

    @Builder
    public Category(String name, Category parent, List<Category> child, Long depth) {
        this.name = name;
        this.parent = parent;
        this.child = child;
        this.depth = depth;
    }
}
