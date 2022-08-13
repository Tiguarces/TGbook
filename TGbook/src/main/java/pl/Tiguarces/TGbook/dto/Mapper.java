package pl.Tiguarces.TGbook.dto;

import org.springframework.stereotype.Component;
import pl.Tiguarces.TGbook.dto.book.BookDTO;
import pl.Tiguarces.TGbook.dto.book.ImageDTO;
import pl.Tiguarces.TGbook.dto.book.ReviewDTO;
import pl.Tiguarces.TGbook.model.book.entity.*;

import java.util.List;

@Component
public class Mapper {

    public List<BookDTO> mapAllToBookDTO(final List<Book> rawBookList) {
        return rawBookList
                .stream()
                .map(this::mapOneToBookDTO)
                .toList();
    }

    private BookDTO mapOneToBookDTO(final Book book) {
        final BookDetails details = book.getDetails();
        final String name = book.getName();
        final String[] authors = getAuthors(book.getAuthors());
        final ReviewDTO[] reviews = getReviews(book.getReviews());
        final String publisherName = book.getPublisher().getName();
        final BookDTO.Category category = getCategory(book.getSubCategory());
        final String type = getType(book.getDetails());
        final String dimensions = details.getDimensions();
        final int numberOfPages = details.getNumberOfPages();
        final int amount = details.getAmount();
        final float price = details.getPrice();
        final ImageDTO[] images = getImages(book.getDetails());
        final String description = details.getDescription().getContent();

        return BookDTO.builder()
                    .name(name)
                    .authors(authors)
                    .reviews(reviews)
                    .publisher(publisherName)
                    .category(category)
                    .type(type)
                    .dimensions(dimensions)
                    .numberOfPages(numberOfPages)
                    .amount(amount)
                    .price(price)
                    .images(images)
                    .description(description)
                .build();
    }

    private ImageDTO[] getImages(final BookDetails details) {
        final List<Image> images = details.getImages();
        return images.stream()
                .map(image -> ImageDTO.builder()
                                .url(image.getUrl())
                                .isMain(image.isMain())
                             .build())
                .toArray(ImageDTO[]::new);
    }

    private String getType(final BookDetails details) {
        final BookType bookType = details.getType();
        return bookType.getType();
    }

    private BookDTO.Category getCategory(final SubCategory subCategory) {
        final String categoryName = subCategory.getCategory().getName();
        final String subCategoryName = subCategory.getName();
        return BookDTO.Category.builder()
                    .name(categoryName)
                    .subCategory(subCategoryName)
                .build();
    }

    private ReviewDTO[] getReviews(final List<Review> reviews) {
        return reviews.stream()
                .map(review -> ReviewDTO.builder()
                                .authorNickname(review.getAuthor().getNickname())
                                .createdDate(review.getCreatedDate())
                                .content(review.getContent())
                            .build()
                ).toArray(ReviewDTO[]::new);
    }

    private String[] getAuthors(final List<Author> authors) {
        return authors.stream()
                .map(Author::getName)
                .toArray(String[]::new);
    }
}
