package al.ikubinfo.hrmanagement.exception;

public class AccessNotGranted extends RuntimeException{
    public AccessNotGranted(String errorMessage){
        super(errorMessage);
    }
}


