package guru.springframework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecipeController {

	private final RecipeService recipeService;
	
	@Autowired//not needed but i put here because this is a tutorial
	public RecipeController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}
	
	@GetMapping
	@RequestMapping("/recipe/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		
		Long lId = Long.valueOf(id);
		
		model.addAttribute("recipe", recipeService.findById(lId));
		return "/recipe/show";
	}
	
	@GetMapping
	@RequestMapping("/recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		
		return "recipe/recipeform";
	}
	
	@GetMapping
	@RequestMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		Long lId = Long.valueOf(id);
		model.addAttribute("recipe", recipeService.findRecipeCommandById(lId));
		
		return "recipe/recipeform";
	}
	
	@GetMapping// here is get because this is the way the http form cannot do DELETE
	@RequestMapping("/recipe/{id}/delete")
	public String deleteRecipe(@PathVariable String id) {
		Long lId = Long.valueOf(id);
		
		recipeService.deleteById(lId);
			
		return "redirect:/";
	}
	
	//@ModelAttribute this will tell spring to bind the form POST attributes to the RecipeCommand
	@PostMapping
	@RequestMapping("/recipe")
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
		
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
		
		//This will tell Spring to redirect a new url
		return String.format("redirect:/recipe/%d/show", savedCommand.getId());
	}
}
