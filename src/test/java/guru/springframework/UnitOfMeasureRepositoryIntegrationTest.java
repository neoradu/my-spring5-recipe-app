package guru.springframework;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;

@RunWith(SpringRunner.class)
@DataJpaTest//this will bring data jpa context
public class UnitOfMeasureRepositoryIntegrationTest {

	@Autowired// this dependency will be injected by spring
	UnitOfMeasureRepository repository;
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void findByDescription() throws Exception {
		Optional<UnitOfMeasure> result = repository.findByDescription("Cup");
		assertEquals("Cup",result.get().getDescription());
	}
	
}
