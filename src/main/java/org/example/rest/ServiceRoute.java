package org.example.rest;

import org.apache.camel.Exchange;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;

@ApplicationScoped
public class ServiceRoute extends EndpointRouteBuilder {
    
    @Override
    public void configure() throws Exception {

        restConfiguration()
                .apiContextPath("api-doc")
                .bindingMode(RestBindingMode.json);

        rest("/")
                .put("/messages").consumes("application/json")
                .to("direct:messages");


        from(direct("messages"))
                .process(this::setTransactionData)
                .log("${headers}")
                .setBody(simple("Result is OK"));

//        from(direct("messages"))
//                .setHeader("Message", jsonpath("$"))
//                .setHeader("Id", jsonpath("$.id"))
//                .setHeader("Type", constant("Received"))
//                .log("${headers}")
//                .setBody(simple("Result is OK"));
    }

    private void setTransactionData(Exchange exchange) {
        Map<String,Object> json = exchange.getIn().getBody(Map.class);
        String id = json.get("id").toString();
        exchange.getIn().setHeader("Message", id);
        exchange.getIn().setHeader("Id", Integer.parseInt(id));
        exchange.getIn().setHeader("Type", "Received");
    }
    
}
