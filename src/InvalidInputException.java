/**
 *  This exception should be thrown if a invalid Token is detected in the input stream
 *
 *  @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */

public class InvalidInputException extends Exception {
    InvalidInputException() {
        super();
    }

    InvalidInputException(String message) {
        super(message);
    }
}
