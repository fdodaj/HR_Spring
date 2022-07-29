package al.ikubinfo.hrmanagement.exception;

public class RequestAlreadyProcessed extends RuntimeException {
    public RequestAlreadyProcessed(String errorMessage){
        super(errorMessage);
    }
}




