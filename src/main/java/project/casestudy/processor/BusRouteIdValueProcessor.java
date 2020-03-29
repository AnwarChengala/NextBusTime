package project.casestudy.processor;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import static project.casestudy.utils.CamelConstants.*;

/**     BusRouteIdValueProcessor finds and sets the exchangeProperty value for BUS_ROUTE_VALUE (e.g. : 901)
 *              which is used further along the route
 */

@Component
public class BusRouteIdValueProcessor implements Processor {

    @SuppressWarnings("unchecked")
    public void process(Exchange exchange) {

        String jsonArrayStr = exchange.getIn().getBody(String.class);
        String routeId = exchange.getProperty(BUS_ROUTE_NAME, String.class);

        Gson gson = new Gson();
        JsonElement element=gson.fromJson(jsonArrayStr, JsonElement.class);
        JsonArray jsonArrayObj = element.getAsJsonArray();

        jsonArrayObj.forEach(obj -> {
            final var asJsonObject = obj.getAsJsonObject();
            if (asJsonObject.get(DESCRIPTION).getAsString().equals(routeId)) {
                exchange.setProperty(BUS_ROUTE_VALUE, asJsonObject.get(ROUTE).getAsString());
            }
        });
    }
}

