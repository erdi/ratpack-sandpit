import ratpack.exec.Operation
import ratpack.exec.Promise

public interface BookService {
    Operation addBook(Book book)

    Promise<List<Book>> getBooks()

    Promise<Book> getBook(String isbn)
}
