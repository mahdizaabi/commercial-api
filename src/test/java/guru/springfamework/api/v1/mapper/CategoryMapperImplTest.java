package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

public class CategoryMapperImplTest {
    public static final String NAME = "mahdi";
    public static final long ID = 1L;
    CategoryMapperImpl categoryMapper = new CategoryMapperImpl();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void categoryToCategoryDTO() {
        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);

        CategoryDTO catDTO = categoryMapper.categoryToCategoryDTO(category);
        assertEquals(NAME, catDTO.getName());
        assertEquals(Long.valueOf(ID), catDTO.getId());

    }
}