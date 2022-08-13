package pl.Tiguarces.TGbook.model.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.Tiguarces.TGbook.model.book.entity.Book;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {
    boolean existsByName(String name);
}
