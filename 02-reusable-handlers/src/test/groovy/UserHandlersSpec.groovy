import ratpack.groovy.test.handling.GroovyRequestFixture
import spock.lang.Specification
import spock.lang.Unroll

class UserHandlersSpec extends Specification {

    /**
     * Don't forget to update Ratpack.groovy with your new handler and check HandlersSpec still passes
     *
     * @see ratpack.groovy.handling.GroovyChainAction
     * @see ratpack.groovy.handling.GroovyChain#prefix(String, ratpack.func.Action)
     */
    @Unroll
    def "can send a #requestHttpMethod request to #requestUri"() {
        given:
            def endpointUnderTest = new UserHandlers()

        when:
            // Using GroovyRequestFixture we can test the chain in isolation with different http methods, headers, uri's and more
            def result = GroovyRequestFixture.handle(endpointUnderTest) {
                method requestHttpMethod
                uri requestUri
            }

        then:
            result.sentResponse
            result.bodyText == expectedResponse

        where:
            requestUri      | requestHttpMethod   | expectedResponse
            ""              | "get"               | "user"
            ""              | "post"              | "user"
            "/dave"         | "get"               | "user/dave"
            "/fred"         | "get"               | "user/fred"
            "/dave/tweets"  | "get"               | "user/dave/tweets"
            "/fred/tweets"  | "get"               | "user/fred/tweets"
            "/dave/friends" | "get"               | "user/dave/friends"
            "fred/friends"  | "get"               | "user/fred/friends"
    }

}
