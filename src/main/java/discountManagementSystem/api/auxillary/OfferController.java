package discountManagementSystem.api.auxillary;

import discountManagementSystem.api.primary.CouponController;
import discountManagementSystem.api.primary.CustomerController;
import discountManagementSystem.api.primary.VoucherController;
import discountManagementSystem.customException.exception.*;
import discountManagementSystem.entity.Coupon;
import discountManagementSystem.entity.Customer;
import discountManagementSystem.entity.CustomerVoucher;
import discountManagementSystem.entity.Voucher;
import discountManagementSystem.repository.CouponRepository;
import discountManagementSystem.repository.CustomerRepository;
import discountManagementSystem.repository.CustomerVoucherRepository;
import discountManagementSystem.repository.VoucherRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class OfferController {

    // Unify methods into a single one using generic offer to distinguish between coupon and voucher if viable towards the end
    // Not viable

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerVoucherRepository customerVoucherRepository;


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
    CustomerVoucher addVoucherToCustomer(@PathVariable String voucherId, @PathVariable Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException(customerId));
        Voucher voucherToAdd = new Voucher();
        BeanUtils.copyProperties(
                voucherRepository
                        .findById(voucherId)
                        .orElseThrow(()->new VoucherNotFoundException(voucherId)),
                voucherToAdd);
        CustomerVoucher customerVoucher = new CustomerVoucher(customer,voucherToAdd, LocalDate.now(),LocalDate.now().plusDays(30),voucherToAdd.getFlatDiscount());
        return customerVoucherRepository.save(customerVoucher);
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
    public CollectionModel<CustomerVoucher> getCustomerVouchers(@PathVariable Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        Collection<CustomerVoucher> customers_vouchers = customer.getCustomers_vouchers();
        return CollectionModel.of(customers_vouchers,
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

    @GetMapping("/customer/{customerId}/voucher/serialNo/{serialNo}")
    public EntityModel<CustomerVoucher> getOneCustomerVoucher(@PathVariable Long customerId, @PathVariable Long serialNo) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        CustomerVoucher customerVoucher = customerVoucherRepository.findById(serialNo).orElseThrow(()->new CustomerVoucherNotFoundException(customerId,serialNo));
        Collection<CustomerVoucher> customers_vouchers = customer.getCustomers_vouchers();

        if (!customers_vouchers.contains(customerVoucher)){
            throw new CustomerVoucherNotFoundException(customerId,serialNo);
        }

        String voucherId = customerVoucher.getVoucher().getVoucherId();

        return EntityModel.of(customerVoucher,
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

//    @DeleteMapping("/customer/{customerId}/voucher/serialNo/{serialNo}")  // Not Allowed
//    ResponseEntity<?> deleteCustomerVoucher(@PathVariable Long serialNo, @PathVariable Long customerId) {
//        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException(customerId));
//        Set<CustomerVoucher> customerVoucherSet = customer.getCustomers_vouchers();
//        CustomerVoucher targetVoucher = customerVoucherRepository.findById(serialNo).orElseThrow(()->new CustomerVoucherNotFoundException(customerId,serialNo));
//
//        if (customerVoucherSet.contains(targetVoucher)){
//            customer.getCustomers_vouchers().remove(targetVoucher);
//            customerRepository.save(customer);
//        }else {
//            throw new CustomerVoucherNotFoundException(customerId,serialNo);
//        }
//        return ResponseEntity.noContent().build();
//    }


}
