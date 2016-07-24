import groovy.json.JsonSlurper
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.test.ApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import static io.netty.handler.codec.http.HttpHeaderNames.ACCEPT
import static ratpack.http.MediaType.APPLICATION_JSON
import static ratpack.http.MediaType.PLAIN_TEXT_UTF8

class ErrorsSpec extends Specification {

    @Shared
    @AutoCleanup
    ApplicationUnderTest appUnderTest = new GroovyRatpackMainApplicationUnderTest()

    @Delegate
    TestHttpClient testClient = appUnderTest.httpClient

    void setup() {
        requestSpec {
            it.headers.add(ACCEPT, PLAIN_TEXT_UTF8)
        }
    }

    /**
     * You will need to add an instance of CustomServerErrorHandler, appropriate implementation for ErrorMessageFormatter and a String to the context
     *
     * @see ratpack.error.ServerErrorHandler
     * @see CustomServerErrorHandler
     */
    def "01 - receives the default error message in text for server errors in the top level chain"() {
        expect:
            getText("error") == "An error occurred - default message"
    }

    /**
     * You will need to add an instance of CustomClientErrorHandler to the context
     *
     * @see ratpack.error.ClientErrorHandler
     * @see CustomClientErrorHandler
     */
    def "02 - receives the default error message in text for not found pages at the root of the path"() {
        expect:
            getText("not-found") == "A client error (404) occurred - default message"
    }

    /**
     * You will need to register a different message for the API chain
     */
    def "03 - receives the api error message in text for server errors in the API chain"() {
        expect:
            getText("api/error") == "An error occurred - api message"
    }

    def "04 - receives the default error message in text for not found pages under the API path"() {
        expect:
            getText("api/not-found") == "A client error (404) occurred - default message"
    }

    /**
     * You will need to add a different message for requests that are prefixed with with-custom-error-message path but it needs to be available
     * Even outside of the custom error message chain because a client error for an unknown path is handled by the handler appended to your chain by Ratpack.
     *
     * Use the fact that Request is-a Registry and it is checked for objects by Context before current chain level registry is being checked.
     */
    def "05 - receives the custom error message in text for not found pages under with-custom-error-message path"() {
        expect:
            getText("with-custom-error-message/not-found") == "A client error (404) occurred - custom message"
    }

    def "06 - receives the custom error message in text for server errors in the custom error message chain"() {
        expect:
            getText("with-custom-error-message/error") == "An error occurred - custom message"
    }

    /**
     * Add a handler that registers a different type of ErrorMessageFormatter for requests that accept json
     *
     * @see ratpack.groovy.handling.GroovyContext#byContent(groovy.lang.Closure)
     * @see ratpack.handling.ByContentSpec#json(ratpack.func.Block)
     * @see ratpack.handling.ByContentSpec#noMatch(ratpack.func.Block)
     */
    def "07 - receives appropriate error messages in json if the accept header is set to json"() {
        given:
            def slurper = new JsonSlurper()
            requestSpec {
                it.headers.add(ACCEPT, APPLICATION_JSON)
            }
        
        expect:
            slurper.parseText(getText("error")).error == "An error occurred - default message"
            slurper.parseText(getText("not-found")).error == "A client error (404) occurred - default message"
            slurper.parseText(getText("api/error")).error == "An error occurred - api message"
            slurper.parseText(getText("api/not-found")).error == "A client error (404) occurred - default message"
            slurper.parseText(getText("with-custom-error-message/error")).error == "An error occurred - custom message"
            slurper.parseText(getText("with-custom-error-message/not-found")).error == "A client error (404) occurred - custom message"
    }

}
