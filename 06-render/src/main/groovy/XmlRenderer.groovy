import com.fasterxml.jackson.dataformat.xml.XmlMapper
import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufAllocator
import io.netty.buffer.ByteBufOutputStream
import ratpack.handling.Context
import ratpack.render.RendererSupport

class XmlRenderer extends RendererSupport<XmlRender> {

    void render(Context context, XmlRender xmlRender) throws Exception {
        XmlMapper mapper = context.get(XmlMapper)

        ByteBuf buffer = context.get(ByteBufAllocator).buffer()
        OutputStream outputStream = new ByteBufOutputStream(buffer)

        try {
            mapper.writeValue(outputStream, xmlRender.object)
        } catch (IOException e) {
            buffer.release()
            context.error(e)
            return
        }

        context.getResponse()
            .contentTypeIfNotSet("application/xml")
            .send(buffer)
    }

}
