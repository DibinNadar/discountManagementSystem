package discountManagementSystem.customException.exception;

public class CustomerVoucherNotFoundException extends RuntimeException {

    private static final Long serialVersionUID = 1L;

    public CustomerVoucherNotFoundException(Long customerId, Long serialNo){
        super("Could not Find Serial No "+serialNo+" for Customer "+customerId);
    }
}
