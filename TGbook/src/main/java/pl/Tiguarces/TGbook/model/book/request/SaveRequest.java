package pl.Tiguarces.TGbook.model.book.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SaveRequest {

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Publisher cannot be null")
    @NotBlank(message = "Publisher cannot be blank")
    private String publisher;

    @NotNull(message = "Type cannot be null")
    @NotBlank(message = "Type cannot be blank")
    private String type;

    @Pattern(regexp = "(([1-9]\\d)|(0[1-9])) x (([1-9]\\d)|(0[1-9])) x (([1-9]\\d)|(0[1-9]))cm")
    private String dimensions;

    @Min(value = 1, message = "Number of pages should be greater than 1")
    private int numberOfPages;

    @Min(value = 1, message = "Amount should be greater than 1")
    private int amount;

    @Min(value = 1, message = "Price should be greater than 1")
    private float price;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be blank")
    private String description;

    @Valid
    private Author[] authors;

    @Valid
    private BookImage[] images;

    @Valid
    private Category category;

    @Getter
    @Setter
    public static class Author {

        @NotNull(message = "Name cannot be null")
        @NotBlank(message = "Name cannot be blank")
        private String name;

        @URL
        @NotNull(message = "PortraitImageURL cannot be null")
        @NotBlank(message = "PortraitImageURL cannot be blank")
        private String portraitImageURL;

        @NotNull(message = "Description cannot be null")
        @NotBlank(message = "Description cannot be blank")
        private String description;
    }

    @Getter
    @Setter
    public static class Category {

        @NotNull(message = "Name cannot be null")
        @NotBlank(message = "Name cannot be blank")
        private String name;

        @NotNull(message = "SubCategory cannot be null")
        @NotBlank(message = "SubCategory cannot be blank")
        private String subCategory;
    }

    @Getter
    @Setter
    public static class BookImage {

        @URL
        @NotNull(message = "URL cannot be null")
        @NotBlank(message = "URL cannot be blank")
        private String url;
        private boolean isMain;
    }
}
