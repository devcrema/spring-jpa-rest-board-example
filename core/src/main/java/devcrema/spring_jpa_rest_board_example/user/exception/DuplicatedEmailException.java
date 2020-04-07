package devcrema.spring_jpa_rest_board_example.user.exception;

public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException() {
        super("DuplicatedEmail");
    }
}
