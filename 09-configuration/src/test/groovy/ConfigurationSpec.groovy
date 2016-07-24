import com.fasterxml.jackson.databind.ObjectMapper
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.test.ApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class ConfigurationSpec extends Specification {

    @Shared
    ObjectMapper mapper = new ObjectMapper()

    @Shared
    @AutoCleanup
    ApplicationUnderTest applicationUnderTest = new GroovyRatpackMainApplicationUnderTest()

    @Delegate
    TestHttpClient client = applicationUnderTest.httpClient

    Config retrieveConfig() {
        mapper.readValue(getText("config"), Config)
    }

    /**
     * The handler that serves config as json is already available but the config needs to be added to the registry.
     *
     * @see ratpack.groovy.Groovy.Ratpack#serverConfig(Closure)
     * @see ratpack.server.ServerConfigBuilder#require(String, Class)
     */
    def "01 - can retrieve and deserialize config from server"() {
        expect:
            retrieveConfig()
    }

    /**
     * Populate the config using a json file added to the base (src/ratpack) directory so that this test passes
     *
     * @see ratpack.server.ServerConfigBuilder#json(String)
     */
    def "02 - can populate config using json"() {
        expect:
            retrieveConfig().first == "from json"
    }

    /**
     * Populate the config using a yaml file added to the base (src/ratpack) directory so that this test passes
     *
     * @see ratpack.server.ServerConfigBuilder#yaml(String)
     */
    def "03 - can populate config using yaml"() {
        expect:
            retrieveConfig().second == "from yaml"
    }

    /**
     * Populate the config using a properties file added to the base (src/ratpack) directory so that this test passes
     *
     * @see ratpack.server.ServerConfigBuilder#props(String)
     */
    def "04 - can populate config using properties file"() {
        expect:
            retrieveConfig().third == "from properties file"
    }

    /**
     * This test runs with system property of config.fourth set to "from system properties", populate the config using system properties so that this test passes
     *
     * @see ratpack.server.ServerConfigBuilder#sysProps(String)
     */
    def "05 - can populate config using system properties"() {
        expect:
            retrieveConfig().fourth == "from system properties"
    }

    /**
     * This test runs with environment variable CONFIG__FIFTH set to "from environment", populate the config using environment variables so that this test passes
     *
     * @see ratpack.server.ServerConfigBuilder#env(String)
     */
    def "06 - can populate config using environment variables"() {
        expect:
            retrieveConfig().fifth == "from environment"
    }

    /**
     * Add a custom config source that uses a fixed map to populate config with data which allows this test to pass
     *
     * @see ratpack.server.ServerConfigBuilder#add(ratpack.config.ConfigSource)
     * @see ratpack.config.ConfigSource
     * @see ObjectMapper#valueToTree(Object)
     */
    def "07 - can populate config using custom source"() {
        expect:
            retrieveConfig().sixth == "from custom source"
    }
}