import ratpack.jackson.Jackson

class JacksonExtension {

    static xml(Jackson jackson, Object object) {
        new DefaultXmlRender(object)
    }

}
