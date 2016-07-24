import static ratpack.groovy.Groovy.ratpack

ratpack {
    handlers {
        register {
            add(ErrorMessageFormatter, new DefaultErrorMessageFormatter())
            add("default message")
        }
        prefix("api") {
            get("error") {
                throw new Exception()
            }
        }
        prefix("with-custom-error-message") {
            get("error") {
                throw new Exception()
            }
        }
        get("error") {
            throw new Exception()
        }
    }
}
