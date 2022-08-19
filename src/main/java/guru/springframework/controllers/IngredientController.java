package guru.springframework.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {

	final private RecipeService recipeService;
	final private IngredientService ingredientService;
	final private UnitOfMeasureService unitOfMeasureService;
	
	@Autowired
	public IngredientController(RecipeService recipeService, IngredientService ingredientService,
			                    UnitOfMeasureService unitOfMeasureService ) {
		super();
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
	}
	
	@GetMapping
	@RequestMapping("/recipe/{id}/ingredients")
	public String getIngredients(@PathVariable String id, Model model) {
		Long idL = Long.valueOf(id);
		Recipe recipe = recipeService.findById(idL);
		model.addAttribute("recipe", recipe);
		
		return String.format("/recipe/ingredients/list", idL);
	}
	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredients/{ingredientId}/show")
	public String showIngredient(@PathVariable String recipeId,
			                     @PathVariable String ingredientId,  Model model) {
		Long recipeIdL = Long.valueOf(recipeId);
		Long ingredientIdL = Long.valueOf(ingredientId);
		model.addAttribute("ingredientCmd", 
				           ingredientService.getByRecipeIdAndIngredientId(recipeIdL, ingredientIdL));
		
		return "/recipe/ingredients/show";
	}
    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredients/new")
    public String newRecipeIngredient(@PathVariable String recipeId,Model model) {
    	Set<UnitOfMeasure> allUnitsOfMeasure = unitOfMeasureService.listAllUoms();
    	UnitOfMeasure firstUoM = allUnitsOfMeasure.iterator().next();
    	UnitOfMeasureCommand uomCm = new UnitOfMeasureCommand();
    	uomCm.setId(firstUoM.getId());
    	uomCm.setDescription(firstUoM.getDescription());
    	
    	IngredientCommand iC = new IngredientCommand();
    	iC.setRecipeId(Long.valueOf(recipeId));
    	iC.setUom(uomCm);
        model.addAttribute("ingredient", iC);

        model.addAttribute("uomList", allUnitsOfMeasure);
        return "recipe/ingredients/ingredientform";
    }
    
    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredients/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String id, Model model){
        model.addAttribute("ingredient", ingredientService.getByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredients/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("saved receipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredients/" + savedCommand.getId() + "/show";
    }
    
    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredients/{ingredientId}/delete")
    public String deteleRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String ingredientId) {
		Long recipeIdL = Long.valueOf(recipeId);
		Long ingredientIdL = Long.valueOf(ingredientId);
		
    	ingredientService.deleteByRecipeIdAndIngredientId(recipeIdL, ingredientIdL);
    	
    	return String.format("redirect:/recipe/%d/ingredients", recipeIdL); 
    }
}
