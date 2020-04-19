package devcrema.spring_jpa_rest_board_example.post.presentation;

import devcrema.spring_jpa_rest_board_example.common.ErrorResponse;
import devcrema.spring_jpa_rest_board_example.post.application.NotAuthorOfPostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static devcrema.spring_jpa_rest_board_example.common.ErrorCode.NOT_AUTHOR_OF_POST;

@RestControllerAdvice
@Slf4j
public class PostRestExceptionHandlingAdvice {
    @ExceptionHandler(NotAuthorOfPostException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleNotAuthorOfPostException(NotAuthorOfPostException exception){
        log.error(exception.getLocalizedMessage());
        return ErrorResponse.of(exception.getLocalizedMessage(), NOT_AUTHOR_OF_POST);
    }

}
