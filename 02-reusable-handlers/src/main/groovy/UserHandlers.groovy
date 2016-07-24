import groovy.util.logging.Slf4j
import ratpack.groovy.handling.GroovyChainAction

@Slf4j
class UserHandlers extends GroovyChainAction {

    void execute() throws Exception {
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

}
