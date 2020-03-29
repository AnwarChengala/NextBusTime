package project.casestudy.processor;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.jupiter.api.Test;
import project.casestudy.utils.TestUtil;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static project.casestudy.utils.CamelConstants.BUS_ROUTE_NAME;
import static project.casestudy.utils.CamelConstants.BUS_ROUTE_VALUE;

public class BusRouteIdValueProcessorTest {

    DefaultExchange exchange = new DefaultExchange(new DefaultCamelContext());
    private BusRouteIdValueProcessor busRouteIdValueProcessor = new BusRouteIdValueProcessor();

    @Test
    public void testProcessor() throws Exception {

        final String rawBody = TestUtil.getFileAsString("busRouteIdValueApiResponseBody.json");
        exchange.setProperty(BUS_ROUTE_NAME, "METRO Blue Line");
        exchange.getIn().setBody(rawBody);

        busRouteIdValueProcessor.process(exchange);

        assertThat(exchange.getProperty(BUS_ROUTE_VALUE)).isNotNull().isEqualTo("901");
    }
}
