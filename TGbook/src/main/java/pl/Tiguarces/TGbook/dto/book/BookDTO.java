package pl.Tiguarces.TGbook.dto.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookDTO {
    private String name;
    private String[] authors;
    private ReviewDTO[] reviews;
    private String publisher;
    private Category category;
    private String type;
    private String dimensions;
    private int numberOfPages;
    private int amount;
    private float price;
    private ImageDTO[] images;
    private String description;

    @Getter
    @Setter
    @Builder
    public static class Category {
        private String name;
        private String subCategory;
    }
}
