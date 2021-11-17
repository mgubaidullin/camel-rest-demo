package org.example.rest;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class ServiceRoute extends EndpointRouteBuilder {
    
    @Override
    public void configure() throws Exception {

        restConfiguration()
                .apiContextPath("api-doc")
                .bindingMode(RestBindingMode.json);

//        curl localhost:8080/transactions
//        curl -X POST -H "Content-Type: application/json" --data '{"id":1,"amount":1000,"description":"Demo"}' http://localhost:8080/transactions

        rest("/")
                .get("/transactions").type(ServiceDto.class).produces("application/json").to("direct:getAll")
                .post("/transactions").type(ServiceDto.class).consumes("application/json").produces("application/json").to("direct:post");
        
        from(direct("getAll"))
                .setBody(constant(List.of(new ServiceDto(1l, new BigDecimal(1000), "Demo"))))
        	.log("${body}");
        
        from(direct("post"))
    	    .log("${body}")
                .process(exchange -> {
                    ServiceDto dto = exchange.getIn().getBody(ServiceDto.class);
                    System.out.println(dto.getId());
                });
    }
    
}
