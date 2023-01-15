package project.payhereprocess.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import project.payhereprocess.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    Optional<User> findOneByEmail(String email);

}
