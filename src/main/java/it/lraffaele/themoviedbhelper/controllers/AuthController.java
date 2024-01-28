package it.lraffaele.themoviedbhelper.controllers;

import it.lraffaele.themoviedbhelper.payload.requests.SignUpRequest;
import it.lraffaele.themoviedbhelper.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("signup")
  public ResponseEntity<?> signupUser(@RequestBody SignUpRequest request) {
    return authService.signup(request);
  }
}
