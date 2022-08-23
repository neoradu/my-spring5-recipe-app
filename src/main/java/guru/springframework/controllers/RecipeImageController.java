package guru.springframework.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.commands.RecipeCommand;
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
	
	@GetMapping("/recipe/{recipeId}/recipeimage")
	public void renderImageFromDb(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
		Recipe recipe = recipeService.findById(Long.valueOf(recipeId));
		if(recipe.getImage() == null)
			return;
		
		byte[] byteArray = new byte[recipe.getImage().length];
		
		for(int i = 0; i < recipe.getImage().length; i ++)
			byteArray[i] = recipe.getImage()[i];
		
		response.setContentType("image/jpeg");
		InputStream is = new ByteArrayInputStream(byteArray);
		IOUtils.copy(is, response.getOutputStream());
		
	}
	
	@PostMapping("/recipe/{recipeId}/image")
	public String uploadImage(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
		
		recipeImageService.saveImageFile(Long.valueOf(recipeId), file);
		return String.format("redirect:/recipe/%s/show", recipeId);
	}

}
