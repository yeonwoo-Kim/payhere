package project.payhereprocess.persistence.accountbook;

import org.springframework.data.jpa.repository.JpaRepository;
import project.payhereprocess.domain.AccountBook;

import java.util.List;
import java.util.Optional;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {
    List<AccountBook> findAllByUserId(Long userId);

    Optional<AccountBook> findById(Long id);

    Optional<AccountBook> findByIdAndUserId(Long id, Long userId);
}
