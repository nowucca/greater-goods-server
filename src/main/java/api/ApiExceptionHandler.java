package api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.StringWriter;

/**
 * Jersey: Exception handler objects manage all exceptions that emerge from an API.
 */
@Provider
@Priority(Priorities.USER)
public class ApiExceptionHandler implements ExceptionMapper<ApiException> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Response toResponse(ApiException exception) {
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        if (exception instanceof ApiException.AuthenticationFailure) {
            status = Response.Status.UNAUTHORIZED;
        } else if (exception instanceof ApiException.InvalidParameter) {
            status = Response.Status.BAD_REQUEST;
        } else if (exception instanceof ApiException.NotFound) {
            status = Response.Status.NOT_FOUND;
        } else if (exception instanceof ApiException.NotAllowed) {
            status = Response.Status.FORBIDDEN;
        } else if (exception instanceof ApiException.PaymentNeeded) {
            status = Response.Status.PAYMENT_REQUIRED;
        } else {
            logger.warn("Got an internal server error ", exception);
        }
        try {
            StringWriter writer = new StringWriter();
            writer.append(status.getReasonPhrase()).append(" ").append(String.valueOf(status.getStatusCode())).append("\n\n").append(exception.getMessage());
            return Response.status(status).entity(writer.getBuffer().toString()).type(MediaType.TEXT_PLAIN).build();
        } catch (Exception e) {
            logger.error("Problem attempting to map an Exception to a json response", e);
            logger.error("Original Exception is - ", exception);
            return Response.serverError().build();
        }
    }
}
