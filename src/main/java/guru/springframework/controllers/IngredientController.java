package guru.springframework.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;


@Controller
public class IngredientController {

	final private RecipeService recipeService;
	
	@Autowired
	public IngredientController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}
	
	@GetMapping
	@RequestMapping("/recipe/{id}/ingredients")
	public String getIngredients(@PathVariable String id, Model model) {
		Long idL = Long.valueOf(id);
		Recipe recipe = recipeService.findById(idL);
		model.addAttribute("recipe", recipe);
		
		return String.format("/recipe/ingredients/list", idL);
	}
}
