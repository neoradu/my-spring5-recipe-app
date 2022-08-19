package guru.springframework.services;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final RecipeService recipeService;
	private final RecipeRepository recipeRepository;
	private final IngredientToIngredientCommand iToicmdConverter;
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	
	@Autowired
    public IngredientServiceImpl(RecipeService recipeService, RecipeRepository recipeRepository,
			IngredientToIngredientCommand iToicmdConverter, UnitOfMeasureRepository unitOfMeasureRepository,
			IngredientCommandToIngredient ingredientCommandToIngredient) {
		super();
		this.recipeService = recipeService;
		this.recipeRepository = recipeRepository;
		this.iToicmdConverter = iToicmdConverter;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
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
	


	@Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if(!recipeOptional.isPresent()){

            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            //to do check for fail
            return iToicmdConverter.convert(savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst()
                    .get());
        }

    }
	@Transactional
	@Override
	public void deleteByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(!recipeOptional.isPresent()){

            //todo toss error if not found!
            log.error("Recipe not found for id: " + recipeId);
        } else {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();
            if(ingredientOptional.isPresent()) {
            	//!!!! Remove the inverse relationship. This will tell hibernate to remove it from the database
            	ingredientOptional.get().setRecipe(null);
            	recipe.getIngredients().remove(ingredientOptional.get());
            	//recipe.setIngredients(new HashSet<>());
            	Recipe savedRecipe = recipeRepository.save(recipe);

            	/*System.out.println(String.format("Ingredient %d  from recipe %d deleted ingredients emty:%b",
		                 ingredientOptional.get().getId(), savedRecipe.getId(),savedRecipe.getIngredients().isEmpty()));
		                 */
            } else {
                //todo toss error if not found!
                log.error(String .format("Ingredient id:%d not found in Recipe id:%d",ingredientId, recipeId));
            } 
            
        }
	}

}
