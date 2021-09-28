package discountManagementSystem.customException.advice;

import discountManagementSystem.customException.exception.VoucherNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class VoucherNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(VoucherNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String voucherNotFoundHandler(VoucherNotFoundException ex){
        return "Error :"+ex.getMessage();
    }
}
