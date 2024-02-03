package it.lraffaele.themoviedbhelper.usermanagement.controller;

import it.lraffaele.themoviedbhelper.usermanagement.service.AuthenticationService;
import it.lraffaele.themoviedbhelper.usermanagement.payload.UserSignUpRequest;
import it.lraffaele.themoviedbhelper.usermanagement.payload.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@Validated
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("sign-up")
  public ResponseEntity<?> createNewUser(@RequestBody UserSignUpRequest request) {
    return authenticationService.signup(request);
  }

  @PostMapping("login")
  public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest request){
    return authenticationService.login(request);
  }
}
