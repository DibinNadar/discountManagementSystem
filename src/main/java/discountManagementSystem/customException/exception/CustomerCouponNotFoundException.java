package discountManagementSystem.customException.exception;

public class CustomerCouponNotFoundException extends RuntimeException {

    private static final Long serialVersionUID = 1L;

    public CustomerCouponNotFoundException(Long customerId, String couponId){
        super("Could not Find Coupon:" + couponId+" for Customer "+customerId);
    }
}
