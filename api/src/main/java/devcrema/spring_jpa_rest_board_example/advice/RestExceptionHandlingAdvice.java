package devcrema.spring_jpa_rest_board_example.advice;

import devcrema.spring_jpa_rest_board_example.AuthenticationFailedException;
import devcrema.spring_jpa_rest_board_example.BaseError;
import devcrema.spring_jpa_rest_board_example.InvalidTokenException;
import devcrema.spring_jpa_rest_board_example.user.exception.DuplicatedEmailException;
import devcrema.spring_jpa_rest_board_example.user.exception.DuplicatedNicknameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandlingAdvice {

    @ExceptionHandler(AuthenticationFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseError handleAuthenticationFailedException(AuthenticationFailedException exception) {
        log.error(exception.getLocalizedMessage());
        return new BaseError(exception.getLocalizedMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseError handleInvalidTokenException(InvalidTokenException exception){
        log.error(exception.getLocalizedMessage());
        return new BaseError(exception.getLocalizedMessage());
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseError handleDuplicatedEmailException(DuplicatedEmailException exception){
        log.error(exception.getLocalizedMessage());
        return new BaseError(exception.getLocalizedMessage());
    }

    @ExceptionHandler(DuplicatedNicknameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseError handleDuplicatedNicknameException(DuplicatedNicknameException exception){
        log.error(exception.getLocalizedMessage());
        return new BaseError(exception.getLocalizedMessage());
    }
    //TODO 에러 핸들링 하기

//        if (bindingResult.hasErrors()){
//            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
//            return new ResponseEntity<>(new ResponseMessage(errorMessage), HttpStatus.BAD_REQUEST);
//        }
//    @ExceptionHandler(InvalidTokenException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public BaseError handleInvalidTokenException(InvalidTokenException exception){
//        log.error(exception.getLocalizedMessage());
//        return new BaseError(exception.getLocalizedMessage());
//    }
}
