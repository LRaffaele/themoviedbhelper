package it.lraffaele.themoviedbhelper.usermanagement.repository;

import it.lraffaele.themoviedbhelper.usermanagement.entity.Authority;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

  Optional<Authority> findByAuthorityName(String role);
}
