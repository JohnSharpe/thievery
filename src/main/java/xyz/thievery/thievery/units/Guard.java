package xyz.thievery.thievery.units;

import xyz.thievery.thievery.Game;
import xyz.thievery.thievery.Player;
import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;

public class Guard extends Unit {

    public Guard(final Player player) {
        super(player);
    }

    public void validateMove(final int destinationX, final int destinationY, final Thief myThief, final Guard theirGuard) throws IllegalActionException {
        super.validateMove(destinationX, destinationY);

        if (destinationY == Game.HOST_HOME_ROW || destinationY == Game.OPPONENT_HOME_ROW) {
            throw new IllegalActionException(IllegalActionReason.ILLEGAL_MOVE, "A guard can not enter a home.");
        }

        if (destinationX == myThief.getX() && destinationY == myThief.getY()) {
            throw new IllegalActionException(IllegalActionReason.BLOCKED, "A guard can not land on his own thief.");
        }

        if (destinationX == theirGuard.getX() && destinationY == theirGuard.getY()) {
            throw new IllegalActionException(IllegalActionReason.BLOCKED, "Your guard may occupy the same space as your opponent's guard.");
        }
    }

}
