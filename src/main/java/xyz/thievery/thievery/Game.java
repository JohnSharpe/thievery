package xyz.thievery.thievery;

import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;
import xyz.thievery.thievery.units.Guard;

/**
 * A single game of Thievery.
 *
 * The map looks like this
 *                ________________________
 * 6 (Opp Home)  |                        |
 *               |________________________|
 * 5             |    |    |    |    |    |
 *               |____|____|____|____|____|
 * 4             |    |    |    |    |    |
 *               |____|____|____|____|____|
 * 3             |    |    |    |    |    |
 *               |____|____|____|____|____|
 * 2             |    |    |    |    |    |
 *               |____|____|____|____|____|
 * 1             |    |    |    |    |    |
 *               | ___|____|____|____|____|
 * 0 (Host Home) |                        |
 *               |________________________|
 *
 *                 0    1    2    3    4
 */
public class Game {

    public static final int LEFTMOST_COLUMN = 0;
    public static final int RIGHTMOST_COLUMN = 4;

    public static final int HOST_HOME_ROW = 0;
    public static final int OPPONENT_HOME_ROW = 6;

    private static final int ACTIONS_PER_TURN = 3;

    private Status status;
    private int remainingActions;

    private Guard hostGuard;

    private Guard opponentGuard;

    public Game() {
        this.status = Status.HOST_TURN;
        this.remainingActions = ACTIONS_PER_TURN;

        this.hostGuard = new Guard(Player.HOST);
        this.opponentGuard = new Guard(Player.OPPONENT);
    }

    public Status getStatus() {
        return status;
    }

    public Guard getHostGuard() {
        return hostGuard;
    }

    public Guard getOpponentGuard() {
        return opponentGuard;
    }

    public void performAction(final Action action) throws IllegalActionException {
        // This is general validation, regardless of the specifics of Action
        this.generalValidation(action);

        // Per-action validation should occur here before making any changes
        switch (action.getActionType()) {
            case END_TURN:
                this.endTurn();
                return;
            case MOVE_GUARD:
                final Guard myGuard = action.getPlayer() == Player.HOST ? this.hostGuard : this.opponentGuard;
                this.moveGuardValidate(action, myGuard);
                myGuard.move(action.getX(), action.getY());
                break;
//            case MOVE_THIEF:
//            case REVEAL:
//                this.remainingActions--;
//                break;

//            default:
//                throw new IllegalActionException(
//                        IllegalActionReason.UNRECOGNISED_ACTION_TYPE, "Unrecognised action type.");
        }

        this.remainingActions--;

        if (this.remainingActions == 0) {
            this.endTurn();
        }

    }

    private void generalValidation(final Action action) throws IllegalActionException {
        //        if (Status.END == this.status) {
//            throw new IllegalActionException("An action cannot be performmed on an ended game.");
//        }

        if (Status.HOST_TURN == this.status && Player.HOST != action.getPlayer()) {
            throw new IllegalActionException(IllegalActionReason.NOT_YOUR_TURN, "It is not the host's turn.");
        }
        if (Status.OPPONENT_TURN == this.status && Player.OPPONENT != action.getPlayer()) {
            throw new IllegalActionException(IllegalActionReason.NOT_YOUR_TURN, "It is not the opponent's turn.");
        }
    }

    private void moveGuardValidate(final Action action, final Guard myGuard) throws IllegalActionException {
        // TODO This looks like it'd be relevant to thieves too
        if (action.getX() < LEFTMOST_COLUMN || action.getX() > RIGHTMOST_COLUMN || action.getY() < HOST_HOME_ROW || action.getY() > OPPONENT_HOME_ROW) {
            throw new IllegalActionException(IllegalActionReason.NO_SUCH_POSITION, "No unit can move off-map");
        }

        // TODO This looks like it'd be relevant to thieves too
        // TODO Consider making 0 and 6 (and other magic numbers) fields
        final int xDifference;
        if (myGuard.getY() == HOST_HOME_ROW || myGuard.getY() == OPPONENT_HOME_ROW) {
            // The guard is currently home, x can be anything on-map
            xDifference = 0;
        } else {
            xDifference = myGuard.getX() - action.getX();
        }

        if (xDifference < -1 || xDifference > 1) {
            throw new IllegalActionException(IllegalActionReason.NOT_WITHIN_REACH, "No unit can move by more than 1 space.");
        }

        final int yDifference = myGuard.getY() - action.getY();
        if (yDifference < -1 || yDifference > 1) {
            throw new IllegalActionException(IllegalActionReason.NOT_WITHIN_REACH, "No unit can move by more than 1 space.");
        }
        if (xDifference != 0 && yDifference != 0) {
            throw new IllegalActionException(IllegalActionReason.NOT_WITHIN_REACH, "No unit can move by more than 1 space.");
        }
        if (xDifference == 0 && yDifference == 0) {
            throw new IllegalActionException(IllegalActionReason.ILLEGAL_MOVE, "A nil move is not possible.");
        }

        if (action.getY() == HOST_HOME_ROW || action.getY() == OPPONENT_HOME_ROW) {
            throw new IllegalActionException(IllegalActionReason.ILLEGAL_MOVE, "A guard can not enter a home.");
        }

    }

    private void endTurn() {
        if (Status.HOST_TURN == this.status) {
            this.status = Status.OPPONENT_TURN;
        } else if (Status.OPPONENT_TURN == this.status) {
            this.status = Status.HOST_TURN;
        }

        this.remainingActions = ACTIONS_PER_TURN;
    }

}
