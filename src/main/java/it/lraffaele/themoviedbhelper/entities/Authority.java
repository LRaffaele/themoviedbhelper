package it.lraffaele.themoviedbhelper.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "authorities")
@Getter @Setter @NoArgsConstructor @ToString
public class Authority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long authorityId;

  @Column(length = 50, nullable = false, unique = true)
  private String authorityName;

  public Authority(String authorityName) {
    this.authorityName = authorityName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Authority authority = (Authority) o;
    return authorityId == authority.authorityId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(authorityId);
  }
}
