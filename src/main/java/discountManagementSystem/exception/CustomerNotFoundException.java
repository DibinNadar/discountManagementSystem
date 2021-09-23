package discountManagementSystem.exception;

public class CustomerNotFoundException extends RuntimeException {

    private static final Long serialVersionUID = 1L;

    public CustomerNotFoundException(Long id){
        super("Could not Find Customer :" + id);
    }
}
