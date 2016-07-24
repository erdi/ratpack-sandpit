import com.google.inject.Inject
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import ratpack.exec.Blocking
import ratpack.exec.Operation
import ratpack.exec.Promise

import javax.sql.DataSource

import static jooq.tables.Book.BOOK

public class DefaultBookRepository implements BookRepository {

    private final DSLContext dsl

    @Inject
    public DefaultBookRepository(DataSource ds) {
        dsl = DSL.using(ds, SQLDialect.MYSQL)
    }

    @Override
    public Operation addBook(Book book) {
        // TODO - Implement addBook function
        // Hint - checkout Blocking#op(Block) to see how to integrate blocking code with Ratpack
        // Hint - checkout DSLContext#newRecord(Table, Object)
        // Hint - jooq.tables.Book.BOOK is the representation for the underlying `book` table
        Blocking.op {
            dsl.newRecord(BOOK, book).store()
        }
    }

    @Override
    public Promise<List<Book>> getBooks() {
        // TODO - Implement getBooks function
        // Hint - checkout Blocking#get(Block) to see how to integrate blocking code with Ratpack
        // Hint - jooq.tables.Book.BOOK is the representation for the underlying `book` table
        // Hint - checkout DSLContext#select() and ResultQuery#fetchInto(Class)
        Blocking.get {
            dsl.select().from(BOOK).fetchInto(Book)
        }
    }

    @Override
    public Promise<Book> getBook(String isbn) {
        // TODO - Implement getBooks function
        // Hint - checkout Blocking#get(Block) to see how to integrate blocking code with Ratpack
        // Hint - jooq.tables.Book.BOOK is the representation for the underlying `book` table
        // Hint - checkout SelectWhereStep#where(Condition...) and ResultQuery#fetchOneInto(Class)
        Blocking.get {
            dsl.select().from(BOOK).where(BOOK.ISBN.equal(isbn)).fetchOneInto(Book)
        }
    }

}
