package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

public interface IngredientService {

	IngredientCommand getByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

	IngredientCommand saveIngredientCommand(IngredientCommand command);
}
