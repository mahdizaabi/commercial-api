package guru.springfamework.service;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.mapper.CustomerMapperImpl;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.assertj.core.condition.Not;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceIntegrationTest {

    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    private CustomerMapper customerMapper = new CustomerMapperImpl();

    @Autowired
    private CategoryRepository categoryRepository;
    @Before
    public void setUp() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run();
        customerService = new CustomerServiceImpl(customerRepository, customerMapper);
    }

    @Test
    public void patchCustomerUpdateFirstName() throws Exception {
        String updatedName = "UpdatedName";
        long id = 1;

        Customer originalCustomer = customerRepository.getOne(id);
        Assert.assertNotNull(originalCustomer);
        //save original first name
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        Assert.assertNotNull(updatedCustomer);
        Assert.assertEquals(updatedName, updatedCustomer.getFirstname());
        Assert.assertThat(originalLastName, CoreMatchers.equalTo(updatedCustomer.getLastname()));
        Assert.assertThat(originalFirstName, Matchers.not(Matchers.equalTo(updatedCustomer.getFirstname())));

    }

    @Test
    public void patchCustomerUpdateLastName() throws Exception {
        String updatedName = "UpdatedName";
        long id = 1;

        Customer originalCustomer = customerRepository.getOne(id);
        Assert.assertNotNull(originalCustomer);

        //save original first/last name
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        Assert.assertNotNull(updatedCustomer);
        Assert.assertEquals(updatedName, updatedCustomer.getLastname());
        Assert.assertThat(originalFirstName, CoreMatchers.equalTo(updatedCustomer.getFirstname()));
        Assert.assertThat(originalLastName, Matchers.not(Matchers.equalTo(updatedCustomer.getLastname())));
    }


    private Long getCustomerIdValue(){
        List<Customer> customers = customerRepository.findAll();

        System.out.println("Customers Found: " + customers.size());

        //return first id
        return customers.get(0).getId();
    }
}
