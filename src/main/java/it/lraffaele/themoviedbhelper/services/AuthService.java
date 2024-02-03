package it.lraffaele.themoviedbhelper.services;

import it.lraffaele.themoviedbhelper.entities.Authority;
import it.lraffaele.themoviedbhelper.entities.User;
import it.lraffaele.themoviedbhelper.payload.requests.UserLoginRequest;
import it.lraffaele.themoviedbhelper.payload.requests.UserSignUpRequest;
import it.lraffaele.themoviedbhelper.payload.response.JwtAuthenticationResponse;
import it.lraffaele.themoviedbhelper.repositories.AuthorityRepository;
import it.lraffaele.themoviedbhelper.repositories.UserRepository;
import it.lraffaele.themoviedbhelper.security.JWTService;
import it.lraffaele.themoviedbhelper.security.UserPrincipal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthorityRepository authorityRepository;
  private final AuthenticationManager authenticationManager;

  public ResponseEntity<?> signup(UserSignUpRequest request) {

    if (userRepository.existsByEmailOrUsername(request.getEmail(), request.getUsername())) {
      return new ResponseEntity<>("User already exists. Please try again", HttpStatus.BAD_REQUEST);
    }

    User user = new User(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
    Optional<Authority> role = authorityRepository.findByAuthorityName("ROLE_GUEST");

    if(role.isEmpty()){
      return new ResponseEntity<>("Something went wrong during registration", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    user.getAuthorities().add(role.get());
    user.setEnabled(true);
    userRepository.save(user);

    return new ResponseEntity<>("User successfully created.", HttpStatus.CREATED);
  }

  public ResponseEntity login(UserLoginRequest request) {

    User user = userRepository.findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail())
        .orElseThrow(() -> new UsernameNotFoundException("Login Failed: Your user ID or password is incorrect"));

    if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
      return new ResponseEntity<>("Login Failed: Your user ID or password is incorrect", HttpStatus.BAD_REQUEST);
    }

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsernameOrEmail(), request.getPassword()
        ));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String JsonWebToken = JWTService.generateJWT(authentication);
    JwtAuthenticationResponse response = UserPrincipal.createResponseFromUserPrincipal(authentication, JsonWebToken);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
