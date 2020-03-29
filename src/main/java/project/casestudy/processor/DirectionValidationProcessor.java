package project.casestudy.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import project.casestudy.utils.Direction;

import static project.casestudy.utils.CamelConstants.DIRECTION;
import static project.casestudy.utils.CamelConstants.DIRECTION_ID;

/**     DirectionValidationProcessor validates if the user entered the correct direction as described in Direction.class,
 *        otherwise the program exits. If the direction is validated, the value of the direction (e.g., "4" for south) is
 *        set in the exchangeProperty for DIRECTION_ID
 */

@Component
public class DirectionValidationProcessor implements Processor {

    private final static Logger LOGGER = LoggerFactory.getLogger(DirectionValidationProcessor.class);

    @SuppressWarnings("unchecked")
    public void process(Exchange exchange) {

        String direction = exchange.getProperty(DIRECTION, String.class);

        if(direction.toUpperCase().equals(Direction.SOUTH.name()) || (direction.toUpperCase().equals(Direction.EAST.name()) ||
                (direction.toUpperCase().equals(Direction.WEST.name())) || (direction.toUpperCase().equals(Direction.NORTH.name())))) {
            exchange.setProperty(DIRECTION_ID, Direction.valueOf(direction.toUpperCase()).toString());
        } else {
            LOGGER.info("Entered direction, {} is invalid.. Exiting the program", direction);
            exchange.setProperty(Exchange.ROUTE_STOP, Boolean.TRUE);
        }
    }
}
