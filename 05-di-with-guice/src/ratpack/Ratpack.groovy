import ratpack.registry.Registry

import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        /**
         * TODO bind DefaultBookService as the implementation of BookService
         * TODO bind DefaultBookRepository as the implementation of BookRepository
         *
         * @see ratpack.guice.BindingsSpec#bind(Class, Class)
         */
    }
    handlers {
        prefix("book/:isbn") {
            all { BookService bookService ->
                String isbn = allPathTokens["isbn"]
                Book b = bookService.getBook(isbn)
                next(Registry.single(b))
            }

            get("title") {
                Book b = context.get(Book)

                response.send b.title
            }

            get("author") { Book b ->
                response.send b.author
            }
        }
    }
}
