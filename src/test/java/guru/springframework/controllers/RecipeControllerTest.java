package guru.springframework.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;
    
   //@InjectMocks
    RecipeController controller;
    
    MockMvc mockMvc;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    @Test
    public void mockMvcTest() throws Exception{
    	mockMvc.perform(get("/recipe/show/1"))
    		   .andExpect(status().isOk())
    		   .andExpect(view().name("/recipe/show"));
    		   
    }
    
    @Test
    public void getRecipePage() throws Exception {

        //given

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        String viewName = controller.showById("1", model);


        //then
        assertEquals("/recipe/show", viewName);
        verify(recipeService, times(1)).findById(anyLong());
        verify(model, times(1)).addAttribute(eq("recipe"), argumentCaptor.capture());
        Recipe returnedRecipe = argumentCaptor.getValue();
        assertEquals(1, (long)returnedRecipe.getId());
    }
}
