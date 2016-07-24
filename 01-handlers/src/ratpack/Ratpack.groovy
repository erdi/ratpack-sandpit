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
        prefix("user") {
            path {
                byMethod {
                    get {
                        response.send "user"
                    }
                    post {
                        response.send "user"
                    }
                }
            }
            path(":match:f.*") {
                def username = pathTokens["match"].tokenize("/").first()
                log.info "Warning, request for $username"
                next()
            }
            prefix(":username") {
                get {
                    response.send "user/${allPathTokens["username"]}"
                }
                get("tweets") {
                    response.send "user/${allPathTokens["username"]}/tweets"
                }
                get("friends") {
                    response.send "user/${allPathTokens["username"]}/friends"
                }
            }
        }
        prefix("api/ws") {
            when({request.headers.get("SOAPAction") == "getTweets"}) {
                post {
                    response.send "api/ws - getTweets"
                }
            }
            when({ request.headers.get("SOAPAction") == "getFriends" }) {
                post {
                    response.send "api/ws - getFriends"
                }
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