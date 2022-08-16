package guru.springframework.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

public class RecipeServiceImplTest {

	private RecipeServiceImpl recipeService;
	
	@Mock
	private RecipeRepository recipeRepository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);//initialize our mocks
		
		recipeService = new RecipeServiceImpl(recipeRepository);
	}
	
	@Test
	public void getRecipies() throws Exception {
		Set<Recipe> expectedRecipes = new HashSet<>();
		expectedRecipes.add(new Recipe());
		
		when(recipeService.getRecipes()).thenReturn(expectedRecipes) ;
		
		
		Set<Recipe> recipes = recipeService.getRecipes();
		
		assertEquals(1 , recipes.size());
	}
}
