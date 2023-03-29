package com.recipes.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.recipes.appuser.AppUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table
public class Recipe implements Comparable<Recipe> {
    @Id
    @SequenceGenerator(
            name = "recipe_sequence",
            sequenceName = "recipe_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "recipe_sequence"
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String name;
    private String category;
    private String date;
    private String description;
    private String[] ingredients;
    private String[] directions;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private AppUser appUser;

    public Recipe(String name,
                  String category,
                  String description,
                  String[] ingredients,
                  String[] directions) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Recipe recipe = (Recipe) o;
        return id != null && Objects.equals(id, recipe.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public int compareTo(Recipe o) {
        return getDate().compareTo(o.getDate());
    }
}
