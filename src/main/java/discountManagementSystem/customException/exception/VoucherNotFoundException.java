package discountManagementSystem.customException.exception;

public class VoucherNotFoundException extends RuntimeException {

    private static final Integer serialVersionUID = 1;

    public VoucherNotFoundException(Integer id){
        super("Could not Find Voucher :" + id);
    }
}
