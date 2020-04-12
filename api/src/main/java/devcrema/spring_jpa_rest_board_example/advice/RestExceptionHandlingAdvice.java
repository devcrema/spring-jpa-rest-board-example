package devcrema.spring_jpa_rest_board_example.advice;

import devcrema.spring_jpa_rest_board_example.ErrorResponse;
import devcrema.spring_jpa_rest_board_example.NotAuthorOfPostException;
import devcrema.spring_jpa_rest_board_example.exception.AuthenticationFailedException;
import devcrema.spring_jpa_rest_board_example.exception.InvalidTokenException;
import devcrema.spring_jpa_rest_board_example.exception.ResourceNotFoundException;
import devcrema.spring_jpa_rest_board_example.user.exception.DuplicatedEmailException;
import devcrema.spring_jpa_rest_board_example.user.exception.DuplicatedNicknameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandlingAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        return ErrorResponse.of(e, e.getBindingResult());
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAuthenticationFailedException(AuthenticationFailedException exception) {
        log.error(exception.getLocalizedMessage());
        return ErrorResponse.of(exception);
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidTokenException(InvalidTokenException exception){
        log.error(exception.getLocalizedMessage());
        return ErrorResponse.of(exception);
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicatedEmailException(DuplicatedEmailException exception){
        log.error(exception.getLocalizedMessage());
        return ErrorResponse.of(exception);
    }

    @ExceptionHandler(DuplicatedNicknameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicatedNicknameException(DuplicatedNicknameException exception){
        log.error(exception.getLocalizedMessage());
        return ErrorResponse.of(exception);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException exception){
        log.error(exception.getLocalizedMessage());
        return ErrorResponse.of(exception);
    }

    @ExceptionHandler(NotAuthorOfPostException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleNotAuthorOfPostException(NotAuthorOfPostException exception){
        log.error(exception.getLocalizedMessage());
        return ErrorResponse.of(exception);
    }

}
