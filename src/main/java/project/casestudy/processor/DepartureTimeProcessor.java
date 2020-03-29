package project.casestudy.processor;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import static project.casestudy.utils.CamelConstants.*;

/**     DepartureTimeProcessor finds and sets the exchangeProperty value for DEPARTING_TIME (e.g., "\/Date(1585443780000-0500)\/")
 *              which is used further along the route
 */

@Component
public class DepartureTimeProcessor implements Processor {

    @SuppressWarnings("unchecked")
    public void process(Exchange exchange) {

        String directionBound = exchange.getProperty(DIRECTION_BOUND, String.class);
        String jsonArrayStr = exchange.getIn().getBody(String.class);

        Gson gson = new Gson();
        JsonElement element=gson.fromJson(jsonArrayStr, JsonElement.class);
        JsonArray jsonArrayObj = element.getAsJsonArray();

        if(jsonArrayObj.size() != 0 && jsonArrayObj.get(0).getAsJsonObject().get(ROUTE_DIRECTION).getAsString().equals(directionBound)) {
                exchange.setProperty(DEPARTING_TIME, jsonArrayObj.get(0).getAsJsonObject().get(DEPARTURE_TIME).getAsString());
            }
        }
}
