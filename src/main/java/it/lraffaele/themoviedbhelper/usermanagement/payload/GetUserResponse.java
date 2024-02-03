package it.lraffaele.themoviedbhelper.usermanagement.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GetUserResponse {

  private String username;
  private String email;
  private boolean isEnabled;

}
