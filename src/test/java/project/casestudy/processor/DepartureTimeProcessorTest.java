package project.casestudy.processor;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.jupiter.api.Test;
import project.casestudy.utils.TestUtil;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static project.casestudy.utils.CamelConstants.DEPARTING_TIME;
import static project.casestudy.utils.CamelConstants.DIRECTION_BOUND;

public class DepartureTimeProcessorTest {

    DefaultExchange exchange = new DefaultExchange(new DefaultCamelContext());
    private DepartureTimeProcessor departureTimeProcessor = new DepartureTimeProcessor();

    @Test
    public void testProcessor() throws Exception {

        final String rawBody = TestUtil.getFileAsString("departureTimeApiResponseBody.json");

        exchange.setProperty(DIRECTION_BOUND, "SOUTHBOUND");

        exchange.getIn().setBody(rawBody);

        departureTimeProcessor.process(exchange);

        assertThat(exchange.getProperty(DEPARTING_TIME)).isNotNull().isEqualTo("/Date(1585348980000-0500)/");
    }
}
