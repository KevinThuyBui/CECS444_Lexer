public class InvalidTokenException extends Exception {
    public InvalidTokenException() {
        super();
    }

    public InvalidTokenException(String errorMessage)
    {
        super(errorMessage);
    }
}
