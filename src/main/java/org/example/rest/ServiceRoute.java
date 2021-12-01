package org.example.rest;

import io.vertx.core.json.Json;
import org.apache.camel.Exchange;
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

//        curl localhost:8080/version

        rest("/")
                .get("/version").produces("application/json").to("direct:version");

        from(direct("version"))
                .removeHeader(Exchange.HTTP_URI)
                .removeHeader(Exchange.HTTP_PATH)
                .to("https://gorest.co.in/public/v1/users/123/posts")
                .unmarshal().json()
                .log("${body}");

    }
    
}
