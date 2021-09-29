package discountManagementSystem.api;

import discountManagementSystem.customException.exception.CouponNotFoundException;
import discountManagementSystem.customException.exception.CustomerCouponNotFoundException;
import discountManagementSystem.customException.exception.CustomerNotFoundException;
import discountManagementSystem.entity.Coupon;
import discountManagementSystem.entity.Customer;
import discountManagementSystem.repository.CouponRepository;
import discountManagementSystem.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class OfferController {


    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @PutMapping("/customer/{customerId}/coupon/{couponId}")
    Customer addCouponToCustomer(@PathVariable String couponId, @PathVariable Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->new CustomerNotFoundException(customerId));
        Coupon customerCoupon = new Coupon();
        BeanUtils.copyProperties(
                        couponRepository
                                .findById(couponId)
                                .orElseThrow(() ->new CouponNotFoundException(couponId)),
                        customerCoupon);
        customer.coupons.add(customerCoupon);
        return customerRepository.save(customer);
    }

    @GetMapping("/customer/{customerId}/coupons")
    public CollectionModel<Coupon> getCustomerCoupons(@PathVariable Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        Collection<Coupon> coupons = customer.getCoupons();

        return CollectionModel.of(coupons,
                linkTo(methodOn(CustomerController.class).getOne(customerId)).withRel(customer.getName()),
                linkTo(methodOn(OfferController.class).getCustomerCoupons(customerId)).withRel(customer.getName()+"'s Coupons"));
    }

    @DeleteMapping("/customer/{customerId}/coupon/{couponId}")  // TODO: Enable delete
    ResponseEntity<?> deleteCustomerCoupon(@PathVariable String couponId, @PathVariable Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException(customerId));
        Set<Coupon> couponSet = customer.getCoupons();
        Coupon targetCoupon = couponRepository.findById(couponId).orElseThrow(()->new CouponNotFoundException(couponId));

        if (couponSet.contains(targetCoupon)){
            customer.getCoupons().remove(targetCoupon);
            customerRepository.save(customer);
        }else {
            throw new CustomerCouponNotFoundException(customerId,couponId);
        }
        return ResponseEntity.noContent().build();
    }



}
