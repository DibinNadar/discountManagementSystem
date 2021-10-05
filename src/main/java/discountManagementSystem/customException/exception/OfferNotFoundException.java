package discountManagementSystem.customException.exception;

public class OfferNotFoundException extends RuntimeException {

    private static final Integer serialVersionUID = 1;

    public OfferNotFoundException(String id){
        super("Could not Find Offer :" + id);
    }
}
