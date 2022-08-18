package guru.springframework.services;

import guru.springframework.domain.Recipe;

import java.util.Set;

/**
 * Created by jt on 6/13/17.
 */
public interface RecipeService {

    public Set<Recipe> getRecipes();
    public Recipe findById(Long id);
}
