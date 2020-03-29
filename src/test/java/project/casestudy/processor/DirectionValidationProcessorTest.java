package project.casestudy.processor;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static project.casestudy.utils.CamelConstants.DIRECTION;
import static project.casestudy.utils.CamelConstants.DIRECTION_ID;

public class DirectionValidationProcessorTest {

    DefaultExchange exchange = new DefaultExchange(new DefaultCamelContext());
    private DirectionValidationProcessor directionValidationProcessor = new DirectionValidationProcessor();

    @Test
    public void testProcessor() {

        exchange.setProperty(DIRECTION, "south");

        directionValidationProcessor.process(exchange);

        assertThat(exchange.getProperty(DIRECTION_ID)).isNotNull().isEqualTo("1");
    }
}
