package xyz.thievery.thievery.units;

import xyz.thievery.thievery.Player;
import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;

public class Thief extends Unit {

    public Thief(Player player) {
        super(player);
    }

    public void validateMove(final int destinationX, final int destinationY, final Guard myGuard) throws IllegalActionException {
        super.validateMove(destinationX, destinationY);

        if (destinationX == myGuard.getX() && destinationY == myGuard.getY()) {
            throw new IllegalActionException(IllegalActionReason.BLOCKED, "A thief can not land on his own guard.");
        }
    }
}
