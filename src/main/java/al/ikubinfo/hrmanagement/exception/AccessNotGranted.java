package al.ikubinfo.hrmanagement.exception;

public class AccessNotGranted extends Exception{
    public AccessNotGranted(String errorMessage){
        super(errorMessage);
    }
}


