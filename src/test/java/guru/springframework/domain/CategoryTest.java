package guru.springframework.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CategoryTest {
	
	private Category category;
	
	@Before
	public void setUp() {
		category = new Category();
		
	}
	
	@Test
	public void testGetId() {
		Long id = 5L;
		category.setId(id);
		
		assertEquals(id ,category.getId());

	}

}
