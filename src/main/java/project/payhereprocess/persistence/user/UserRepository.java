package project.payhereprocess.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import project.payhereprocess.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
}
