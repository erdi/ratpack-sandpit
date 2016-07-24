import ratpack.groovy.test.handling.GroovyRequestFixture
import spock.lang.Specification
import spock.lang.Unroll

class SoapActionHandlerSpec extends Specification {

    /**
     * Don't forget to add a dsl extension for SoapActionHandler to GroovyChainExtension.
     * Then update Ratpack.groovy with your new dsl addition and check `HandlerSpec` still passes.
     *
     * @see ratpack.handling.Context#insert(ratpack.handling.Handler...)
     */
    @Unroll
    def "can handle soap requests"() {
        given:
            def handlerUnderTest = new SoapActionHandler("foo", {
                response.send(request.headers.get("SOAPAction"))
            })

        when:
            // Using GroovyRequestFixture we can test the handler in isolation with different http methods, headers, uri's and more
            def result = GroovyRequestFixture.handle(handlerUnderTest) {
                method "POST"
                header "SOAPAction", soapAction
            }

        then:
            result.sentResponse == responseSent
            result.bodyText == expectedResponse

        where:
            soapAction  | responseSent  |   expectedResponse
            "foo"       | true          |   "foo"
            "bar"       | false         |   null
    }

}