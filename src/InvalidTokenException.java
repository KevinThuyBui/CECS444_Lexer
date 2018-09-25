public class InvalidTokenException extends Exception {
    public InvalidTokenException() {
        //do nothing
    }

    public InvalidTokenException(String errorMessage)
    {
        super(errorMessage);
    }
}
