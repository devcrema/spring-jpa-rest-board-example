package devcrema.spring_jpa_rest_board_example.controller;

import devcrema.spring_jpa_rest_board_example.ResponseMessage;
import devcrema.spring_jpa_rest_board_example.user.SignUpUserRequest;
import devcrema.spring_jpa_rest_board_example.user.SignUpUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static devcrema.spring_jpa_rest_board_example.user.SignUpUserService.SignUpResult.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final SignUpUserService signUpUserService;

    private final ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<ResponseMessage> signUp(@Valid @RequestBody SignUpUserRequest signUpUserRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(new ResponseMessage(errorMessage), HttpStatus.BAD_REQUEST);
        }

        if (signUpUserRequest == null) {
            return new ResponseEntity<>(new ResponseMessage("request can not be null"), HttpStatus.BAD_REQUEST);
        }

        SignUpUserService.SignUpResult result = signUpUserService.signUp(signUpUserRequest.toUser(modelMapper));

        switch (result) {
            case SUCCESS:
                return new ResponseEntity<>(new ResponseMessage(SUCCESS.name()), HttpStatus.OK);
            case DUPLICATED_NICKNAME:
                return new ResponseEntity<>(new ResponseMessage(DUPLICATED_NICKNAME.name()), HttpStatus.CONFLICT);
            case DUPLICATED_EMAIL:
                return new ResponseEntity<>(new ResponseMessage(DUPLICATED_EMAIL.name()), HttpStatus.CONFLICT);
            default:
                return new ResponseEntity<>(new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
