package pl.Tiguarces.TGbook.model.purchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.Tiguarces.TGbook.model.purchase.entity.Purchase;

@Repository
public interface IPurchaseRepository extends JpaRepository<Purchase, Long> {
}
