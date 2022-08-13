package pl.Tiguarces.TGbook.dto.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    private String name;
    private String subCategory;
    private String[] bookNames;
}
