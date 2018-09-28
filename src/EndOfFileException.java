/**
 *  This exception should be thrown if the EOF is detected in the input stream
 *
 *  @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */

public class EndOfFileException extends Exception {
    EndOfFileException() {
        super();
    }

    EndOfFileException(String message) {
        super(message);
    }
}
