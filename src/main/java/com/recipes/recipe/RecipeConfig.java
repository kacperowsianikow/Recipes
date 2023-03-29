package com.recipes.recipe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class RecipeConfig {
//    private final LocalDateTime date = LocalDateTime.now();
//
//    @Bean
//    CommandLineRunner commandLineRunner(RecipeRepository repository) {
//        return args -> {
//            Recipe freshMintTea = new Recipe(
//                    "Fresh Mint Tea",
//                    "beverage",
//                    "Light, aromatic and refreshing beverage, ...",
//                    new String[]{
//                            "boiled water",
//                            "honey",
//                            "fresh mint leaves"
//                    },
//                    new String[]{
//                            "Boil water",
//                            "Pour boiling hot water into a mug",
//                            "Add fresh mint leaves",
//                            "Mix and let the mint leaves seep for 3-5 minutes",
//                            "Add honey and mix again"
//                    }
//            );
//            freshMintTea.setDate(String.valueOf(date));
//
//            Recipe scrambledEggs = new Recipe(
//                    "Scrambled Eggs",
//                    "meal",
//                    "Perfect morning meal",
//                    new String[]{
//                            "eggs",
//                            "butter",
//                            "salt"
//                    },
//                    new String[]{
//                            "Beat and season eggs",
//                            "Melt butter on the pan",
//                            "Pour eggs on hot pan",
//                            "When eggs will start to come together your meal is ready"
//                    }
//            );
//            scrambledEggs.setDate(String.valueOf(date));
//
//            repository.saveAll(
//                    List.of(freshMintTea, scrambledEggs)
//            );
//
//        };
//    }
}
