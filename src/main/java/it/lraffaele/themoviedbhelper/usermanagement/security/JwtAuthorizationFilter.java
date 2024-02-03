package it.lraffaele.themoviedbhelper.usermanagement.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import it.lraffaele.themoviedbhelper.usermanagement.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final CustomUserDetailsService customUserDetailsService;
  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    try {
      String bearerToken = extractBearerTokenFromAuthHeader(request);
      DecodedJWT decodedJWT = JWTService.verifyJwt(bearerToken);
      if (StringUtils.hasText(bearerToken) && decodedJWT != null) {

        var claim = decodedJWT.getClaim("id").toString();
        long userId = Long.parseLong(claim);
        UserDetails userDetails = customUserDetailsService.loadUserByUserId(userId);

        if(!userDetails.isEnabled()) {
          throw new DisabledException("Account not enabled");
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception ex) {
      log.error("Could not set user authentication in security context", ex);
    }

    filterChain.doFilter(request,response);
  }

  private String extractBearerTokenFromAuthHeader (HttpServletRequest request){
    String authHeader = request.getHeader("Authorization");
    if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
      return authHeader.substring(7);
    }
    return null;
  }
}
