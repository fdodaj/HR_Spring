package al.ikubinfo.hrmanagement.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    private static final String NOT_ENOUGH_PTO = "NOT_ENOUGH_PTO";
    private static final  String UNEXPECTED_ERROR = "UNEXPECTED_ERROR";

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ErrorResponse handleThrowable(final Throwable ex) {
        log.error(UNEXPECTED_ERROR, ex);
        return new ErrorResponse(INTERNAL_SERVER_ERROR, "Entity not found. Please enter a valid ID");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    public ErrorResponse handleNotFound(final Throwable ex) {
        log.error(UNEXPECTED_ERROR, ex);
        return new ErrorResponse(INTERNAL_SERVER_ERROR, "Entity not found. Please enter a valid ID");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ErrorResponse handleNullPointer(final Throwable ex) {
        log.error(UNEXPECTED_ERROR, ex);
        return new ErrorResponse(INTERNAL_SERVER_ERROR, "Empty body");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InsufficientPtoException.class)
    @ResponseBody
    public ErrorResponse handleInsufficientPto(final Throwable ex){
        log.error(UNEXPECTED_ERROR, ex);
        return new ErrorResponse(NOT_ENOUGH_PTO, "User does not have enough Paid Time Off for this request");
    }


    @Data
    public static class ErrorResponse {
        private final String code;
        private final String message;
    }
}
