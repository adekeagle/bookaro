package pl.adcom.bookaro.catalog.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.adcom.bookaro.catalog.application.port.CatalogUseCase;
import pl.adcom.bookaro.catalog.domain.Book;
import pl.adcom.bookaro.catalog.domain.CatalogRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
@AllArgsConstructor
class CatalogService implements CatalogUseCase {

    private final CatalogRepository repository;
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
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findOneByTitle(String title) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().contains(title))
                .findFirst();
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return repository.findByAuthor()
                .stream()
                .filter(a -> a.getAuthor().contains(author))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll();
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
    public void addBook(CreateBookCommand command) {
//        Book book = new Book(command.getTitle(), command.getAuthor(), command.getYear(), command.getPrice());
        Book book = command.toBook();
        repository.save(book);
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
}
