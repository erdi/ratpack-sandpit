import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import ratpack.groovy.template.MarkupTemplateModule
import ratpack.jackson.Jackson

import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        module(MarkupTemplateModule)
        bind(BookService, DefaultBookService)
        bind(BookRepository, DefaultBookRepository)
        bind(XmlRenderer)
        bind(ObjectMapper)
        add(XmlMapper, new XmlMapper())
    }
    handlers {
        get {
            render "Hello Ratpackers!"
        }
        get("welcome") {
            render groovyMarkupTemplate("index.gtpl", welcomeMessage: "Hello Ratpackers!")
        }
        get("api/book/:isbn") { BookService service ->
            def book = service.getBook(pathTokens["isbn"])
            byContent {
                json {
                    render Jackson.json(book)
                }
                xml {
                    render Jackson.xml(book)
                }
            }

        }
    }
}
