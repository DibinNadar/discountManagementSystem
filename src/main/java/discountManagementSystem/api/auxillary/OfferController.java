package discountManagementSystem.api.auxillary;

import discountManagementSystem.api.primary.CouponController;
import discountManagementSystem.api.primary.CustomerController;
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
import org.springframework.hateoas.EntityModel;
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

    // TODO
//    @Autowired
//    private VoucherRepository voucherRepository;

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
    // TODO: Voucher addVoucherToCustomer
//    @PutMapping("customer/{customerId}/voucher/{voucherId}")
//    Customer addVoucherToCustomer(@PathVariable String voucherId, @PathVariable Long customerId) {
//        Customer customer = customerRepository.findById(customerId).get();
//        Voucher customerVoucher = new Voucher();
//        BeanUtils.copyProperties(voucherRepository.findById(voucherId).get(), customerVoucher);
//        customer.vouchers.add(customerVoucher);
//        return customerRepository.save(customer);
//    }


    @GetMapping("/customer/{customerId}/coupons")
    public CollectionModel<Coupon> getCustomerCoupons(@PathVariable Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        Collection<Coupon> coupons = customer.getCoupons();

        return CollectionModel.of(coupons,
                linkTo(methodOn(CustomerController.class).findById(customerId)).withRel(customer.getName()),
                linkTo(methodOn(OfferController.class).getCustomerCoupons(customerId)).withRel(customer.getName()+"'s Coupons"));
    }
    // TODO: Voucher getCustomerVoucher
//    @GetMapping("customer/{customerId}/vouchers")
//    public Collection<Voucher> getCustomerVouchers(@PathVariable Long customerId) {
//        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
//        Collection<Voucher> vouchers = customer.getVouchers();
//        return vouchers;
//    }

    @GetMapping("/customer/{customerId}/coupon/{couponId}")
    public EntityModel<Coupon> getOneCustomerCoupon(@PathVariable Long customerId, @PathVariable String couponId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(()->new CouponNotFoundException(couponId));
        Collection<Coupon> coupons = customer.getCoupons();

        if (!coupons.contains(coupon)){
            throw new CustomerCouponNotFoundException(customerId,couponId);
        }

        return EntityModel.of(coupon,
                linkTo(methodOn(CustomerController.class).findById(customerId)).withRel("customer"),
                linkTo(methodOn(CouponController.class).findById(couponId)).withRel("coupon"),
                linkTo(methodOn(OfferController.class).getCustomerCoupons(customerId)).withRel(customer.getName()+"'s coupons"));
    }
    // TODO: Voucher getOneCustomerVoucher

    @DeleteMapping("/customer/{customerId}/coupon/{couponId}")  // TODO: Enable delete or remove this method?
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







    // TODO: AHH the Mistake, mostly to be added to Voucher, updateCustomerVoucher
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
////            BeanUtils.copyProperties(newCoupon,updatedCoupon);
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



}
