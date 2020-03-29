package project.casestudy.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static project.casestudy.utils.CamelConstants.DEPARTING_TIME;
import static project.casestudy.utils.CamelConstants.TIME_TO_NEXT_BUS;

/**     CalculateDepartureTimeProcessor finds and sets the exchangeProperty value for TIME_TO_NEXT_BUS (e.g. : 8 minutes)
 *              which is used further along the route
 */

@Component
public class CalculateDepartureTimeProcessor implements Processor {

    @SuppressWarnings("unchecked")
    public void process(Exchange exchange) {

        String departureTime = exchange.getProperty(DEPARTING_TIME, String.class);

        var message = "The last bus for the day is not available";

        if (!StringUtils.isEmpty(departureTime)) {
            final var departingTime = Long.valueOf(departureTime.substring(6, 19)).longValue();
            final var timeNow = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

            long timeToNextBus = Math.toIntExact((departingTime - timeNow) / 60000);
            message = "The next bus leaves in " + timeToNextBus + (timeToNextBus > 1 ? " minutes" : " minute");
        }
        exchange.setProperty(TIME_TO_NEXT_BUS, message);
    }
}
