package xyz.thievery.thievery.units;

import xyz.thievery.thievery.Game;
import xyz.thievery.thievery.Player;
import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;

public abstract class Unit {

    private int x;
    private int y;

    public Unit(final Player player) {
        if (Player.HOST == player) {
            this.x = 0;
            this.y = Game.HOST_HOME_ROW;
        } else {
            this.x = 0;
            this.y = Game.OPPONENT_HOME_ROW;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void executeMove(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    protected void validateMove(final int destinationX, final int destinationY) throws IllegalActionException {
        if (destinationX < Game.LEFTMOST_COLUMN || destinationX > Game.RIGHTMOST_COLUMN ||
                destinationY < Game.HOST_HOME_ROW || destinationY > Game.OPPONENT_HOME_ROW) {
            throw new IllegalActionException(IllegalActionReason.NO_SUCH_POSITION, "No unit can move off-map");
        }

        final int xDifference;
        if (this.y == Game.HOST_HOME_ROW || this.y == Game.OPPONENT_HOME_ROW) {
            // The unit is currently home, x can be anything on-map
            xDifference = 0;
        } else {
            xDifference = this.x - destinationX;
        }

        if (xDifference < -1 || xDifference > 1) {
            throw new IllegalActionException(IllegalActionReason.NOT_WITHIN_REACH, "No unit can move by more than 1 space.");
        }

        final int yDifference = this.y - destinationY;
        if (yDifference < -1 || yDifference > 1) {
            throw new IllegalActionException(IllegalActionReason.NOT_WITHIN_REACH, "No unit can move by more than 1 space.");
        }
        if (xDifference != 0 && yDifference != 0) {
            throw new IllegalActionException(IllegalActionReason.NOT_WITHIN_REACH, "No unit can move by more than 1 space.");
        }
        if (xDifference == 0 && yDifference == 0) {
            throw new IllegalActionException(IllegalActionReason.ILLEGAL_MOVE, "A nil move is not possible.");
        }
    }
}
