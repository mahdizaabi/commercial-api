package guru.springfamework.controller.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;


public class CategoryControllerTest {

    @Mock
    CategoryService categoryService;


    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new ResponseExceptionHandler())
                .build();

    }

    @Test
    public void getAllCategories() throws Exception {
        CategoryDTO category1 = new CategoryDTO();
        category1.setId(1L);
        category1.setName("Nissan");

        CategoryDTO category2 = new CategoryDTO();
        category2.setId(2L);
        category2.setName("Golf");

        List<CategoryDTO> categories = Arrays.asList(category1, category2);

        Mockito.when(categoryService.getAllCategories()).thenReturn(categories);
        mockMvc.perform(get("/api/v1/categories/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));
    }

    @Test
    public void getByName() throws Exception {
        CategoryDTO category1 = new CategoryDTO();
        category1.setId(1L);
        category1.setName("Nissan");
        Mockito.when(categoryService.getCategoryByName(ArgumentMatchers.anyString())).thenReturn(category1);
        mockMvc.perform(get("/api/v1/categories/cccc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Nissan")));
    }
    @Test
    public void NOT_FOUND_ERROR_HADNLING() throws Exception {
        CategoryDTO category1 = new CategoryDTO();
        mockMvc.perform(get("/api/v1/categories/lkkkllkkl")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}