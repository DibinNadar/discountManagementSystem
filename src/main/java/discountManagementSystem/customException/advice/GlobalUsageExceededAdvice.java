package discountManagementSystem.customException.advice;

import discountManagementSystem.customException.exception.GlobalUsageExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalUsageExceededAdvice {

    @ResponseBody
    @ExceptionHandler(GlobalUsageExceededException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)  //TODO: Find right code to use
    String globalUsageExceededHandler(GlobalUsageExceededException ex){
        return "Error :"+ex.getMessage();
    }
}
