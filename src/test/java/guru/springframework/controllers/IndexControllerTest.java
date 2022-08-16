package guru.springframework.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;

public class IndexControllerTest {

	@Mock
	private RecipeService recipeService;
	@Mock
	private Model model;
	
	private IndexController indexController;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);//initialize our mocks
		
		indexController = new IndexController(recipeService);
	}
	
	@Test
	public void getIndexPageTest() {
		//given
		Set<Recipe> recipes = new HashSet<>();
		recipes.add(new Recipe());
		when(recipeService.getRecipes()).thenReturn(recipes) ;
		
		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
		
		//when
		String returnVal = indexController.getIndexPage(model);
		
		//then
		//verify returned string
		assertEquals("index" , returnVal);
		//verify that getRecipes() is called once
		verify(recipeService, times(1)).getRecipes();
		
		//capture what is passed to the Model and verify that
		verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
		Set<Recipe> caturedSet = argumentCaptor.getValue();
		assertEquals(caturedSet.size() , 1);
		
	}
	
	
}
