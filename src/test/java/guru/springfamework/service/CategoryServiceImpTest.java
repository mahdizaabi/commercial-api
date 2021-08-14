package guru.springfamework.service;

import guru.springfamework.api.v1.mapper.CategoryMapperImpl;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CategoryServiceImpTest {
    public static final Long ID = 2L;
    public static final String NAME = "Jimmy";
    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    CategoryMapperImpl categoryMapper = new CategoryMapperImpl();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
   categoryService = new CategoryServiceImp(categoryRepository, categoryMapper);
    }

    @Test
    public void getAllCategories() {
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());
        Mockito.when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();
        assertEquals(3, categoryDTOS.size());


    }

    @Test
    public void getCategoryByName() {

        Category category = new Category();
        category.setName("Banana");
        category.setId(1L);

        Mockito.when(categoryRepository.findCategoryByName(ArgumentMatchers.anyString())).thenReturn(category);
        CategoryDTO categoryDTO = categoryService.getCategoryByName("anyString");
        assertEquals(Long.valueOf(1), categoryDTO.getId());
        assertEquals("Banana", categoryDTO.getName());
    }
}