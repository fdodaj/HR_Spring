package al.ikubinfo.hrmanagement.exception;

public class InvalidDateException extends RuntimeException{
    public InvalidDateException(String errorMessage){
        super(errorMessage);
    }
}
