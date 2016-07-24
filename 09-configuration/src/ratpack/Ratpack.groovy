import ratpack.jackson.Jackson

import static ratpack.groovy.Groovy.ratpack

ratpack {
    serverConfig {
        json("config.json")
        yaml("config.yaml")
        props("config.properties")
        sysProps("config.")
        env("CONFIG__")
        add(new FixedConfigSource())
        require("", Config)
    }
    handlers {
        get("config") { Config config ->
            render Jackson.json(config)
        }
    }
}