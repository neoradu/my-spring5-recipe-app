package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeImageService;
import guru.springframework.services.RecipeService;

@Controller
public class RecipeImageController {
	
	private final RecipeService recipeService;
	private final RecipeImageService recipeImageService;
	
	public RecipeImageController(RecipeService recipeService, RecipeImageService recipeImageService) {
		super();
		this.recipeService = recipeService;
		this.recipeImageService = recipeImageService;
	}

	@GetMapping("/recipe/{recipeId}/image")
	public String ChangeImage(@PathVariable String recipeId, Model model) {
		
		Recipe recipe = recipeService.findById(Long.valueOf(recipeId));
		model.addAttribute("recipe", recipe);
		
		return "/recipe/imageuploadform";
	}
	
	@PostMapping("/recipe/{recipeId}/image")
	public String uploadImage(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
		
		recipeImageService.saveImageFile(Long.valueOf(recipeId), file);
		return String.format("redirect:/recipe/%s/show", recipeId);
	}

}
