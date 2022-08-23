package guru.springframework.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeImageServiceImpl implements RecipeImageService {

	private final RecipeRepository recipeRepository;
	
	public RecipeImageServiceImpl(RecipeRepository recipeRepository) {
		super();
		this.recipeRepository = recipeRepository;
	}

	@Override
	public void saveImageFile(Long recipeId, MultipartFile file) {
		
		try {
			//Change to wrapped primitive type. Hybernate recomads this
			Byte[] recipeBytes = new Byte[file.getBytes().length];
			Recipe recipe = recipeRepository.findById(recipeId).get();
			
			for (int i=0; i < file.getBytes().length; i++) {
				recipeBytes[i] = file.getBytes()[i];
			}
			
			recipe.setImage(recipeBytes);
			recipeRepository.save(recipe);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("Save Image File...");
		System.out.println("Save Image File..." + file.toString());
		
	}

}
