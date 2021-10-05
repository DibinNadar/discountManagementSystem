package discountManagementSystem.api.primary;

import discountManagementSystem.assembler.CustomerModelAssembler;
import discountManagementSystem.customException.exception.CouponNotFoundException;
import discountManagementSystem.customException.exception.CustomerNotFoundException;
import discountManagementSystem.entity.Customer;
import discountManagementSystem.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;  // Ideally service layer between controller and repo

    private CustomerModelAssembler customerModelAssembler;

    public CustomerController(CustomerRepository customerRepository, CustomerModelAssembler customerModelAssembler) {
        this.customerRepository = customerRepository;
        this.customerModelAssembler = customerModelAssembler;
    }

    @RequestMapping("/customer/home")
    public String getHome() {
        return "This is the Customer Home Page";
    }

    @PostMapping("/customer")
    ResponseEntity<?> addNewCustomer(@Valid @RequestBody Customer newCustomer) {

        EntityModel<Customer> entityModel = customerModelAssembler.toModel(customerRepository.save(newCustomer));

        return ResponseEntity.
                created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PostMapping("/customers")
    List<EntityModel<Customer>> addMultipleNewCustomers(@Valid @RequestBody Customer[] customerArrayList) {

        List<EntityModel<Customer>> response = new ArrayList<>();

        for (Customer customer : customerArrayList) {
            response.add(customerModelAssembler.toModel(customerRepository.save(customer)));
        }
        return response;
    }

    @GetMapping("/customer/{customerId}")
//    public Customer findById(@PathVariable Long customerId) {
    public EntityModel<Customer> findById(@PathVariable Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

//        return customer;
        return customerModelAssembler.toModel(customer);
    }

    @GetMapping("/customers")
    public CollectionModel<EntityModel<Customer>> findAll() {

        List<EntityModel<Customer>> customerList =
                customerRepository
                        .findAll()
                        .stream()
                        .map(customerModelAssembler::toModel)
                        .collect(Collectors.toList());

        if (customerList.isEmpty()){
            throw new CouponNotFoundException(null);
        }

        return CollectionModel.of(customerList,
                linkTo(methodOn(CustomerController.class).findAll()).withSelfRel());

    }

    @PutMapping("/customer/{customerId}")
    ResponseEntity<?> updateCustomer(@Valid @RequestBody Customer newCustomer, @PathVariable Long customerId) {

        Customer updatedCustomer = customerRepository.findById(customerId)
                .map(customer -> {
                    newCustomer.setCoupons(customer.getCoupons());
                    BeanUtils.copyProperties(newCustomer, customer);
                    return customerRepository.save(customer);
                })
                .orElseGet(() -> {
                    newCustomer.setCustomerId(customerId);
                    return customerRepository.save(newCustomer);
                });

        EntityModel<Customer> entityModel = customerModelAssembler.toModel(updatedCustomer);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

//    @DeleteMapping("/customer/{customerId}")  //  Enable delete
//    ResponseEntity<?> deleteCustomer(@PathVariable Long customerId) {customerRepository.deleteById(customerId);return ResponseEntity.noContent().build();}

}