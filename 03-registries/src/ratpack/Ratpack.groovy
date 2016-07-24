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
        register {
            add(BookService, new DefaultBookService())
        }

        prefix("book/:isbn") {
            /**
             * TODO add your new common handler here
             *
             * @see ratpack.handling.Context#next(ratpack.registry.Registry)
             * @see Registry#single(Object)
             */
            all { BookService bookService ->
                String isbn = allPathTokens["isbn"]
                Book b = bookService.getBook(isbn)
                next(Registry.single(b))
            }

            get("title") {
                // Objects added to the registry by upstream handlers are available via type-lookup
                Book b = context.get(Book)

                //TODO refactor this into a common handler for this chain

                response.send b.title
            }

            get("author") { Book b -> // Registry objects can also be "injected" into handler closures

                //TODO refactor this into a common handler for this chain

                response.send b.author
            }
        }
    }
}
