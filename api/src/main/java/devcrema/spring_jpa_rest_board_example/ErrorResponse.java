package devcrema.spring_jpa_rest_board_example;

import lombok.Value;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class ErrorResponse {
    String message;
    String code;
    List<FieldError> errors;

    @Value
    public static class FieldError {
        String field;
        String value;
        String reason;
    }

    public static ErrorResponse of(Exception exception) {
        return new ErrorResponse(exception.getLocalizedMessage()
                , exception.getClass().getSimpleName()
                , new ArrayList<>());
    }

    public static ErrorResponse of(Exception exception, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors().stream()
                .map((it) -> new FieldError(it.getField()
                        , String.valueOf(it.getRejectedValue())
                        , it.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErrorResponse("request validation is failed."
                , exception.getClass().getSimpleName()
                , fieldErrors);
    }
}
