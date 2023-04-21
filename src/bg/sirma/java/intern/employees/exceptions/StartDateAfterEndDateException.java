package bg.sirma.java.intern.employees.exceptions;

public class StartDateAfterEndDateException extends Exception {
    public StartDateAfterEndDateException(String message) {
       super(message);
    }
}
