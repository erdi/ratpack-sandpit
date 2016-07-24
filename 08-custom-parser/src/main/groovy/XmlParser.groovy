import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.google.common.reflect.TypeToken
import ratpack.handling.Context
import ratpack.http.TypedData
import ratpack.parse.NoOptParserSupport

class XmlParser extends NoOptParserSupport {

    protected <T> T parse(Context context, TypedData requestBody, TypeToken<T> type) throws Exception {
        if (requestBody.contentType.type != "application/xml") {
            return null
        }

        def mapper = context.get(XmlMapper)
        mapper.readValue(requestBody.inputStream, toJavaType(type, mapper))
    }

    private <T> JavaType toJavaType(TypeToken<T> type, XmlMapper objectMapper) {
        objectMapper.typeFactory.constructType(type.type)
    }

}
