package guru.springfamework.service;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryByName(String name);
}
