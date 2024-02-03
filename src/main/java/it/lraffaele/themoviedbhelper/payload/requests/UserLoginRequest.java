package it.lraffaele.themoviedbhelper.payload.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserLoginRequest {

  @NotBlank
  private String usernameOrEmail;
  @NotBlank
  private String password;
}
