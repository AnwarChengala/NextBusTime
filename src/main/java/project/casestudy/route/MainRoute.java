package project.casestudy.route;

import org.apache.camel.CamelContext;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.http.NoHttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import project.casestudy.processor.*;

import java.util.concurrent.TimeUnit;

import static project.casestudy.utils.CamelConstants.*;

/**
 *    MainRoute represents the application that returns the time in minutes for a bus on
 *         "BUS ROUTE" leaving from "BUS STOP NAME" going in a "DIRECTION" using the API -
 *         https://svc.metrotransit.org/nextrip/. The application takes input from the application.yml file.
 *         If the last bus for the day is gone, the application returns nothing and exits..
 */

@Component
public class MainRoute extends RouteBuilder {

    public static final String NEXT_BUS_TIME_ROUTE_ID = "NextBusTimeRouteId";

    @Autowired
    RequestProcessor requestProcessor;

    @Autowired
    DirectionValidationProcessor directionValidationProcessor;

    @Autowired
    BusRouteIdValueProcessor busRouteIdValueProcessor;

    @Autowired
    DirectionBoundProcessor directionBoundProcessor;

    @Autowired
    StopIdProcessor stopIdProcessor;

    @Autowired
    DepartureTimeProcessor departureTimeProcessor;

    @Autowired
    CalculateDepartureTimeProcessor calculateDepartureTimeProcessor;

    @Value("${metrotransit.url:}")
    private String url;

    @Value("${file.location:}")
    private String location;

    @Override
    public void configure() throws Exception {

        // Retry in case of HttpException
        onException(HttpOperationFailedException.class, NoHttpResponseException.class)
                .maximumRedeliveries(5)
                .retryAttemptedLogLevel(LoggingLevel.WARN)
                .redeliveryDelay(TimeUnit.SECONDS.toMillis(5))
                .useExponentialBackOff()
                .maximumRedeliveryDelay(TimeUnit.MINUTES.toMillis(10));

        CamelContext context = new DefaultCamelContext();
        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {

                    from("direct:start")
                            .routeId(NEXT_BUS_TIME_ROUTE_ID)
                            .process(directionValidationProcessor)   // Validates the various entered variables
                            .toD(url + "Routes?format=json")
                            .process(busRouteIdValueProcessor) // to set the BusRouteId value

                            .choice()    // Program exits if BusRouteId value is null
                            .when(exchangeProperty(BUS_ROUTE_VALUE).isNull())
                            .log("RouteValue is not available for BusRouteId: ${exchangeProperty.BUS_ROUTE_NAME}.. Stopping the Program")
                            .stop().end()

                            .toD(url + "Directions/${exchangeProperty.BUS_ROUTE_VALUE}?format=json")
                            .process(directionBoundProcessor) // to set the DirectionBound value

                            .choice()     // Program exits if DirectionBound value is null
                            .when(exchangeProperty(DIRECTION_BOUND).isNull())
                            .log("DirectionBound is not available for RouteId: ${exchangeProperty.BUS_ROUTE_VALUE}.. Stopping the instance")
                            .stop().end()

                            .toD(url + "Stops/${exchangeProperty.BUS_ROUTE_VALUE}/${exchangeProperty.DIRECTION_ID}?format=json")
                            .process(stopIdProcessor)  // to set the StopId value

                            .choice()     // Program exits if StopId value is null
                            .when(exchangeProperty(STOP_ID).isNull())
                            .log("StopId is not available for RouteId: ${exchangeProperty.BUS_STOP_NAME}.. Stopping the instance")
                            .stop()
                            .end()

                            .toD(url + "${exchangeProperty.BUS_ROUTE_VALUE}/${exchangeProperty.DIRECTION_ID}/${exchangeProperty.STOP_ID}?format=json")
                            .process(departureTimeProcessor)   // Finds the departureTime from api

                            // Calculates the time in minutes for the next bus to arrive
                            // If the last bus for the day is gone, program prints that message
                            .process(calculateDepartureTimeProcessor)
                            .log("${exchangeProperty.TIME_TO_NEXT_BUS}")
                            .setBody(simple("${exchangeProperty.TIME_TO_NEXT_BUS}"))
                            .to("file:" + location);      // Prints the results to the specified file location
                }
            });

            FluentProducerTemplate fluent = context.createFluentProducerTemplate();
            context.start();

            fluent.to("direct:start").withProcessor(requestProcessor).asyncSend();

            Thread.sleep(2000);
        } finally {
            context.stop();
        }
    }

}
