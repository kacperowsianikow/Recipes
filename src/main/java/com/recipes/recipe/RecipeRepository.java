package com.recipes.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByName(String name);
    List<Recipe> findByNameIgnoreCaseContaining(
            @Param("name") String name);
    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(
            @Param("category") String category);
}
