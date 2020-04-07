package devcrema.spring_jpa_rest_board_example.user.exception;

public class DuplicatedNicknameException extends RuntimeException {
    public DuplicatedNicknameException() {
        super("DuplicatedNickname");
    }
}
