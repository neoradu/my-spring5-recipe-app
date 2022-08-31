package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * Created by jt on 6/19/17.
 */
@Slf4j
@Controller
public class RecipeController {
	private final String RECIPE_FORM = "recipe/recipeform";
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){

        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());

        return RECIPE_FORM;
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return  RECIPE_FORM;
    }

    //@Validated -- Variant of JSR-303's javax.validation.Valid, supporting the specification of validation groups.
    //Designed for convenient use with Spring's JSR-303 support but not JSR-303 specific.
    @PostMapping("recipe")
    public String saveOrUpdate(@Validated @ModelAttribute RecipeCommand command,
    						   BindingResult bindingResult, Model model){
    	
    	if(bindingResult.hasErrors()) {
    		bindingResult.getAllErrors()
    		             .forEach(err -> log.debug(String.format("Error on recipe form:%s", err)));
    		
    		model.addAttribute("recipe", command);
    		return RECIPE_FORM;
    	}
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id){

        log.debug("Deleting id: " + id);

        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
    
    //@ResponseStatus(HttpStatus.NOT_FOUND)//this is put here because @ExceptionHandler 200 is taking a higher  presidency  
/*    @ExceptionHandler(value = NotFoundException.class)
    public ModelAndView handle404(Exception ex) {
    	log.error("Handling 404 error!");
    	log.error("Exception received:" + ex.toString());
    	ModelAndView mav = new ModelAndView();

    	mav.addObject("exception",ex);
		mav.setViewName("404error");

    	
    	return mav;
    }*/
}
