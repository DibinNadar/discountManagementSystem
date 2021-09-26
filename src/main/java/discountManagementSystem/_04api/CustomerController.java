package discountManagementSystem._04api;

import discountManagementSystem._01entity.Coupon;
import discountManagementSystem._02repository.CouponRepository;
import discountManagementSystem.assembler.CustomerModelAssembler;
import discountManagementSystem._02repository.CustomerRepository;
import discountManagementSystem._01entity.Customer;
import discountManagementSystem.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
//@RequestMapping("/") Should do this probably
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;  // Ideally service layer between controller and repo

    @Autowired
    private CouponRepository couponRepository;

    private CustomerModelAssembler customerModelAssembler;

    public CustomerController(CustomerRepository customerRepository, CustomerModelAssembler customerModelAssembler) {
        this.customerRepository = customerRepository;
        this.customerModelAssembler = customerModelAssembler;
    }

    @RequestMapping("/customer_home")
    public String getHome(){
        return "This is the Customer Home Page";
    }

//    @PostMapping("/customer") // TODO setMany()

    @GetMapping("/customers")
    public CollectionModel<EntityModel<Customer>> getAll(){

        List<EntityModel<Customer>> customerList =
                customerRepository
                        .findAll()
                        .stream()
                        .map(customerModelAssembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(customerList,
                linkTo(methodOn(CustomerController.class).getAll()).withSelfRel());

    }

    @PutMapping("customers/{customerId}/coupons/{couponId}")
    Customer addCouponToCustomer(@PathVariable String couponId, @PathVariable Long customerId) {
        Customer customer = customerRepository.findById(customerId).get();
        Coupon coupon = couponRepository.findById(couponId).get();
        customer.coupons.add(coupon);
        return customerRepository.save(customer);
    }

    @GetMapping("/customers/{customerId}")
    public EntityModel<Customer> getOne(@PathVariable Long customerId){

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException(customerId));

        return customerModelAssembler.toModel(customer);
    }

// TODO: Make it return Links
    @GetMapping("customers/{customerId}/coupons")
    public Collection<Coupon> getCustomerCoupons( @PathVariable Long customerId){
        Customer customer = customerRepository.findById(customerId).get();
        Collection<Coupon> coupons = customer.getCoupons();

        return coupons;
    }

    @PostMapping("/customers")
    ResponseEntity<?> addNewCustomer (@Valid @RequestBody Customer newCustomer){

        EntityModel<Customer> entityModel = customerModelAssembler.toModel(customerRepository.save(newCustomer));

        return ResponseEntity.
                created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/customers/{customerId}")
    ResponseEntity<?> updateCustomer(@Valid @RequestBody Customer newCustomer, @PathVariable Long customerId){

        Customer updatedCustomer = customerRepository.findById(customerId)
                .map(customer -> {
                    customer.setCustomerId(newCustomer.getCustomerId());
                    customer.setName(newCustomer.getName());
                    customer.setCoupons((Set<Coupon>) newCustomer.getCoupons());
                    return customerRepository.save(newCustomer);
                })
                .orElseGet(()->{
                    newCustomer.setCustomerId(customerId);
                    return customerRepository.save(newCustomer);
                });

        EntityModel<Customer> entityModel = customerModelAssembler.toModel(updatedCustomer);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }

    @DeleteMapping("/customers/{customerId}")
    ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        customerRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
