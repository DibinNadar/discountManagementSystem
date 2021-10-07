package discountManagementSystem.api.auxillary;

import discountManagementSystem.api.primary.CouponController;
import discountManagementSystem.api.primary.CustomerController;
import discountManagementSystem.api.primary.VoucherController;
import discountManagementSystem.customException.exception.*;
import discountManagementSystem.entity.Coupon;
import discountManagementSystem.entity.Customer;
import discountManagementSystem.entity.Voucher;
import discountManagementSystem.repository.CouponRepository;
import discountManagementSystem.repository.CustomerRepository;
import discountManagementSystem.repository.VoucherRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class OfferController {

    // TODO: Unify methods into a single one using generic offer to distinguish between coupon and voucher if viable towards the end


    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private VoucherRepository voucherRepository;

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

    @PutMapping("customer/{customerId}/voucher/{voucherId}")
    Customer addVoucherToCustomer(@PathVariable String voucherId, @PathVariable Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException(customerId));
        Voucher customerVoucher = new Voucher();
        BeanUtils.copyProperties(
                voucherRepository
                        .findById(voucherId)
                        .orElseThrow(()->new VoucherNotFoundException(voucherId)),
                customerVoucher);
        customer.vouchers.add(customerVoucher);
        return customerRepository.save(customer);
    }


    @GetMapping("/customer/{customerId}/coupons")
    public CollectionModel<Coupon> getCustomerCoupons(@PathVariable Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        Collection<Coupon> coupons = customer.getCoupons();

        return CollectionModel.of(coupons,
                linkTo(methodOn(CustomerController.class).findById(customerId)).withRel(customer.getName()),
                linkTo(methodOn(OfferController.class).getCustomerCoupons(customerId)).withRel(customer.getName()+"'s Coupons"));
    }

    @GetMapping("customer/{customerId}/vouchers")
    public CollectionModel<Voucher> getCustomerVouchers(@PathVariable Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        Collection<Voucher> vouchers = customer.getVouchers();

        return CollectionModel.of(vouchers,
                linkTo(methodOn(CustomerController.class).findById(customerId)).withRel(customer.getName()),
                linkTo(methodOn(OfferController.class).getCustomerVouchers(customerId)).withRel(customer.getName()+"'s Vouchers"));
    }

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
    
    
    @GetMapping("/customer/{customerId}/voucher/{voucherId}")
    public EntityModel<Voucher> getOneCustomerVoucher(@PathVariable Long customerId, @PathVariable String voucherId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(()->new VoucherNotFoundException(voucherId));
        Collection<Voucher> vouchers = customer.getVouchers();

        if (!vouchers.contains(voucher)){
            throw new CustomerVoucherNotFoundException(customerId,voucherId);
        }

        return EntityModel.of(voucher,
                linkTo(methodOn(CustomerController.class).findById(customerId)).withRel("customer"),
                linkTo(methodOn(VoucherController.class).findById(voucherId)).withRel("voucher"),
                linkTo(methodOn(OfferController.class).getCustomerVouchers(customerId)).withRel(customer.getName()+"'s vouchers"));
    }

//    @DeleteMapping("/customer/{customerId}/coupon/{couponId}")  // Not Allowed
//    ResponseEntity<?> deleteCustomerCoupon(@PathVariable String couponId, @PathVariable Long customerId) {
//        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException(customerId));
//        Set<Coupon> couponSet = customer.getCoupons();
//        Coupon targetCoupon = couponRepository.findById(couponId).orElseThrow(()->new CouponNotFoundException(couponId));
//
//        if (couponSet.contains(targetCoupon)){
//            customer.getCoupons().remove(targetCoupon);
//            customerRepository.save(customer);
//        }else {
//            throw new CustomerCouponNotFoundException(customerId,couponId);
//        }
//        return ResponseEntity.noContent().build();
//    }

//    @DeleteMapping("/customer/{customerId}/voucher/{voucherId}")  // Not Allowed
//    ResponseEntity<?> deleteCustomerVoucher(@PathVariable String voucherId, @PathVariable Long customerId) {
//        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException(customerId));
//        Set<Voucher> voucherSet = customer.getVouchers();
//        Voucher targetVoucher = voucherRepository.findById(voucherId).orElseThrow(()->new VoucherNotFoundException(voucherId));
//
//        if (voucherSet.contains(targetVoucher)){
//            customer.getVouchers().remove(targetVoucher);
//            customerRepository.save(customer);
//        }else {
//            throw new CustomerVoucherNotFoundException(customerId,voucherId);
//        }
//        return ResponseEntity.noContent().build();
//    }







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
