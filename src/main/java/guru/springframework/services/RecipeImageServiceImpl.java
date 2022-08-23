package guru.springframework.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeImageServiceImpl implements RecipeImageService {

	@Override
	public void saveImageFile(Long recipeId, MultipartFile file) {
		log.debug("Save Image File...");
		System.out.println("Save Image File..." + file.toString());
		
	}

}
