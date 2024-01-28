package it.lraffaele.themoviedbhelper.services;

import it.lraffaele.themoviedbhelper.entities.User;
import it.lraffaele.themoviedbhelper.payload.requests.SignUpRequest;
import it.lraffaele.themoviedbhelper.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;

  public ResponseEntity<?> signup(SignUpRequest request) {
    if (userRepository.existsByEmailOrUsername(request.getEmail(), request.getUsername())) {
      return new ResponseEntity<>("User already exists. Please try again", HttpStatus.BAD_REQUEST);
    }
    User user = new User(request.getUsername(), request.getEmail(), request.getPassword());
    userRepository.save(user);
    return new ResponseEntity<>("User successfully created.", HttpStatus.CREATED);
  }
}
