package dtn.ServiceScore.exceptions;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message, String additionalInfo) {
        super(message + " Additional info: " + additionalInfo);
    }

    public DataNotFoundException(String message) {
        super(message);
    }
}
