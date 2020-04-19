package devcrema.spring_jpa_rest_board_example.common;

import devcrema.spring_jpa_rest_board_example.config.AuthenticationFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static devcrema.spring_jpa_rest_board_example.common.ErrorCode.*;

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


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException exception){
        log.error(exception.getLocalizedMessage());
        return ErrorResponse.of(exception.getLocalizedMessage(), RESOURCE_NOT_FOUND);
    }


}
