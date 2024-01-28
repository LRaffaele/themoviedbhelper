package it.lraffaele.themoviedbhelper.repositories;

import it.lraffaele.themoviedbhelper.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByEmailOrUsername(String email, String username);

  User save(User user);
}
