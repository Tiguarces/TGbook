package pl.Tiguarces.TGbook.service.utils;

import org.springframework.stereotype.Component;
import pl.Tiguarces.TGbook.model.book.entity.*;
import pl.Tiguarces.TGbook.model.book.request.SaveRequest;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.stream;
import static pl.Tiguarces.TGbook.model.book.entity.BookType.valueOf;

@Component
public class EntityHelper {

    public Publisher getBookPublisher(final String publisherName) {
        return Publisher.builder()
                .name(publisherName)
                .build();
    }

    public List<Author> getAuthorsToSave(final SaveRequest.Author[] authors) {
        return stream(authors)
                .map(author -> buildBookAuthor(Map.of(
                        "name", author.getName(),
                        "portrait", author.getPortraitImageURL(),
                        "description", author.getDescription())))
                .toList();
    }

    public List<Image> getImagesToSave(final SaveRequest.BookImage[] images) {
        final List<Image> imagesToSave = stream(images)
                .map(image -> buildBookImage(Map.of(
                        "url", image.getUrl(),
                        "main", image.isMain())))
                .toList();

        checkImages(imagesToSave);
        return imagesToSave;
    }

    /**
     * Method which check if list of book images contains at least one main image,
     * if it doesn't contain then first image will be set to main.
     * @param imagesToSave images to check
     */
    private void checkImages(final List<Image> imagesToSave) {
        boolean mainImageFound = false;

        for(final Image image: imagesToSave) {
            if(image.isMain() && !mainImageFound) {
                mainImageFound = true;
            } else
                image.setMain(false);
        }

        if(!mainImageFound) {
            imagesToSave.get(0)
                        .setMain(true);
        }
    }

    //////////////////////////////////////////////////

    private Author buildBookAuthor(final Map<String, String> data) {
        final String name = data.get("name");
        final String portrait = data.get("portrait");
        final String description = data.get("description");

        final AuthorDescription authorDescription = AuthorDescription.builder()
                .content(description)
                .build();

        return Author.builder()
                    .name(name)
                    .portraitImageURL(portrait)
                    .description(authorDescription)
                .build();
    }

    private Image buildBookImage(final Map<String, Object> data) {
        final String url = getValue(data.get("url"));
        final boolean isMain = (boolean) data.get("main");

        return Image.builder()
                    .url(url)
                    .main(isMain)
                .build();
    }

    public Category getCategory(final String categoryName) {
        return Category.builder()
                    .name(categoryName)
                .build();
    }

    public SubCategory getSubCategory(final String subCategoryName, final Category category) {
        return SubCategory.builder()
                    .name(subCategoryName)
                    .category(category)
                .build();
    }

    public BookDescription getBookDescription(final String description) {
        return BookDescription.builder()
                    .content(description)
                .build();
    }

    public BookDetails getBookDetails(final Map<String, Object> details, final List<Image> images, final BookDescription description) {
        final BookType type = getTypeFromString(details.get("type"));
        final String dimensions = getValue(details.get("dimensions"));
        final int numberOfPages = (int) getNumber(details.get("numberOfPages"));
        final int amount = (int) getNumber(details.get("amount"));
        final String releaseDate = getValue(details.get("releaseDate"));
        final float price = (float) getNumber(details.get("price"));

        return BookDetails.builder()
                .type(type)
                .dimensions(dimensions)
                .numberOfPages(numberOfPages)
                .amount(amount)
                .price(price)
                .images(images)
                .description(description)
                .releaseDate(releaseDate)
                .build();
    }

    private BookType getTypeFromString(final Object type) throws IllegalArgumentException {
        return valueOf(type.toString());
    }

    private String getValue(final Object value) {
        return value.toString();
    }

    private Number getNumber(final Object value) {
        if(value instanceof Integer)
            return Integer.parseInt(value.toString());

        else if(value instanceof Float)
            return Float.parseFloat(value.toString());

        else
            return 0;
    }

    public Book getBook(final String bookName, final SubCategory subCategory, final Publisher publisher,
                        final List<Author> authors, final BookDetails details, BookDescription bookDescription) {
        final Book book = Book.builder()
                    .authors(authors)
                    .name(bookName)
                    .subCategory(subCategory)
                    .publisher(publisher)
                    .details(details)
                .build();

        details.setBook(book);
        bookDescription.setBook(book);
        details.setDescription(bookDescription);
        authors.forEach(author -> author.setBook(book));
        details.getImages().forEach(image -> image.setBook(book));
        return book;
    }
}
