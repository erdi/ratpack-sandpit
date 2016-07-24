import XmlParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import ratpack.h2.H2Module
import ratpack.hikari.HikariModule
import ratpack.jackson.Jackson

import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND
import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        module(new H2Module("sa", "", "jdbc:h2:mem:lab08;MODE=MySQL"))
        module(HikariModule, { hikariConfig ->
            hikariConfig.addDataSourceProperty("user", "sa");
            hikariConfig.addDataSourceProperty("password", "");
            hikariConfig.addDataSourceProperty("URL", "jdbc:h2:mem:lab08;INIT=RUNSCRIPT FROM 'classpath:init.sql'");
            hikariConfig.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
        })
        bind(BookRepository, DefaultBookRepository)
        bind(BookService, DefaultBookService)
        bind(ObjectMapper)
        bind(XmlParser)
        add(XmlMapper, new XmlMapper())
    }

    handlers {
        prefix("api/book") {
            get(":isbn") { BookService bookService ->
                bookService.getBook(pathTokens["isbn"])
                    .onNull { clientError(NOT_FOUND.code()) }
                    .then { render Jackson.json(it) }
            }
            all { BookService bookService ->
                byMethod {
                    get {
                        bookService.books.then { render Jackson.json(it) }
                    }
                    post {
                        parse(Book).nextOp {
                            bookService.addBook(it)
                        }.then {
                            render Jackson.json([message: "success"])
                        }
                    }
                }
            }
        }
    }
}