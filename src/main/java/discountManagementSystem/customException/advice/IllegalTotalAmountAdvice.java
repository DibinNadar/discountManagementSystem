package discountManagementSystem.customException.advice;

import discountManagementSystem.customException.exception.IllegalTotalAmountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class IllegalTotalAmountAdvice {

    @ResponseBody
    @ExceptionHandler(IllegalTotalAmountException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // TODO Apt error code
    String illegalTotalAmountHandler(IllegalTotalAmountException ex){
        return "Error :"+ex.getMessage();
    }
}
