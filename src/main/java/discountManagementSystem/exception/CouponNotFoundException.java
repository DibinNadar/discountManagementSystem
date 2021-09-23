package discountManagementSystem.exception;

public class CouponNotFoundException extends RuntimeException {

    private static final Integer serialVersionUID = 1;

    public CouponNotFoundException(String id){
        super("Could not Find Coupon :" + id);
    }
}
