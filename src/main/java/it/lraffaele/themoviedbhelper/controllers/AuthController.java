package it.lraffaele.themoviedbhelper.controllers;

import it.lraffaele.themoviedbhelper.payload.requests.UserSignUpRequest;
import it.lraffaele.themoviedbhelper.payload.requests.UserLoginRequest;
import it.lraffaele.themoviedbhelper.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("sign-up")
  public ResponseEntity<?> createNewUser(@RequestBody UserSignUpRequest request) {
    return authService.signup(request);
  }

  @PostMapping("login")
  public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest request){
    return authService.login(request);
  }
}
