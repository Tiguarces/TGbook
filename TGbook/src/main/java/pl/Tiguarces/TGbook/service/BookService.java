package pl.Tiguarces.TGbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.Tiguarces.TGbook.dto.Mapper;
import pl.Tiguarces.TGbook.dto.book.BookDTO;
import pl.Tiguarces.TGbook.model.book.entity.*;
import pl.Tiguarces.TGbook.model.book.repository.IBookRepository;
import pl.Tiguarces.TGbook.model.book.repository.ICategryRepository;
import pl.Tiguarces.TGbook.model.book.repository.ISubCategoryRepository;
import pl.Tiguarces.TGbook.model.book.request.SaveRequest;
import pl.Tiguarces.TGbook.model.book.request.UpdateRequest;
import pl.Tiguarces.TGbook.model.book.template.IBookTemplate;
import pl.Tiguarces.TGbook.security.exceptions.EntityRecordExists;
import pl.Tiguarces.TGbook.service.utils.EntityHelper;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService implements IBookTemplate {
    private final Mapper mapper;
    private final EntityHelper helper;

    private final IBookRepository bookRepository;
    private final ICategryRepository categoryRepository;
    private final ISubCategoryRepository subCategoryRepository;

    @Override
    public void save(final SaveRequest request) throws EntityRecordExists {
        if(bookRepository.existsByName(request.getName()))
            throw new EntityRecordExists("Book", "name", request.getName());

        final String bookName = request.getName();
        final String publisherName = request.getPublisher();
        final String type = request.getType();
        final String dimensions = request.getDimensions();
        final int numberOfPages = request.getNumberOfPages();
        final int amount = request.getAmount();
        final float price = request.getPrice();
        final String bookDescription = request.getDescription();
        final String releaseDate = request.getReleaseDate();

        final String categoryName = request.getCategory().getName();
        final String subCategoryName = request.getCategory().getSubCategory();
        final SaveRequest.Author[] authors = request.getAuthors();
        final SaveRequest.BookImage[] images = request.getImages();

        // Done entities
        final List<Image> imagesToSave = helper.getImagesToSave(images);
        final List<Author> authorsToSave = helper.getAuthorsToSave(authors);
        final Publisher publisherToSave = helper.getBookPublisher(publisherName);
        final BookDescription bookDescriptionToSave = helper.getBookDescription(bookDescription);

        final Category categoryToSave = categoryRepository.existsByName(categoryName)
                ? categoryRepository.findByName(categoryName)
                : helper.getCategory(categoryName);

        final SubCategory subCategoryToSave = subCategoryRepository.existsByName(subCategoryName)
                ? subCategoryRepository.findByName(subCategoryName)
                : helper.getSubCategory(subCategoryName, categoryToSave);

        final BookDetails detailsToSave = helper.getBookDetails(Map.of(
                "type", type,
                "dimensions", dimensions,
                "numberOfPages", numberOfPages,
                "amount", amount,
                "releaseDate", releaseDate,
                "price", price), imagesToSave, bookDescriptionToSave);

        final Book book = helper.getBook(bookName, subCategoryToSave, publisherToSave, authorsToSave, detailsToSave, bookDescriptionToSave);

        bookRepository.save(book);
        log.info("Book has been saved");
    }

    @Override
    public void update(UpdateRequest request) {

    }

    @Override
    public void delete(String name) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<BookDTO> fetchAll() {
        return mapper.mapAllToBookDTO(bookRepository.findAll());
    }

    @Override
    public BookDTO fetchByName(String name) {
        return null;
    }
}
