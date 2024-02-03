package it.lraffaele.themoviedbhelper.usermanagement.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserSignUpRequest {

  @NotBlank @Size(min = 6, max = 20)
  private String username;

  @Pattern(regexp = "^[a-zA-Z0-9]{6,50}$",
      message = "La password inserita può contenere solo caratteri alfanumerici."
          + " La lunghezza deve essere compresa tra 6 e 50")
  private String password;

  @NotBlank @Email
  private String email;


}
