package guru.springframework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;

@Service
public class IngredientServiceImpl implements IngredientService {

	private final RecipeService recipeService;
	private final IngredientToIngredientCommand iToicmdConverter;
	@Autowired
	public IngredientServiceImpl(RecipeService recipeService, IngredientToIngredientCommand iToicmdConverter) {
		super();
		this.recipeService = recipeService;
		this.iToicmdConverter = iToicmdConverter;
	}

	@Override
	public IngredientCommand getByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		Recipe recipe = recipeService.findById(recipeId);
		Ingredient retIngredient = null;
		if(recipe.getIngredients() != null) {
			retIngredient = recipe.getIngredients()
			      .stream()
			      .filter(ingredient -> ingredient.getId() == ingredientId)
			      .findFirst().get();
		}
		
		return iToicmdConverter.convert(retIngredient);
	}

}
