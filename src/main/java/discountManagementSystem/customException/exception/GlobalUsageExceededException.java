package discountManagementSystem.customException.exception;

public class GlobalUsageExceededException extends RuntimeException {

    private static final Integer serialVersionUID = 1;

    public GlobalUsageExceededException(String id){
        super("Global usage count has exceeded for offer :" + id);
    }
}
