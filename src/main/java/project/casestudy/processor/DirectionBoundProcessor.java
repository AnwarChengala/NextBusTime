package project.casestudy.processor;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import static project.casestudy.utils.CamelConstants.*;

/**     DirectionBoundProcessor finds and sets the exchangeProperty value for DIRECTION_BOUND (e.g., SOUTHBOUND)
 *              which is used further along the route
 */

@Component
public class DirectionBoundProcessor implements Processor {

    @SuppressWarnings("unchecked")
    public void process(Exchange exchange) {

        String direction = exchange.getProperty(DIRECTION_ID, String.class);
        String jsonArrayStr = exchange.getIn().getBody(String.class);

        Gson gson = new Gson();
        JsonElement element = gson.fromJson(jsonArrayStr, JsonElement.class);
        JsonArray jsonArrayObj = element.getAsJsonArray();

        jsonArrayObj.forEach(obj -> {
            final var asJsonObject = obj.getAsJsonObject();
            if (asJsonObject.get(VALUE).getAsString().equals(direction.toUpperCase())) {
                exchange.setProperty(DIRECTION_BOUND, asJsonObject.get(TEXT).getAsString());
            }
        });
    }
}
