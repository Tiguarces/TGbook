package pl.Tiguarces.TGbook.model.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.Tiguarces.TGbook.model.book.entity.AuthorDescription;

@Repository
public interface IAuthorDescriptionRepository extends JpaRepository<AuthorDescription, Long> {
}
