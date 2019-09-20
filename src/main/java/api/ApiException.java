package api;


/**
 * A skeleton for how to define exceptions from an API.
 */
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class AuthenticationFailure extends ApiException {
        public AuthenticationFailure(String message) {
            super(message);
        }

        public AuthenticationFailure(String message, Throwable t) {
            super(message, t);
        }
    }

    public static class InvalidParameter extends ApiException {
        public InvalidParameter(String message) {
            super(message);
        }

        public InvalidParameter(String message, Throwable t) {
            super(message, t);
        }
    }

    public static class NotFound extends ApiException {
        public NotFound(String message) {
            super(message);
        }

        public NotFound(String message, Throwable t) {
            super(message, t);
        }
    }

    public static class NotAllowed extends ApiException {
        public NotAllowed(String message) {
            super(message);
        }

        public NotAllowed(String message, Throwable t) {
            super(message, t);
        }
    }

    public static class PaymentNeeded extends ApiException {
        public PaymentNeeded(String message) {
            super(message);
        }

        public PaymentNeeded(String message, Throwable t) {
            super(message, t);
        }
    }

}
