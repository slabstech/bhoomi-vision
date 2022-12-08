package planareas.api;

public class PlanAreaNotFoundException extends RuntimeException {

    public PlanAreaNotFoundException() {
        super();
    }

    public PlanAreaNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PlanAreaNotFoundException(final String message) {
        super(message);
    }

    public PlanAreaNotFoundException(final Throwable cause) {
        super(cause);
    }
}
