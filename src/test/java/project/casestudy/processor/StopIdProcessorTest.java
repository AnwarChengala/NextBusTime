package project.casestudy.processor;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.jupiter.api.Test;
import project.casestudy.utils.TestUtil;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static project.casestudy.utils.CamelConstants.*;

public class StopIdProcessorTest {

    DefaultExchange exchange = new DefaultExchange(new DefaultCamelContext());
    private StopIdProcessor stopIdProcessor = new StopIdProcessor();

    @Test
    public void testProcessor() throws Exception {

        final String rawBody = TestUtil.getFileAsString("stopIdApiResponseBody.json");
        exchange.setProperty(BUS_STOP_NAME, "Target Field Station Platform 1");

        exchange.getIn().setBody(rawBody);

        stopIdProcessor.process(exchange);

        assertThat(exchange.getProperty(STOP_ID)).isNotNull().isEqualTo("TF12");
    }
}
