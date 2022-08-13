package pl.Tiguarces.TGbook.dto.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AuthorDTO {
    private String name;
    private String portraitImageURL;
    private String description;
    private List<BookDTO> books;
}
