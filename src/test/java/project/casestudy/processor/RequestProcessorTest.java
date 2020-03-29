package project.casestudy.processor;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static project.casestudy.utils.CamelConstants.*;

public class RequestProcessorTest {

    DefaultExchange exchange = new DefaultExchange(new DefaultCamelContext());

    @Test
    public void testHappyPathProcessor() throws Exception {

        RequestProcessor requestProcessor =
                new RequestProcessor("METRO Blue Line", "Target Field Station Platform 1", "south");

        requestProcessor.process(exchange);

        assertThat(exchange.getProperty(BUS_ROUTE_NAME)).isNotNull().isEqualTo("METRO Blue Line");
        assertThat(exchange.getProperty(BUS_STOP_NAME)).isNotNull().isEqualTo("Target Field Station Platform 1");
        assertThat(exchange.getProperty(DIRECTION)).isNotNull().isEqualTo("south");
    }

    @Test
    public void testUnHappyPathProcessor() throws Exception {

        RequestProcessor requestProcessor =
                new RequestProcessor("", "Target Field Station Platform 1", "south");

        requestProcessor.process(exchange);

        assertThat(exchange.getProperty(BUS_ROUTE_NAME)).isNotNull().isEqualTo("");
        assertThat(exchange.getProperty(Exchange.ROUTE_STOP)).isNotNull().isEqualTo(true);

        assertThat(exchange.getProperty(BUS_STOP_NAME)).isNotNull().isEqualTo("Target Field Station Platform 1");
        assertThat(exchange.getProperty(DIRECTION)).isNotNull().isEqualTo("south");
    }

}
