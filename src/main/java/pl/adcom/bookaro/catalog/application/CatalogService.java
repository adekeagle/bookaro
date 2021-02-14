package pl.adcom.bookaro.catalog.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.adcom.bookaro.catalog.application.port.CatalogUseCase;
import pl.adcom.bookaro.catalog.domain.Book;
import pl.adcom.bookaro.catalog.domain.CatalogRepository;
import pl.adcom.bookaro.uploads.application.ports.UploadUseCase;
import pl.adcom.bookaro.uploads.application.ports.UploadUseCase.SaveUploadCommand;
import pl.adcom.bookaro.uploads.domain.Upload;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
@AllArgsConstructor
class CatalogService implements CatalogUseCase {

    private final CatalogRepository repository;
    private final UploadUseCase upload;
//    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

//    public CatalogService(@Qualifier("memoryCatalogRepository") CatalogRepository repository) {
//        this.repository = repository;
//    }


//    public CatalogService(CatalogRepository repository){
//        this.repository = repository;
////        storage.put(1L, new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", 1876));
////        storage.put(2L, new Book(2L, "Ogniem i Mieczem", "Henryk Sienkiewicz", 1884));
////        storage.put(3L, new Book(3L, "Chłopi", "Władysław Reymont", 1904));
//
//    }

    @Override
    public List<Book> findByTitle(String title) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase().startsWith(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Book> findOneByTitle(String title) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().contains(title))
                .findFirst();
    }

    @Override
    public Optional<Book> findByOneAuthor(String author) {
        return repository
                .findAll()
                .stream()
                .filter(auth -> auth.getAuthor().contains(author))
                .findFirst();
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Book> findByTitleAndAuthor(String title, String author) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findOneByTitleAndAuthor(String title, String author) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().contains(title))
                .filter(book -> book.getAuthor().contains(author))
                .findFirst();
    }

    @Override
    public Book addBook(CreateBookCommand command) {
//        Book book = new Book(command.getTitle(), command.getAuthor(), command.getYear(), command.getPrice());
        Book book = command.toBook();
        return repository.save(book);
    }

    @Override
    public void removeById(Long id) {
        repository.removeById(id);
    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookCommand command) {
        return repository.findById(command.getId())
                .map(book -> {
                    Book updatedBook = command.updateFields(book);
//                    book.setTitle(command.getTitle());
//                    book.setAuthor(command.getAuthor());
//                    book.setYear(command.getYear());
                    repository.save(book);
                    return UpdateBookResponse.SUCCESS;
                })
                .orElseGet(() -> new UpdateBookResponse(false, Arrays.asList("Book not found with id: " + command.getId())));
    }

    @Override
    public void updateBookCover(UpdateBookCoverCommand command) {
        int length = command.getFile().length;
        System.out.println("Received cover command: " + command.getFilename() + " bytes " + length);
        repository.findById(command.getId())
                .ifPresent(book -> {
                    Upload savedUpload = upload.save(new SaveUploadCommand(command.getFilename(), command.getFile(), command.getContentType()));
                    book.setCoverId(savedUpload.getId());
                    repository.save(book);
                });
    }

    @Override
    public void removeBookCover(Long id) {
        repository.findById(id)
                .ifPresent(book -> {
                    if (book.getCoverId() != null) {
                        upload.removeById(book.getCoverId());
                        book.setCoverId(null);
                        repository.save(book);
                    }
                });
    }
}
