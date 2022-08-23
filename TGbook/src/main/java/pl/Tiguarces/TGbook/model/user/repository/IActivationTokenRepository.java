package pl.Tiguarces.TGbook.model.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.Tiguarces.TGbook.model.user.entity.ActivationToken;

import java.util.Optional;

@Repository
public interface IActivationTokenRepository extends JpaRepository<ActivationToken, Long> {
    Optional<ActivationToken> findByToken(String token);

    void deleteByToken(String token);
}
