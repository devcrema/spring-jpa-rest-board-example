package devcrema.spring_jpa_rest_board_example;

public class NotAuthorOfPostException extends RuntimeException {
    public NotAuthorOfPostException(String message) {
        super(message);
    }
}
