package xyz.thievery.thievery.units;

import xyz.thievery.thievery.Game;
import xyz.thievery.thievery.Player;
import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;

public class Thief extends Unit {

    private boolean carrying;

    public Thief(Player player) {
        super(player);
        this.carrying = false;
    }

    public void validateMove(final int destinationX, final int destinationY, final Guard myGuard) throws IllegalActionException {
        super.validateMove(destinationX, destinationY);

        if (destinationY != Game.HOST_HOME_ROW && destinationY != Game.OPPONENT_HOME_ROW &&
                destinationX == myGuard.getX() && destinationY == myGuard.getY()) {
            throw new IllegalActionException(IllegalActionReason.BLOCKED, "A thief can not land on his own guard.");
        }

        // TODO Consider moving this into the supertype
        if ((this.getY() == Game.HOST_HOME_ROW || this.getY() == Game.OPPONENT_HOME_ROW) &&
                (destinationY == Game.HOST_HOME_ROW || destinationY == Game.OPPONENT_HOME_ROW)) {
            throw new IllegalActionException(IllegalActionReason.ILLEGAL_MOVE, "A thief cannot (and does not need to) move laterally while in a home.");
        }

    }

    public boolean isCarrying() {
        return carrying;
    }

    public void setCarrying(boolean carrying) {
        this.carrying = carrying;
    }
}
