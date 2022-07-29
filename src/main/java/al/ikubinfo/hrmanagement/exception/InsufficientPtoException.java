package al.ikubinfo.hrmanagement.exception;

public class InsufficientPtoException extends RuntimeException {
    public InsufficientPtoException(String errorMessage){
        super(errorMessage);
    }
}
