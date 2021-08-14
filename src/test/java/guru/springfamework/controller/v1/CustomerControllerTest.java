package guru.springfamework.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.service.CustomerService;
import net.bytebuddy.matcher.ElementMatchers;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class CustomerControllerTest {
    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testListCustomers() throws Exception {

        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstname("Michale");
        customer1.setLastname("Weston");
        customer1.setCustomerUrl("/api/v1/customer/1");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setFirstname("Sam");
        customer2.setLastname("Axe");
        customer2.setCustomerUrl("/api/v1/customer/2");

        Mockito.when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customers", Matchers.hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception {

        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstname("Michale");
        customer1.setLastname("Weston");
        customer1.setCustomerUrl("/api/v1/customer/1");

        Mockito.when(customerService.getCustomerById(ArgumentMatchers.anyLong())).thenReturn(customer1);

        //when
        mockMvc.perform(get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", CoreMatchers.equalTo("Michale")));
    }

    @Test
    public void testCreateNewCustomer() throws Exception {
        CustomerDTO incomingCustomerDTO = new CustomerDTO();
        incomingCustomerDTO.setId(1L);
        incomingCustomerDTO.setFirstname("test");
        incomingCustomerDTO.setLastname("tteesstt");

        CustomerDTO ReturnedcustomerDTO = new CustomerDTO();
        ObjectMapper objectMapper = new ObjectMapper();
        ReturnedcustomerDTO.setId(1L);
        ReturnedcustomerDTO.setFirstname("test");
        ReturnedcustomerDTO.setLastname("tteesstt");
        ReturnedcustomerDTO.setCustomerUrl("/api/v1/customers/1");


        Mockito.when(customerService.createNewCustomer(ArgumentMatchers.any(CustomerDTO.class)))
                .thenReturn(ReturnedcustomerDTO);
        mockMvc.perform(post("/api/v1/customers/").content(
                objectMapper.writeValueAsString(incomingCustomerDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", CoreMatchers.equalTo("test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer_url", CoreMatchers.equalTo("/api/v1/customers/1")));
    }

    @Test
    public void updateCustomerTest() throws Exception {
        CustomerDTO incomingCustomerDTO = new CustomerDTO();
        incomingCustomerDTO.setFirstname("Cristina");
        incomingCustomerDTO.setLastname("Coutier");

        CustomerDTO ReturnedcustomerDTO = new CustomerDTO();
        ObjectMapper objectMapper = new ObjectMapper();
        ReturnedcustomerDTO.setId(1L);
        ReturnedcustomerDTO.setFirstname("Cristina");
        ReturnedcustomerDTO.setLastname("Coutier");
        ReturnedcustomerDTO.setCustomerUrl("/api/v1/customers/1");

        Mockito.when(customerService.saveCustomerByDTO(ArgumentMatchers.anyLong(),
                ArgumentMatchers.any(CustomerDTO.class))).thenReturn(ReturnedcustomerDTO);

        mockMvc.perform(put("/api/v1/customers/1")
                .content(objectMapper.writeValueAsString(incomingCustomerDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", CoreMatchers.equalTo("Cristina")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer_url", CoreMatchers.equalTo("/api/v1/customers/1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.equalTo(1)));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/v1/customers/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    Mockito.verify(customerService).deleteCustomer(ArgumentMatchers.anyLong());
    }

}
