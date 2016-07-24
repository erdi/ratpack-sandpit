import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        bind(BookService, DefaultBookService)
        bind(BookRepository, DefaultBookRepository)
    }
    handlers {
    }
}
