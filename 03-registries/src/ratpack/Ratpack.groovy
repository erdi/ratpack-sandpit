import ratpack.registry.Registry

import static ratpack.groovy.Groovy.ratpack

ratpack {

    /**
     * TODO register DefaultBookService as the implementation of BookService in the registry
     *
     * @see ratpack.groovy.handling.GroovyChain#register(Closure)
     * @see ratpack.registry.RegistrySpec#add(Class, Object)
     */
    handlers {
        prefix("book/:isbn") {
            /**
             * TODO add your new common handler here
             *
             * @see ratpack.handling.Context#next(ratpack.registry.Registry)
             * @see Registry#single(Object)
             */

            get("title") {
                // Objects added to the registry by upstream handlers are available via type-lookup
                BookService bookService = context.get(BookService)

                //TODO refactor this into a common handler for this chain
                String isbn = allPathTokens["isbn"]
                Book b = bookService.getBook(isbn)

                response.send b.title
            }

            get("author") { BookService bookService -> // Registry objects can also be "injected" into handler closures

                //TODO refactor this into a common handler for this chain
                String isbn = allPathTokens["isbn"]
                Book b = bookService.getBook(isbn)

                response.send b.author
            }
        }
    }
}
