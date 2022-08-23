package pl.Tiguarces.TGbook.model.user.template;

import pl.Tiguarces.TGbook.model.book.request.UpdateRequest;

public interface IAccountTemplate {
    void update(UpdateRequest request);
    void delete(String name);
    void deleteAll();
}
