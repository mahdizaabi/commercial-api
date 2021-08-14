package guru.springfamework.service;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.mapper.CustomerMapperImpl;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpPlus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;


    @Autowired(required = true)
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerServiceImpl() {

    }

    public CustomerServiceImpl(CustomerMapperImpl customerMapper) {
        this.customerMapper = customerMapper;
    }


    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(item -> {
                    CustomerDTO cDTO = customerMapper.customerToCustomerDTO(item);
                    cDTO.setCustomerUrl("api/v1/customer/" + item.getId());
                    return cDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) throws NumberFormatException {
        Customer customer = customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        customerDTO.setCustomerUrl("/api/v1/customers/" + id);
        return customerDTO;
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        //return new CustomerTDO wwith the customer url!!!

        CustomerDTO returnedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        returnedCustomerDTO.setCustomerUrl("/api/v1/customer/" + returnedCustomerDTO.getId());
        return returnedCustomerDTO;
    }

    private CustomerDTO saveAndReturnValidDTO(Customer customer) {
        Customer customer1 = customerRepository.save(customer);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer1);
        customerDTO.setCustomerUrl("/api/v1/customers/" + customerDTO.getId());
        return customerDTO;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);
        return saveAndReturnValidDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        Customer customerToPatch = customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (customerDTO.getFirstname() != null) {
            customerToPatch.setFirstname(customerDTO.getFirstname());
        }
        if (customerDTO.getLastname() != null) {
            customerToPatch.setLastname(customerDTO.getLastname());
        }
        return customerMapper.customerToCustomerDTO(customerRepository.save(customerToPatch));
    }
    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        customerRepository.deleteById(id);
    }

    private String GenerateCustomerUrl(Long id){
        String BASE_URL = "/api/v1/customers/";
        return (BASE_URL + id);
    }
}
