package api.client.filters;

import io.restassured.filter.FilterContext;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Header;
import io.restassured.internal.support.Prettifier;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.slf4j.Logger;

public class LogbackResponseLoggingFilter extends ResponseLoggingFilter {

    private final Logger log;

    public LogbackResponseLoggingFilter(Logger log) {
        this.log = log;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);
        String statusCode = response.getStatusLine() != null ? response.getStatusLine() : "null";
        String prettifiedBody = new Prettifier().getPrettifiedBodyIfPossible(response, response.body());
        Header header = response.getHeaders().get("X-Request-Id");
        String requestId = header != null ? header.getValue() : "null";
        log.info("--Response:\n---Status: {}\n---X-Request-Id: {}\n---Body:\n{}", statusCode, requestId, prettifiedBody);
//        log.info("---Body:  \n {}", prettifiedBody);
        return response;
    }
}
