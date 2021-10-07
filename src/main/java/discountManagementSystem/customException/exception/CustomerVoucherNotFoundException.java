package discountManagementSystem.customException.exception;

public class CustomerVoucherNotFoundException extends RuntimeException {

    private static final Long serialVersionUID = 1L;

    public CustomerVoucherNotFoundException(Long customerId, String voucherId){
        super("Could not Find Voucher:" + voucherId+" for Customer "+customerId);
    }
}
