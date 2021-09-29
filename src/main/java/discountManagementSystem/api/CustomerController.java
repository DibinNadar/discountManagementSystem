package discountManagementSystem.api;

import discountManagementSystem.assembler.CustomerModelAssembler;
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
    public EntityModel<Customer> getOne(@PathVariable Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        return customerModelAssembler.toModel(customer);
    }

    @GetMapping("/customers")
    public CollectionModel<EntityModel<Customer>> getAll() {

        List<EntityModel<Customer>> customerList =
                customerRepository
                        .findAll()
                        .stream()
                        .map(customerModelAssembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(customerList,
                linkTo(methodOn(CustomerController.class).getAll()).withSelfRel());

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

    @DeleteMapping("/customer/{customerId}")  // TODO: Enable delete
    ResponseEntity<?> deleteCustomer(@PathVariable Long customerId) {customerRepository.deleteById(customerId);return ResponseEntity.noContent().build();}

/**************************************************************************************************************/

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
/**************************************************************************************************************/
//    @Autowired
//    private VoucherRepository voucherRepository;

//    @PutMapping("customer/{customerId}/voucher/{voucherId}")
//    Customer addVoucherToCustomer(@PathVariable String voucherId, @PathVariable Long customerId) {
//        Customer customer = customerRepository.findById(customerId).get();
//        Voucher customerVoucher = new Voucher();
//        BeanUtils.copyProperties(voucherRepository.findById(voucherId).get(), customerVoucher);
//        customer.vouchers.add(customerVoucher);
//        return customerRepository.save(customer);
//    }


//    // TODO: Make it return Links
//    @GetMapping("customer/{customerId}/vouchers")
//    public Collection<Voucher> getCustomerVouchers(@PathVariable Long customerId) {
//        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
//        Collection<Voucher> vouchers = customer.getVouchers();
//        return vouchers;
//    }
/**************************************************************************************************************/

    // TODO:  vocher custoer extra fields


}