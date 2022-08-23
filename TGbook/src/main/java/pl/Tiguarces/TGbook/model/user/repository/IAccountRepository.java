package pl.Tiguarces.TGbook.model.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.Tiguarces.TGbook.model.user.entity.Account;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    boolean existsByNickname(String nickname);

    Optional<Account> findByNickname(String nickname);
}
