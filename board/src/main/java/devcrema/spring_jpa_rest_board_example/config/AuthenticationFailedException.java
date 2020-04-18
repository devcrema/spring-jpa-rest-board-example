package devcrema.spring_jpa_rest_board_example.config;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException() {
        super();
    }

    public AuthenticationFailedException(String message) {
        super(message);
    }
}
