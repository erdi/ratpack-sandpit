import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.handling.RequestLogger

import static ratpack.groovy.Groovy.ratpack

final Logger log = LoggerFactory.getLogger("Ratpack")

ratpack {
    handlers {
        all RequestLogger.ncsa(log)
        get {
            response.send "Hello Ratpackers!"
        }
        prefix("user", new UserHandlers())
        prefix("api/ws") {
            soapAction("getTweets") {
                response.send "api/ws - getTweets"
            }
            soapAction("getFriends") {
                response.send "api/ws - getFriends"
            }
        }
        files {
            it.path("assets").dir("public")
        }
        files {
            it.dir("pages").indexFiles("index.html")
        }
    }
}