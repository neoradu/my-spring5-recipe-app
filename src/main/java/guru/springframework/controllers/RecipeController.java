package guru.springframework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.services.RecipeService;

@Controller
public class RecipeController {

	private final RecipeService recipeService;
	
	@Autowired//not needed but i put here because this is a tutorial
	public RecipeController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}
	@RequestMapping("/recipe/show/{id}")
	public String showById(@PathVariable String id, Model model) {
		
		Long lId = Long.valueOf(id);
		
		model.addAttribute("recipe", recipeService.findById(lId));
		return "/recipe/show";
	}
}
