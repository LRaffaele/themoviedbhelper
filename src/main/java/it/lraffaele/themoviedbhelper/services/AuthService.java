package it.lraffaele.themoviedbhelper.services;

import it.lraffaele.themoviedbhelper.entities.Authority;
import it.lraffaele.themoviedbhelper.entities.User;
import it.lraffaele.themoviedbhelper.payload.requests.SignUpRequest;
import it.lraffaele.themoviedbhelper.repositories.AuthorityRepository;
import it.lraffaele.themoviedbhelper.repositories.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final AuthorityRepository authorityRepository;

  public ResponseEntity<?> signup(SignUpRequest request) {
    if (userRepository.existsByEmailOrUsername(request.getEmail(), request.getUsername())) {
      return new ResponseEntity<>("User already exists. Please try again", HttpStatus.BAD_REQUEST);
    }
    User user = new User(request.getUsername(), request.getEmail(), request.getPassword());
    Optional<Authority> role = authorityRepository.findByAuthorityName("ROLE_GUEST");

    if(role.isEmpty()){
      return new ResponseEntity<>("Something went wrong during registration", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    user.getAuthorities().add(role.get());
    user.setEnabled(true);
    userRepository.save(user);
    return new ResponseEntity<>("User successfully created.", HttpStatus.CREATED);
  }
}
