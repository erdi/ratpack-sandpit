import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.handling.GroovyHandler
import ratpack.handling.Handler

import static ratpack.groovy.Groovy.groovyHandler

class SoapActionHandler extends GroovyHandler {

    private final String soapAction
    private final Handler handler

    SoapActionHandler(String soapAction, @DelegatesTo(value = GroovyContext, strategy = Closure.DELEGATE_FIRST) Closure<?> handler) {
        this.soapAction = soapAction
        this.handler = groovyHandler(handler)
    }

    void handle(GroovyContext ctx) throws Exception {
        if (ctx.request.headers.get("SOAPAction") == soapAction) {
            ctx.byMethod {
                post { handler.handle(ctx) }
            }
        } else {
            ctx.next()
        }
    }

}
