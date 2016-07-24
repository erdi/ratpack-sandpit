import groovy.json.JsonOutput

class JsonErrorMessageFormatter implements ErrorMessageFormatter {

    String format(String message) {
        JsonOutput.toJson(error: message)
    }

}
