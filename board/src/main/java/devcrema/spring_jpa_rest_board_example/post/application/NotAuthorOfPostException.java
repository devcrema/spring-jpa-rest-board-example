package devcrema.spring_jpa_rest_board_example.post.application;

public class NotAuthorOfPostException extends RuntimeException {
    public NotAuthorOfPostException(String message) {
        super(message);
    }
}
