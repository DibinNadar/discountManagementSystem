package discountManagementSystem.api;

import discountManagementSystem.entity.Coupon;
import discountManagementSystem.entity.Customer;
import discountManagementSystem.repository.CouponRepository;
import discountManagementSystem.repository.CustomerRepository;
import discountManagementSystem.assembler.CustomerModelAssembler;
import discountManagementSystem.customException.exception.CustomerNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
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
        Coupon customerCoupon = new Coupon();
        BeanUtils.copyProperties(couponRepository.findById(couponId).get(),customerCoupon);
        customer.coupons.add(customerCoupon);
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
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException(customerId));
        Collection<Coupon> coupons = customer.getCoupons();
        return coupons;
    }

    // TODO: AHH the Mistake, mostly to be added to Voucher
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    @PutMapping("customers/{customerId}/customer_coupons/{couponId}")
//    HttpStatus updateCustomerCoupon(@Valid @RequestBody Coupon newCoupon,  @PathVariable Long customerId , @PathVariable String couponId){
////    ResponseEntity<?> updateCustomerCoupon(@Valid @RequestBody Coupon newCoupon,  @PathVariable Long customerId , @PathVariable String couponId){
//
//        // where do I put this findByCustomerAndCouponId () and how do I use it
//        // basically maintain unique customer coupon records
//        int i =0;
//        Collection<Coupon> coupons = customerRepository.findById(customerId).get().getCoupons();
//
//
//        Coupon updatedCoupon = null;
//        for (Coupon c : coupons ){
//            if (c.getCouponId().equals(couponId)){
//                updatedCoupon = c;
//                coupons.remove(c);
//            }
//        }
//
//        if (updatedCoupon!= null && updatedCoupon.getCouponId().equals(couponId)){
////            BeanUtils.copyProperties(newCoupon,updatedCoupon);  // TODO: Test it
//            updatedCoupon.setCouponId(newCoupon.getCouponId());
//            updatedCoupon.setPercentageDiscount(newCoupon.getPercentageDiscount());
//            updatedCoupon.setUpperAmountLimit(newCoupon.getUpperAmountLimit());
//            updatedCoupon.setGlobalUsageLimit(newCoupon.getGlobalUsageLimit());
//            updatedCoupon.setStartDate(newCoupon.getStartDate());
//            updatedCoupon.setExpiryDate(newCoupon.getExpiryDate());
//            updatedCoupon.setCustomers(newCoupon.getCustomers());
//
//            coupons.add(updatedCoupon);
//
//            customerRepository.findById(customerId).get().setCoupons((Set<Coupon>) coupons);
//        }
//
//        System.out.println("Updated Coupon :" + updatedCoupon);
//
//        System.out.println("Change"+customerRepository.findById(customerId).get());
//
//        return HttpStatus.OK;
//    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
                    BeanUtils.copyProperties(newCustomer,customer);
                    return customerRepository.save(customer);
                })
                .orElseGet(()->{
                    System.out.println("get");
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