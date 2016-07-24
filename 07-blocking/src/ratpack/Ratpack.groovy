import ratpack.exec.Promise
import ratpack.h2.H2Module
import ratpack.hikari.HikariModule
import ratpack.jackson.Jackson

import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND
import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        module(new H2Module("sa", "", "jdbc:h2:mem:lab07;MODE=MySQL"))
        module(HikariModule, { hikariConfig ->
            hikariConfig.addDataSourceProperty("user", "sa");
            hikariConfig.addDataSourceProperty("password", "");
            hikariConfig.addDataSourceProperty("URL", "jdbc:h2:mem:lab07;INIT=RUNSCRIPT FROM 'classpath:init.sql'");
            hikariConfig.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
        })
        bind(BookRepository, DefaultBookRepository)
        bind(BookService, DefaultBookService)
    }

    handlers {
        prefix("api/book") {
            get(":isbn") { BookService bookService ->
                def isbn = pathTokens["isbn"]

                /**
                 * TODO Use Jackson integration to render the promised JSON. Return 404 if the book does not exist
                 *
                 * @see Promise#onNull(ratpack.func.Block)
                 * @see Promise#then(ratpack.func.Action)
                 */
                Promise<Book> book = bookService.getBook(isbn)
            }
            all { BookService bookService ->
                byMethod {
                    get {
                        /**
                         *  TODO Render the promised list of books as json
                         */
                        Promise<List<Book>> books = bookService.books
                    }
                    post {
                        /**
                         * TODO Parse incoming json, store the book using BookService and return {"message":"success"}
                         *
                         * @see ratpack.handling.Context#parse(Class)
                         * @see Promise#nextOp(ratpack.func.Function)
                         */
                    }
                }
            }
        }
    }
}