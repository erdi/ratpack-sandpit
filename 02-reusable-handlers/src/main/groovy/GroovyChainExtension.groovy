import ratpack.groovy.handling.GroovyChain
import ratpack.groovy.handling.GroovyContext

class GroovyChainExtension {

    static void soapAction(GroovyChain chain, String soapAction, @DelegatesTo(value = GroovyContext, strategy = Closure.DELEGATE_FIRST) Closure<?> handler) {
        chain.all(new SoapActionHandler(soapAction, handler))
    }

}
