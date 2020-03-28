package devcrema.spring_jpa_rest_board_example;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super();
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}
