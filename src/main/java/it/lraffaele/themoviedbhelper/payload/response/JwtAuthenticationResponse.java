package it.lraffaele.themoviedbhelper.payload.response;

import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class JwtAuthenticationResponse {

  private long id;
  private String username;
  private String email;
  private Set<String> authorities;
  private String token;

  public JwtAuthenticationResponse(long id, String username, String email, Set<String> authorities,
      String token) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.authorities = authorities;
    this.token = "Bearer " + token;
  }
}
