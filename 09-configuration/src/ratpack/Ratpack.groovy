import ratpack.jackson.Jackson

import static ratpack.groovy.Groovy.ratpack

ratpack {
    serverConfig {
    }
    handlers {
        get("config") { Config config ->
            render Jackson.json(config)
        }
    }
}