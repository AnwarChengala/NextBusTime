package project.casestudy.processor;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.jupiter.api.Test;
import project.casestudy.utils.TestUtil;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static project.casestudy.utils.CamelConstants.DIRECTION_BOUND;
import static project.casestudy.utils.CamelConstants.DIRECTION_ID;

public class DirectionBoundProcessorTest {

    DefaultExchange exchange = new DefaultExchange(new DefaultCamelContext());
    private DirectionBoundProcessor directionBoundProcessor = new DirectionBoundProcessor();

    @Test
    public void testProcessor() throws Exception {

        final String rawBody = TestUtil.getFileAsString("directionBoundApiResponseBody.json");

        exchange.setProperty(DIRECTION_ID, "1");

        exchange.getIn().setBody(rawBody);

        directionBoundProcessor.process(exchange);

        assertThat(exchange.getProperty(DIRECTION_BOUND)).isNotNull().isEqualTo("SOUTHBOUND");
    }
}
