package guru.springfamework.service;

import guru.springfamework.api.v1.mapper.CategoryMapperImpl;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {


    CategoryRepository categoryRepository;
    CategoryMapperImpl categoryMapper;

    public CategoryServiceImp(CategoryRepository categoryRepository,
                              CategoryMapperImpl categoryMapper) {

        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }


    @Override
    public List<CategoryDTO> getAllCategories() {
       return categoryRepository.findAll()
               .stream()
               .map(categoryMapper::categoryToCategoryDTO)
               .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryByName(String name) throws ResourceNotFoundException {

        Category category = categoryRepository.findCategoryByName(name);
        if(category == null){
           throw new ResourceNotFoundException();
        }
        return categoryMapper.categoryToCategoryDTO(category);
    }
}
