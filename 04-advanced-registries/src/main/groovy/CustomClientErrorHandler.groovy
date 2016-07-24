import ratpack.error.ClientErrorHandler
import ratpack.handling.Context

class CustomClientErrorHandler implements ClientErrorHandler {

    void error(Context context, int statusCode) throws Exception {
        def message = context.get(String)
        def formatter = context.get(ErrorMessageFormatter)
        def responseText = formatter.format("A client error ($statusCode) occurred - $message")
        context.response.send responseText
    }

}
