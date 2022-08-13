package pl.Tiguarces.TGbook.model.book.template;

import pl.Tiguarces.TGbook.dto.book.BookDTO;
import pl.Tiguarces.TGbook.model.book.request.SaveRequest;
import pl.Tiguarces.TGbook.model.book.request.UpdateRequest;

import java.util.List;

public interface IBookTemplate {
    void save(SaveRequest request);
    void update(UpdateRequest request);
    void delete(String name);
    void deleteAll();

    List<BookDTO> fetchAll();
    BookDTO fetchByName(String name);
}
