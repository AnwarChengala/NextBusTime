package project.casestudy.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static project.casestudy.utils.CamelConstants.*;

/**     RequestProcessor sets the properties - BUS_ROUTE_NAME, BUS_STOP_NAME, DIRECTION which is further used along
 *            in the route. If the values are null / invalid, the program exits. Also, note that the program exits in
 *            case the entereD values does not fully match with what is available in the apis
 */

@Component
public class RequestProcessor implements Processor {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestProcessor.class);

    @Value("${bus.route.name}")
    private String routeId;

    @Value("${bus.stop.name}")
    private String stopId;

    @Value("${direction}")
    private String directionId;

    private RequestProcessor() { }

    public RequestProcessor(String routeId, String stopId, String directionId) {
        this.routeId = routeId;
        this.stopId = stopId;
        this.directionId = directionId;
    }

    @SuppressWarnings("unchecked")
    public void process(Exchange exchange) {

        validateVariables(exchange);

        exchange.setProperty(BUS_ROUTE_NAME, routeId);
        exchange.setProperty(BUS_STOP_NAME, stopId);
        exchange.setProperty(DIRECTION, directionId);
    }

    public void validateVariables(Exchange exchange) {

        if (routeId == null || routeId.trim().length() == 0) {
            LOGGER.info("Entered route id is null.. Exiting the Program", routeId);
            exchange.setProperty(Exchange.ROUTE_STOP, Boolean.TRUE);
        }

        if (stopId == null || stopId.trim().length() == 0) {
            LOGGER.info("Entered stopId is null.. Exiting the Program", routeId);
            exchange.setProperty(Exchange.ROUTE_STOP, Boolean.TRUE);
        }

        if (directionId == null || directionId.trim().length() == 0) {
            LOGGER.info("Entered direction is null.. Exiting the Program", routeId);
            exchange.setProperty(Exchange.ROUTE_STOP, Boolean.TRUE);
        }
    }
}

