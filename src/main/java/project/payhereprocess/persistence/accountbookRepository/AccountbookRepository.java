package project.payhereprocess.persistence.accountbookRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.payhereprocess.domain.AccountBook;

public interface AccountbookRepository extends JpaRepository<AccountBook, Long> {


}
