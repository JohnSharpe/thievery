package xyz.thievery.thievery;

/**
 * This class represents one Action in the game of thievery to be performed by either player at any time.
 * It is the responsibility of Game to check it's validity, apply it, communicate the consequences e.t.c.
 * This class should be immutable.
 */
public class Action {

    private final String playerIdentifier;
    private final ActionType actionType;

    public Action(
            final String playerIdentifier,
            final ActionType actionType
    ) {
        this.playerIdentifier = playerIdentifier;
        this.actionType = actionType;
    }

    public String getPlayerIdentifier() {
        return playerIdentifier;
    }

    public ActionType getActionType() {
        return actionType;
    }
}
