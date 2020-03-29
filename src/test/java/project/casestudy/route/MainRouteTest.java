package project.study.route;

import org.apache.camel.*;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.casestudy.application.NextBusTimeApplication;

import static project.casestudy.utils.CamelConstants.*;

@SpringBootTest(classes = NextBusTimeApplication.class)
@RunWith(CamelSpringBootRunner.class)
public class MainRouteTest {

    @Autowired
    CamelContext camelContext;

    @EndpointInject(uri = "mock:fileEndpoint")
    protected MockEndpoint mockEndpoint;

    @Before
    public void setUp() throws Exception {
        camelContext.start();
    }

    @Test
    public void testHappyPath() throws Exception {
        DefaultExchange exchange = new DefaultExchange(new DefaultCamelContext());

        exchange.setProperty(BUS_ROUTE_NAME, "METRO Blue Line");
        exchange.setProperty(BUS_STOP_NAME, "Target Field Station Platform 1");
        exchange.setProperty(DIRECTION, "south");

        FluentProducerTemplate fluent = camelContext.createFluentProducerTemplate();
        fluent.to("direct:start").withExchange(exchange).asyncSend();

        mockEndpoint.expectedMessageCount(1);
    }

}
