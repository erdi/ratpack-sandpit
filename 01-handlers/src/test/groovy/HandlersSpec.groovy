import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.http.client.RequestSpec
import ratpack.test.ApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
import uk.org.lidalia.slf4jtest.TestLogger
import uk.org.lidalia.slf4jtest.TestLoggerFactory

import static io.netty.handler.codec.http.HttpResponseStatus.METHOD_NOT_ALLOWED
import static uk.org.lidalia.slf4jtest.LoggingEvent.info

class HandlersSpec extends Specification {
    public static final TestLogger log = TestLoggerFactory.getTestLogger("Ratpack")

    // Define our script backed application and make it available for testing.
    // @Shared means the same app instance will be used for all tests.
    @Shared
    @AutoCleanup
    ApplicationUnderTest appUnderTest = new GroovyRatpackMainApplicationUnderTest()

    // ApplicationUnderTest includes a TestHttpClient that we can use for sending requests to our application.
    // @Delegate means that all client methods and properties are implicitly available in the context of this test thus saving us some typing.
    @Delegate
    TestHttpClient testClient = appUnderTest.httpClient

    void cleanup() {
        TestLoggerFactory.clearAll()
    }

    /**
     * @see ratpack.groovy.handling.GroovyChain#get(Closure)
     * @see ratpack.handling.Context#getResponse()
     * @see ratpack.http.Response#send(String)
     */
    def "01 - can send a request to the root path"() {
        when: "a GET request is sent with no path"
            get() // we don't have to assign the ReceivedResponse returned as TestHttpClient will keep track of this for us

        then: "a response is returned with body text of 'Hello Ratpackers!'"
            response.body.text == "Hello Ratpackers!" // response is the ReceivedResponse from the last request sent
    }

    /**
     * @see ratpack.groovy.handling.GroovyChain#get(String, Closure)
     */
    def "02 - can send a GET request to the path '/user'"() {
        when: "a GET request is sent to the path '/user'"
            get("user")

        then: "a response is returned with body text of 'user'"
            response.body.text == "user"
    }

    /**
     * @see ratpack.handling.Chain - Path Binding section
     * @see ratpack.handling.Context#getPathTokens()
     * @see ratpack.path.PathBinding#getTokens()
     */
    @Unroll
    def "03 - can send a GET request to the path '/user/#username'"() {
        expect:
            getText("user/$username") == "user/$username" // Using `getText()` we can roll 2 method calls into 1

        where:
            username << ["dave", "fred"]
    }

    @Unroll
    def "04 - can send a GET request to the path '/user/#username/tweets'"() {
        expect:
            getText("user/$username/tweets") == "user/$username/tweets"

        where:
            username << ["dave", "fred"]
    }

    /**
     * If you haven't already, you might want to start refactoring
     * @see ratpack.groovy.handling.GroovyChain#prefix(String, Closure)
     * @see ratpack.handling.Context#getAllPathTokens()
     * @see ratpack.path.PathBinding#getAllTokens()
     */
    @Unroll
    def "05 - can send a GET request to the path '/user/#username/friends'"() {
        expect:
            getText("user/$username/friends") == "user/$username/friends"

        where:
            username << ["dave", "fred"]
    }

    /**
     * @see ratpack.groovy.handling.GroovyContext#byMethod(Closure)
     * @see ratpack.groovy.handling.GroovyChain#path(Closure)
     * @see ratpack.groovy.handling.GroovyChain#path(String, Closure)
     */
    def "06 - can send a POST request to the path '/user'"() {
        expect:
            postText("user") == "user"
    }

    def "07 - can not send a PUT request to the path '/user'"() {
        expect:
            put("user").statusCode == METHOD_NOT_ALLOWED.code()
    }

    /**
     * There is already a file available to serve src/ratpack/public/js/app.js
     * @see ratpack.groovy.handling.GroovyChain#files(Closure)
     * @see ratpack.groovy.handling.GroovyChain#files(ratpack.func.Action)
     * @see ratpack.file.FileHandlerSpec#path(String)
     * @see ratpack.file.FileHandlerSpec#dir(String)
     */
    def "08 - can request a static asset"() {
        expect:
            getText("assets/js/app.js") == "var message = 'Hello Ratpackers!';"
    }

    /**
     * There is already an index file available to serve src/ratpack/pages/home/index.html
     * @see ratpack.file.FileHandlerSpec#indexFiles(String...)
     */
    def "09 - can serve an index file"() {
        expect:
            getText("home") == "<html><body><p>Hello Ratpackers!</p></body></html>"
    }

    /**
     * Handlers that don't generate a response must delegate to the next handler
     * @see ratpack.handling.Context#next()
     *
     * Path route matching with a regular expression might help here
     * @see ratpack.handling.Chain - Path Binding section
     */
    @Unroll
    def "10 - can log a warning when there is a request for a user starting with 'f'"() {
        when:
            get("user/$username/tweets")

        then:
            log.allLoggingEvents.contains info("Warning, request for $username")

        where:
            username << ["florence", "fred"]
    }

    /**
     * @see ratpack.groovy.handling.GroovyChain#all(Closure)
     * @see ratpack.handling.RequestLogger#ncsa(org.slf4j.Logger)
     */
    @Unroll
    def "11 - can log all requests"() {
        when:
            get(requestPath)

        then:
            log.allLoggingEvents*.message.first().contains(expectedLogEntry)

        where:
            requestPath | expectedLogEntry
            "user"      | "\"GET /user HTTP/1.1\" 200"
            "user/1"    | "\"GET /user/1 HTTP/1.1\" 200"
    }

    /**
     * @see ratpack.handling.Context#getRequest()
     * @see ratpack.http.Request#getHeaders()
     */
    @Unroll
    def "12 - can send a POST request with a soap action of #soapAction"() {
        given:
            // Using TestHttpClient.requestSpec we can configure details of this request like adding headers and setting the body
            requestSpec { RequestSpec req ->
                req.headers.set("SOAPAction", soapAction)
            }

        expect:
            postText("api/ws") == "api/ws - $soapAction"

        where:
            soapAction << ["getTweets", "getFriends"]
    }
}
