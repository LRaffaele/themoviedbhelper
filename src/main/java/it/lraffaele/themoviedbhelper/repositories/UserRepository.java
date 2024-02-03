package it.lraffaele.themoviedbhelper.repositories;

import it.lraffaele.themoviedbhelper.entities.User;
import it.lraffaele.themoviedbhelper.payload.response.GetUserResponse;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByEmailOrUsername(String email, String username);

  Optional<User> findByUsernameOrEmail(String username, String Email);

  @Query(value = "SELECT new it.lraffaele.themoviedbhelper.payload.response.GetUserResponse("
      + "u.username, "
      + "u.email,"
      + "u.enabled"
      + ") FROM User u "
      + "WHERE u.id = :userId")
  GetUserResponse getUserResponse(long userId);
}
