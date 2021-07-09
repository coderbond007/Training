package exception;

/**
 * @author pradyumn.ag
 */
public class MyException extends Exception {
    private static final long serialVersionUID = -7986915990118714483L;
    private static final String DEFAULT_MESSAGE = "Resource is not available!";

    public MyException() {
        super(DEFAULT_MESSAGE);
    }

    public MyException(String message) {
        super(message + "::" + DEFAULT_MESSAGE);
    }
}