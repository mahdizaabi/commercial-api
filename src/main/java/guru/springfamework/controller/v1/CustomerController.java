package guru.springfamework.controller.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CategoryListDTO;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.service.CategoryService;
import guru.springfamework.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping({"/api/v1/customers/", "/api/v1/customers"})
public class CustomerController {
    static String BASE_URL = "/api/v1/customers/";
    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerListDTO> getAllCategories() {
        return new ResponseEntity<CustomerListDTO>(new CustomerListDTO(customerService.getAllCustomers())
                , HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id){
        return new ResponseEntity<CustomerDTO>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerDTO createedCustomerDTO = customerService.createNewCustomer(customerDTO);
        return new ResponseEntity<CustomerDTO>(createedCustomerDTO, HttpStatus.CREATED);
    }


    //PUT -> update and replace the whole resource with an updated version(keep the id)
    @PutMapping({"/{id}"})
    public ResponseEntity<CustomerDTO> updateCustomerPUT(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
        return new ResponseEntity<>(customerService.saveCustomerByDTO(id, customerDTO), HttpStatus.OK);
    }

    //Patch -> update the whole resource, eventhough only a singlepiece of data has changed

    @PatchMapping({"/{id}"})
    public ResponseEntity<CustomerDTO> updateCustomerPATCH(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
        return new ResponseEntity<>(customerService.patchCustomer(id, customerDTO), HttpStatus.OK);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        try{
            customerService.deleteCustomer(id);

        }catch(Exception exc) {
            System.out.println("No");
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
