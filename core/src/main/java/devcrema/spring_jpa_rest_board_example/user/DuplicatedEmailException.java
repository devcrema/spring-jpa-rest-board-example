package devcrema.spring_jpa_rest_board_example.user;

public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException() {
        super("duplicated email");
    }
}
