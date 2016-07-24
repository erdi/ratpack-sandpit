import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import ratpack.config.ConfigSource
import ratpack.file.FileSystemBinding

class FixedConfigSource implements ConfigSource {

    ObjectNode loadConfigData(ObjectMapper objectMapper, FileSystemBinding fileSystemBinding) throws Exception {
        objectMapper.valueToTree([sixth: "from custom source"])
    }

}
