package xyz.thievery.thievery.exceptions;

public class IllegalActionException extends Exception {

    private final IllegalActionReason reason;

    public IllegalActionException(final IllegalActionReason reason, final String message) {
        super(message);
        this.reason = reason;
    }

    public IllegalActionReason getReason() {
        return reason;
    }

}
