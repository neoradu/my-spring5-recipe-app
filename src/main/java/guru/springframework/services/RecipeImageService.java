package guru.springframework.services;

import org.springframework.web.multipart.MultipartFile;

public interface RecipeImageService {

	void saveImageFile(Long recipeId, MultipartFile file);
}
