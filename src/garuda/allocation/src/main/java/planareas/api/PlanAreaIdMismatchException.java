package planareas.api;

public class PlanAreaIdMismatchException extends RuntimeException {

    public PlanAreaIdMismatchException() {
        super();
    }

    public PlanAreaIdMismatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PlanAreaIdMismatchException(final String message) {
        super(message);
    }

    public PlanAreaIdMismatchException(final Throwable cause) {
        super(cause);
    }
}
