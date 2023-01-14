package project.payhereprocess.persistence.accountbook;

import org.springframework.data.jpa.repository.JpaRepository;
import project.payhereprocess.domain.AccountBook;

import java.util.List;

public interface AccountbookRepository extends JpaRepository<AccountBook, Long> {
    List<AccountBook> findAllByUserId(Long userId);
}
