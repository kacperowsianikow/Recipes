package com.recipes.recipe;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final LocalDateTime date = LocalDateTime.now();

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getOneRecipe(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND
                ));
    }

    public Long addNewRecipe(@NonNull Recipe recipe) {
        Optional<Recipe> recipeByName =
                recipeRepository.findByName(recipe.getName());

        if (recipeByName.isPresent()) {
            throw new IllegalStateException("name is taken");
        }

        boolean ingredientsContain =
                Arrays.stream(recipe.getIngredients())
                        .anyMatch(String::isBlank);
        boolean directionsContain =
                Arrays.stream(recipe.getDirections())
                        .anyMatch(String::isBlank);
        if (
                recipe.getName().isBlank() ||
                recipe.getDescription().isBlank() ||
                recipe.getCategory().isBlank() ||
                recipe.getIngredients().length == 0 ||
                ingredientsContain ||
                recipe.getDirections().length == 0 ||
                directionsContain
        ) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST
            );
        }
        recipe.setDate(String.valueOf(date));
        recipeRepository.save(recipe);

        return recipe.getId();
    }

    public void deleteRecipe(Long id) {
        boolean exists = recipeRepository.existsById(id);
        if (!exists) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        recipeRepository.deleteById(id);
    }

    @Transactional
    public void updateRecipe(
            Long id,
            String name,
            String category,
            String description,
            String[] ingredients,
            String[] directions
    ) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND
                ));

        if (name != null &&
                name.length() > 0 &&
                !Objects.equals(recipe.getName(), name)) {
            recipe.setName(name);
        }

        if (category != null &&
                category.length() > 0 &&
                !Objects.equals(recipe.getCategory(), category)) {
            recipe.setCategory(category);
        }

        if (description != null &&
                description.length() > 0 &&
                !Objects.equals(recipe.getDescription(), description)) {
            recipe.setDescription(description);
        }

        if (ingredients != null &&
                ingredients.length > 0 &&
                Arrays.stream(recipe.getIngredients())
                        .noneMatch(String::isBlank)) {
            recipe.setIngredients(ingredients);
        }

        if (directions != null &&
                directions.length > 0 &&
                Arrays.stream(recipe.getDirections())
                        .noneMatch(String::isBlank)) {
            recipe.setDirections(directions);
        }

        recipe.setDate(String.valueOf(date));
    }

    public List<Recipe> searchRecipesByNameOrCategory(String name, String category) {
        if (name != null && name.length() > 0) {
            List<Recipe> byNameIgnoreCaseContaining = recipeRepository
                    .findByNameIgnoreCaseContaining(name);

            Collections.sort(byNameIgnoreCaseContaining);

            return byNameIgnoreCaseContaining;
        }

        if (category != null && category.length() > 0) {
            return recipeRepository
                    .findByCategoryIgnoreCaseOrderByDateDesc(category);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
