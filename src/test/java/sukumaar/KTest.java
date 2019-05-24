package sukumaar;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.integration.utils.EmbeddedKafkaCluster;
import org.apache.kafka.test.MockMetricsReporter;
import org.apache.kafka.test.TestUtils;
import org.junit.*;
import org.junit.rules.TestName;

import java.util.Properties;

/**
 * Simple test for the embedded kafka
 * @author Sukumaar
 */
public class KTest {
    private static final int NUM_BROKERS = 1;
    // We need this to avoid the KafkaConsumer hanging on poll
    // (this may occur if the test doesn't complete quickly enough)
    @ClassRule
    public static final EmbeddedKafkaCluster CLUSTER = new EmbeddedKafkaCluster(NUM_BROKERS);
    private static final int NUM_THREADS = 2;
    private final StreamsBuilder builder = new StreamsBuilder();
    @Rule
    public TestName testName = new TestName();
    private KafkaStreams globalStreams;
    private Properties props;

    @Before
    public void before() {
        props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "appId");
        props.put(StreamsConfig.CLIENT_ID_CONFIG, "clientId");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, CLUSTER.bootstrapServers());
        props.put(StreamsConfig.METRIC_REPORTER_CLASSES_CONFIG, MockMetricsReporter.class.getName());
        props.put(StreamsConfig.STATE_DIR_CONFIG, TestUtils.tempDirectory().getPath());
        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, NUM_THREADS);
        globalStreams = new KafkaStreams(builder.build(), props);
    }

    @After
    public void cleanup() {
        if (globalStreams != null) {
            globalStreams.close();
        }
    }

    @Test
    public void thisIsFirstFakeTest() {
        assert true;
    }
}
