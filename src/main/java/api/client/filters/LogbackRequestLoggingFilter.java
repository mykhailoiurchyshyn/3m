package api.client.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.filter.FilterContext;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.slf4j.Logger;

public class LogbackRequestLoggingFilter extends RequestLoggingFilter {

    private final Logger log;

    public LogbackRequestLoggingFilter(Logger log) {
        this.log = log;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        log.info("--Request: {} : {}\n---Headers:\n{}\n---Body:\n{}",
                requestSpec.getMethod(), requestSpec.getURI(), requestSpec.getHeaders(), getRequestBodyAsJson(requestSpec.getBody()));
//        log.info("---Headers: \n {} \n ---Body:  \n {}", requestSpec.getHeaders(), requestSpec.getBody());

        return ctx.next(requestSpec, responseSpec);
    }

    String getRequestBodyAsJson(String requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(requestBody);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (JsonProcessingException | IllegalArgumentException e) {
            log.warn("Could not parse request body: {}", requestBody);
            return requestBody;
        }
    }
}
