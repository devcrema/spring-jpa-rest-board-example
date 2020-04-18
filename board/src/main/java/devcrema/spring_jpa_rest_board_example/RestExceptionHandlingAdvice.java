package devcrema.spring_jpa_rest_board_example;

import devcrema.spring_jpa_rest_board_example.common.ErrorResponse;
import devcrema.spring_jpa_rest_board_example.config.AuthenticationFailedException;
import devcrema.spring_jpa_rest_board_example.common.ResourceNotFoundException;
import devcrema.spring_jpa_rest_board_example.post.application.NotAuthorOfPostException;
import devcrema.spring_jpa_rest_board_example.user.application.DuplicatedEmailException;
import devcrema.spring_jpa_rest_board_example.user.application.DuplicatedNicknameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static devcrema.spring_jpa_rest_board_example.common.ErrorCode.*;

//TODO 각 도메인에 맞게 분리할 것!

@RestControllerAdvice
@Slf4j
public class RestExceptionHandlingAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("handleMethodArgumentNotValidException", exception);
        return ErrorResponse.of("invalid field request.", INVALID_FIELD, exception.getBindingResult());
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAuthenticationFailedException(AuthenticationFailedException exception) {
        log.error(exception.getLocalizedMessage());
        return ErrorResponse.of(exception.getLocalizedMessage(), AUTHENTICATION_FAILED);
    }

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

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException exception){
        log.error(exception.getLocalizedMessage());
        return ErrorResponse.of(exception.getLocalizedMessage(), RESOURCE_NOT_FOUND);
    }

    @ExceptionHandler(NotAuthorOfPostException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleNotAuthorOfPostException(NotAuthorOfPostException exception){
        log.error(exception.getLocalizedMessage());
        return ErrorResponse.of(exception.getLocalizedMessage(), NOT_AUTHOR_OF_POST);
    }

}
