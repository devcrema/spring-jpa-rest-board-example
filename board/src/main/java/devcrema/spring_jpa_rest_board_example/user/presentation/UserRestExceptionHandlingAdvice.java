package devcrema.spring_jpa_rest_board_example.user.presentation;

import devcrema.spring_jpa_rest_board_example.common.ErrorResponse;
import devcrema.spring_jpa_rest_board_example.user.application.DuplicatedEmailException;
import devcrema.spring_jpa_rest_board_example.user.application.DuplicatedNicknameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static devcrema.spring_jpa_rest_board_example.common.ErrorCode.DUPLICATED_EMAIL;
import static devcrema.spring_jpa_rest_board_example.common.ErrorCode.DUPLICATED_NICKNAME;

@RestControllerAdvice
@Slf4j
public class UserRestExceptionHandlingAdvice {
    @ExceptionHandler(DuplicatedEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicatedEmailException(DuplicatedEmailException exception){
        log.error(exception.getLocalizedMessage());
        return ErrorResponse.of(exception.getLocalizedMessage(), DUPLICATED_EMAIL);
    }

    @ExceptionHandler(DuplicatedNicknameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicatedNicknameException(DuplicatedNicknameException exception){
        log.error(exception.getLocalizedMessage());
        return ErrorResponse.of(exception.getLocalizedMessage(), DUPLICATED_NICKNAME);
    }

}
