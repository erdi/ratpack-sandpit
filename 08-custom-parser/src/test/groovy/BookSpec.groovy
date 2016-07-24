import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.test.ApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class BookSpec extends Specification {
    @Shared
    @AutoCleanup
    ApplicationUnderTest appUnderTest = new GroovyRatpackMainApplicationUnderTest()

    @Delegate
    TestHttpClient testClient = appUnderTest.httpClient

    def "01 - empty db has no entries"() {
        expect:
            getText('/api/book') == "[]"

        and:
            get('/api/book/1').statusCode == 404
    }

    def "02 - can add a Book as XML and render it as Json"() {
        given:
            requestSpec { req ->
                req.body
                    .type("application/xml")
                    .stream { stream ->
                    new MarkupBuilder(new PrintWriter(stream)).book {
                        isbn(1)
                        quantity(10)
                        price(49.99)
                        title("Learning Ratpack")
                        author("Dan Woods")
                        publisher("O'Reilly")
                    }
                }
            }

        when:
            post("api/book")

        then:
            new JsonSlurper().parseText(response.body.text).message == "success"

        when:
            get("api/book/1")

        then:
            def book = new JsonSlurper().parseText(response.body.text)
            with(book) {
                isbn == "1"
                quantity == 10
                price == 49.99
                title == "Learning Ratpack"
                author == "Dan Woods"
                publisher == "O'Reilly"
            }

        and:
            response.headers['content-type'] == 'application/json'

        when:
            get("api/book")

        then:
            def books = new JsonSlurper().parseText(response.body.text)
            with(books.first()) {
                isbn == "1"
                quantity == 10
                price == 49.99
                title == "Learning Ratpack"
                author == "Dan Woods"
                publisher == "O'Reilly"
            }
    }

}

