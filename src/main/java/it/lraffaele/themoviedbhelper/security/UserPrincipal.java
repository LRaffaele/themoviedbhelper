package it.lraffaele.themoviedbhelper.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.lraffaele.themoviedbhelper.entities.User;
import it.lraffaele.themoviedbhelper.payload.response.JwtAuthenticationResponse;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {

  private final long id;
  private final String username;
  private final String email;
  private final String password;
  private final boolean enabled;
  private final Collection<? extends GrantedAuthority> authorities;

  public static UserPrincipal createUserPrincipalFromUser(User user){
    List<? extends GrantedAuthority> authorities = user.getAuthorities().stream().map(
        authority -> new SimpleGrantedAuthority(authority.getAuthorityName())
    ).toList();

    return new UserPrincipal(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getPassword(),
        user.isEnabled(),
        authorities
    );
  }

  public static JwtAuthenticationResponse createResponseFromUserPrincipal(Authentication authentication, String token){
    UserPrincipal authenticatedUser = (UserPrincipal) authentication.getPrincipal();

    return new JwtAuthenticationResponse(
        authenticatedUser.getId(),
        authenticatedUser.getUsername(),
        authenticatedUser.getEmail(),
        authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.toSet()),
        token
    );
  }

  public long getId() {
    return id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return true;
  }


}
