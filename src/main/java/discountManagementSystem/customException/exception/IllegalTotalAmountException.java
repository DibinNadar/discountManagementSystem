package discountManagementSystem.customException.exception;

public class IllegalTotalAmountException extends RuntimeException {

    private static final Integer serialVersionUID = 1;

    public IllegalTotalAmountException(Double amount){
        super("Purchase Amount of " + amount+" is not acceptable");
    }
}
