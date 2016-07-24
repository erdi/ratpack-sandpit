import ratpack.error.ServerErrorHandler
import ratpack.handling.Context

class CustomServerErrorHandler implements ServerErrorHandler {

    void error(Context context, Throwable throwable) throws Exception {
        def message = context.get(String)
        def formatter = context.get(ErrorMessageFormatter)
        def responseText = formatter.format("An error occurred - $message")
        context.response.send responseText
    }

}
