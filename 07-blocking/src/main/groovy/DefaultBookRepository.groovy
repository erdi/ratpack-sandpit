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

    /**
     * Implement this function
     *
     * @see Blocking#op(ratpack.func.Block)
     * @see DSLContext#newRecord(org.jooq.Table, Object)
     * @see jooq.tables.Book#BOOK
     */
    @Override
    public Operation addBook(Book book) {
    }

    /**
     * Implement this function
     *
     * @see Blocking#get(ratpack.func.Factory)
     * @see DSLContext#select()
     * @see jooq.tables.Book#BOOK
     * @see org.jooq.SelectSelectStep#from(org.jooq.TableLike)
     * @see org.jooq.ResultQuery#fetchInto(Class)
     */
    @Override
    public Promise<List<Book>> getBooks() {
    }

    /**
     * Implement this function
     *
     * @see Blocking#get(ratpack.func.Factory)
     * @see DSLContext#select()
     * @see jooq.tables.Book#BOOK
     * @see org.jooq.SelectSelectStep#from(org.jooq.TableLike)
     * @see org.jooq.SelectJoinStep#where(org.jooq.Condition)
     * @see jooq.tables.Book#ISBN
     * @see org.jooq.TableField#equal(Object)
     * @see org.jooq.ResultQuery#fetchOneInto(Class)
     */
    @Override
    public Promise<Book> getBook(String isbn) {
    }

}
