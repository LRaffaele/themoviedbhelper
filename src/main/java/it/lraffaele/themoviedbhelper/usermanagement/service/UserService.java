package it.lraffaele.themoviedbhelper.usermanagement.service;

import it.lraffaele.themoviedbhelper.usermanagement.payload.GetUserResponse;
import it.lraffaele.themoviedbhelper.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public ResponseEntity<?> getUser(long userId) {
    if(!userRepository.existsById(userId)){
      return new ResponseEntity<>("User with id " + userId + " not found", HttpStatus.NOT_FOUND);
    }
    GetUserResponse response = userRepository.getUserResponse(userId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
