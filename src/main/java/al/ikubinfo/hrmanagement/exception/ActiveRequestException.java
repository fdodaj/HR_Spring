package al.ikubinfo.hrmanagement.exception;

public class ActiveRequestException extends RuntimeException{
    public ActiveRequestException(String errorMessage){
        super(errorMessage);
    }
}


