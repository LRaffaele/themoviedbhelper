package it.lraffaele.themoviedbhelper.security.service;

import it.lraffaele.themoviedbhelper.entities.User;
import it.lraffaele.themoviedbhelper.repositories.UserRepository;
import it.lraffaele.themoviedbhelper.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
        ()-> new UsernameNotFoundException("Username not found: " + usernameOrEmail)
    );
    return UserPrincipal.createUserPrincipalFromUser(user);
  }

  @Transactional
  public UserDetails loadUserByUserId(long userId){
    User user = userRepository.findById(userId).orElseThrow(
        () -> new UsernameNotFoundException("Username with id " + userId + " not found")
    );
    return UserPrincipal.createUserPrincipalFromUser(user);
  }
}
