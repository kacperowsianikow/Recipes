package com.recipes.recipe;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/api/recipe")
    public List<Recipe> getRecipes() {
        return recipeService.getRecipes();
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        return recipeService.getOneRecipe(id);
    }

    @PostMapping("/api/recipe/new")
    public Long addNewRecipe(@RequestBody Recipe recipe) {
        return recipeService.addNewRecipe(recipe);
    }

    @DeleteMapping("api/recipe/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable("id") Long id) {
        recipeService.deleteRecipe(id);
    }

    @PutMapping("/api/recipe/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRecipe(
            @PathVariable("id") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String[] ingredients,
            @RequestParam(required = false) String[] directions) {
        recipeService.updateRecipe(
                id,
                name,
                category,
                description,
                ingredients,
                directions
        );
    }

    @GetMapping("/api/recipe/search/")
    public List<Recipe> searchRecipesByNameOrCategory(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category) {
        return recipeService
                .searchRecipesByNameOrCategory(name, category);
    }

}
