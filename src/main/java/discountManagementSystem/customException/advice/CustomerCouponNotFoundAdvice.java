package discountManagementSystem.customException.advice;

import discountManagementSystem.customException.exception.CustomerCouponNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomerCouponNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(CustomerCouponNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String customerCouponNotFoundHandler(CustomerCouponNotFoundException ex){
        return "Error :"+ex.getMessage();
    }
}
