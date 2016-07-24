import groovy.json.JsonSlurper
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.test.ApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class RenderSpec extends Specification {

    @Shared
    @AutoCleanup
    ApplicationUnderTest appUnderTest = new GroovyRatpackMainApplicationUnderTest()

    @Delegate
    TestHttpClient testClient = appUnderTest.httpClient

    /**
     * Ratpack already has a renderer for String
     *
     * @see ratpack.handling.Context#render(Object)
     */
    def "01 - can render a String"() {
        expect:
            getText() == "Hello Ratpackers!"
    }

    /**
     * A markup template is already provided in `src/ratpack/templates`
     *
     * @see ratpack.groovy.template.MarkupTemplateModule
     */
    def "02 - can render a Groovy Markup Template"() {
        expect:
            getText("welcome") == "<!DOCTYPE html><html><body><p>Hello Ratpackers!</p></body></html>"
    }

    /**
     * Ratpack has a built-in support for rendering json from objects using JsonRender
     *
     * @see ratpack.jackson.Jackson#json(Object)
     * @see ratpack.jackson.Jackson
     */
    def "03 - can render a Book as Json"() {
        given:
            requestSpec { req ->
                req.body.type("application/json")
            }

        when:
            get("api/book/1")

        then:
            def book = new JsonSlurper().parseText(response.body.text)
            with(book) {
                isbn == "1"
                quantity == 10
                price == 15.99
                title == "Ratpack Web Framework"
                author == "Dan Woods"
                publisher == "O'Reilly"
            }

        and:
            response.headers['content-type'] == 'application/json'
    }

    /**
     * Add a custom XML renderer:
     * - XmlRender and DefaultXmlRender are provided
     * - add a ratpack.jackson.Jackson#xml(Object) static method returning XmlRender using JacksonExtension
     * - implement XmlRenderer based on JsonRenderer using XmlMapper
     * - add XmlRenderer to registry
     *
     * @see JacksonExtension
     * @see ratpack.jackson.JsonRender
     * @see ratpack.jackson.internal.DefaultJsonRender
     * @see ratpack.jackson.internal.JsonRenderer
     * @see com.fasterxml.jackson.dataformat.xml.XmlMapper
     */
    def "04 - can render a Book as Xml"() {
        given:
            requestSpec { req ->
                req.headers.set("Accept", "application/xml")
            }

        when:
            get("api/book/1")

        then:
            def book = new XmlSlurper().parseText(response.body.text)
            with(book) {
                isbn == "1"
                quantity == 10
                price == 15.99
                title == "Ratpack Web Framework"
                author == "Dan Woods"
                publisher == "O'Reilly"
            }

        and:
            response.headers['content-type'] == 'application/xml'
    }

}
