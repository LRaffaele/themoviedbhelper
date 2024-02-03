package it.lraffaele.themoviedbhelper.usermanagement.controller;

import it.lraffaele.themoviedbhelper.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("{userId}")
  @PreAuthorize("hasRole(ROLE_ADMIN)")
  public ResponseEntity<?> getUser(@PathVariable long userId){
    return userService.getUser(userId);
  }
}
