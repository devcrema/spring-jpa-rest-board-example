package devcrema.spring_jpa_rest_board_example;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class BaseError {
    String message;
}
