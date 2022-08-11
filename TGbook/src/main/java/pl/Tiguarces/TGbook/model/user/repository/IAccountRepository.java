package pl.Tiguarces.TGbook.model.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.Tiguarces.TGbook.model.user.entity.Account;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
}
