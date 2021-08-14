package guru.springfamework.service;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.mapper.CustomerMapperImpl;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.Arrays;
import java.util.List;

public class CustomerServiceTest {
    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService = new CustomerServiceImpl(new CustomerMapperImpl());

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // customerService = new CustomerServiceImpl(customerMapper, customerRepository);
    }

    @Test
    public void getAllCustomers() throws Exception {
        //given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstname("Cristina");
        customer1.setLastname("Martin");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstname("Alexa");
        customer2.setLastname("Axe");

        Mockito.when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        //when
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        //then
        Assert.assertEquals(2, customerDTOS.size());

    }


    @Test
    public void createNewCustomer() throws Exception {

        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Cristina");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setId(1L);

        Mockito.when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);
        System.out.println(savedDto);
        //then
        Assert.assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
        Assert.assertEquals("/api/v1/customer/1", savedDto.getCustomerUrl());
        Assert.assertEquals("Cristina", savedDto.getFirstname());

    }

    @Test
    public void saveCustomerByDTO(){
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Jim");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(1l);

        Mockito.when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDto = customerService.saveCustomerByDTO(1L, customerDTO);

        //then
        Assert.assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
        Assert.assertEquals("/api/v1/customers/1", savedDto.getCustomerUrl());

    }

    @Test
    public void deleteCustomerByIdTest(){
        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname("customerDTO.getFirstname()");
        savedCustomer.setLastname("customerDTO.getLastname()");
        savedCustomer.setId(1L);
        Long id = 1L;
        Mockito.when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.of(savedCustomer));
        customerService.deleteCustomer(id);
        Mockito.verify(customerRepository, Mockito.times(1)).deleteById(anyLong());
    }



}
