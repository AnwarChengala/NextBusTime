package project.casestudy.processor;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static project.casestudy.utils.CamelConstants.DEPARTING_TIME;
import static project.casestudy.utils.CamelConstants.TIME_TO_NEXT_BUS;
import static org.hamcrest.MatcherAssert.assertThat;

public class CalculateDepartureTimeProcessorTest {

    DefaultExchange exchange = new DefaultExchange(new DefaultCamelContext());
    private CalculateDepartureTimeProcessor calculateDepartureTimeProcessor = new CalculateDepartureTimeProcessor();

    @Test
    public void testProcessor() {

        exchange.setProperty(DEPARTING_TIME, "/Date(1585348980000-0500)/");
        calculateDepartureTimeProcessor.process(exchange);

        assertThat(exchange.getProperty(TIME_TO_NEXT_BUS, String.class), containsString("minute"));
    }
}
