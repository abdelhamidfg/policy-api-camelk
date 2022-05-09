//camel-k: language=java dependency=camel-quarkus-openapi-java dependency=mvn:xalan:xalan:2.7.1
//camel-k: open-api=api/openapi.json resource=file:api/openapi.json property=file:cfg/svc.properties  resource=file:map/response-mapping.adm

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

public class PolicyAPI extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("direct:getPolicyPerCustomer")//operation id which used in open api spec
                // set customer_id query string in the exchange
                .setProperty("customer_id", simple("${header.customer_id}"))
                // call backend service xml over http
                .removeHeaders("*")
                .setHeader("Exchange.HTTP_METHOD", constant("GET"))
                .setHeader("Exchange.CONTENT_TYPE", constant("text/xml"))
                .setHeader("Exchange.HTTP_QUERY", simple("customer_id=${exchangeProperty.customer_id}"))
                .to("https:{{api.backend1.host}}/rest/Policy+Backend+API/1.0.0/policy?throwExceptionOnFailure=false")//dont throw exception in case of 404 response
                //transform backend response  xml ..> json
                .choice()
                .when(header(Exchange.HTTP_RESPONSE_CODE).isNotEqualTo("404"))
                 .to("atlasmap:{{api.resources}}/response-mapping.adm")
                
                 ;

        
        ;

    }
}
