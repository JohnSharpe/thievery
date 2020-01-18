package xyz.thievery.thievery;

import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;

/**
 * This class represents one Action in the game of thievery to be performed by either player at any time.
 * It is the responsibility of Game to check it's validity, apply it, communicate the consequences e.t.c.
 * This class should be immutable.
 */
public class Action {

    private final Player player;
    private final ActionType actionType;

    private final Integer x;
    private final Integer y;

    public Action(final Player player, final ActionType actionType) throws IllegalActionException {
        if (player == null) {
            throw new IllegalActionException(IllegalActionReason.MALFORMED_ACTION, "An Action cannot exist without a player performing it.");
        }
        if (actionType == null) {
            throw new IllegalActionException(IllegalActionReason.MALFORMED_ACTION, "An Action cannot exist without a type.");
        }
        if (actionType == ActionType.MOVE_GUARD || actionType == ActionType.MOVE_THIEF) {
            throw new IllegalActionException(IllegalActionReason.MALFORMED_ACTION, "An action of type MOVE_GUARD or MOVE_THIEF needs destination coordinates.");
        }

        this.player = player;
        this.actionType = actionType;

        this.x = null;
        this.y = null;
    }

    public Action(final Player player, final ActionType actionType, final Integer x, final Integer y) throws IllegalActionException {
        if (player == null) {
            throw new IllegalActionException(IllegalActionReason.MALFORMED_ACTION, "An Action cannot exist without a player performing it.");
        }
        if (actionType == null) {
            throw new IllegalActionException(IllegalActionReason.MALFORMED_ACTION, "An Action cannot exist without a type.");
        }
        if ((actionType == ActionType.MOVE_GUARD || actionType == ActionType.MOVE_THIEF) &&
                (x == null || y == null)) {
            throw new IllegalActionException(IllegalActionReason.MALFORMED_ACTION, "An action of type MOVE_GUARD or MOVE_THIEF needs destination coordinates.");
        }
        this.player = player;
        this.actionType = actionType;
        this.x = x;
        this.y = y;
    }

    public Player getPlayer() {
        return player;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

}
