package discountManagementSystem.customException.advice;

import discountManagementSystem.customException.exception.CouponNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CouponNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(CouponNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String couponNotFoundHandler(CouponNotFoundException ex){
        return "Error :"+ex.getMessage();
    }
}
