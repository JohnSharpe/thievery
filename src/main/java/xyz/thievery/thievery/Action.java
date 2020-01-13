package xyz.thievery.thievery;

/**
 * This class represents one Action in the game of thievery to be performed by either player at any time.
 * It is the responsibility of Game to check it's validity, apply it, communicate the consequences e.t.c.
 * This class should be immutable.
 */
public class Action {

    private final Player player;
    private final ActionType actionType;

    public Action(
            final Player player,
            final ActionType actionType
    ) {
        this.player = player;
        this.actionType = actionType;
    }

    public Player getPlayer() {
        return player;
    }

    public ActionType getActionType() {
        return actionType;
    }
}
