package xyz.thievery.thievery.units;

import xyz.thievery.thievery.Game;
import xyz.thievery.thievery.Player;
import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;
import xyz.thievery.thievery.units.ranges.Range;

public class Guard extends Unit {

    private Range range;

    public Guard(final Player player, final Range range) {
        super(player);
        this.range = range;
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

    public boolean hasCaught(final Thief thief) {

        boolean hasCaught = false;

        // Guards are powerless in their home row.
        if (Game.HOST_HOME_ROW == this.getY() || Game.OPPONENT_HOME_ROW == this.getY()) {
            return false;
        }

        // Guards always catch if they're right on
        if (this.getX() == thief.getX() && this.getY() == thief.getY()) {
            hasCaught = true;
        }

        // TODO Add the range

        return hasCaught;
    }

    public Range getRange() {
        return this.range;
    }

    public void setRange(final Range range) {
        this.range = range;
    }

}
