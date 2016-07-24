import ratpack.error.ClientErrorHandler
import ratpack.error.ServerErrorHandler
import ratpack.registry.Registry

import static ratpack.groovy.Groovy.ratpack

ratpack {
    handlers {
        register {
            add(ServerErrorHandler, new CustomServerErrorHandler())
            add(ClientErrorHandler, new CustomClientErrorHandler())
            add(ErrorMessageFormatter, new DefaultErrorMessageFormatter())
            add("default message")
        }
        all {
            byContent {
                json {
                    next(Registry.single(ErrorMessageFormatter, new JsonErrorMessageFormatter()))
                }
                noMatch {
                    next()
                }
            }
        }
        prefix("api") {
            register {
                add("api message")
            }
            get("error") {
                throw new Exception()
            }
        }
        prefix("with-custom-error-message") {
            all {
                request.add("custom message")
                next()
            }
            get("error") {
                throw new Exception()
            }
        }
        get("error") {
            throw new Exception()
        }
    }
}
