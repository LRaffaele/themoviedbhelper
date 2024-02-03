package it.lraffaele.themoviedbhelper.usermanagement.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import it.lraffaele.themoviedbhelper.usermanagement.security.UserPrincipal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JWTService {
  @Value("${SECRET_KEY}")
  private String secretKey;

  private static String SECRETKEY_STATIC;

  @Value("${SECRET_KEY}")
  public void setSecretKey(String secretKey) {
    JWTService.SECRETKEY_STATIC = secretKey;
  }

  public static String generateJWT(Authentication authentication) {

    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    JWTCreator.Builder builder = JWT.create()
        .withIssuer("moviedbhelper")
        .withSubject(userPrincipal.getUsername())
        .withIssuedAt(Instant.now())
        .withExpiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
        .withClaim("id", userPrincipal.getId())
        .withClaim("roles", userPrincipal.getAuthorities().toString())
        .withClaim("isEnabled", userPrincipal.isEnabled());

    return builder.sign(Algorithm.HMAC256(SECRETKEY_STATIC));
  }

  public static DecodedJWT verifyJwt (String token) throws TokenExpiredException {
    DecodedJWT decodedJwt = null;
    try {
      decodedJwt = JWT.require(Algorithm.HMAC256(SECRETKEY_STATIC)).build().verify(token);
      return decodedJwt;
    } catch (TokenExpiredException ex){
      return null;
    } catch (Exception e){
      log.error("++++++++++++++++ "+e.getMessage());
      return null;
    }
  }
}

