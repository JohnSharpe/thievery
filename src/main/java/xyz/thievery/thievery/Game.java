package xyz.thievery.thievery;

import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;
import xyz.thievery.thievery.units.Guard;
import xyz.thievery.thievery.units.Thief;

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

    private final Guard hostGuard;
    private final Thief hostThief;

    private final Guard opponentGuard;
    private final Thief opponentThief;

    private Status status;
    private int remainingActions;

    public Game() {
        this.hostGuard = new Guard(Player.HOST);
        this.hostThief = new Thief(Player.HOST);
        this.opponentGuard = new Guard(Player.OPPONENT);
        this.opponentThief = new Thief(Player.OPPONENT);

        this.status = Status.HOST_TURN;
        this.remainingActions = ACTIONS_PER_TURN;
    }

    public Status getStatus() {
        return status;
    }

    public int getRemainingActions() {
        return remainingActions;
    }

    public Guard getHostGuard() {
        return hostGuard;
    }

    public Thief getHostThief() {
        return hostThief;
    }

    public Guard getOpponentGuard() {
        return opponentGuard;
    }

    public Thief getOpponentThief() {
        return opponentThief;
    }

    public void performAction(final Action action) throws IllegalActionException {
        // This is general validation, regardless of the specifics of Action
        this.generalValidation(action);

        // Per-action validation should occur here before making any changes
        switch (action.getActionType()) {
            case END_TURN: {
                this.endTurn();
                return;
            }
            case MOVE_GUARD: {
                final Guard myGuard;
                final Thief myThief;
                final Guard theirGuard;

                if (action.getPlayer() == Player.HOST) {
                    myGuard = this.hostGuard;
                    myThief = this.hostThief;
                    theirGuard = this.opponentGuard;
                } else {
                    myGuard = this.opponentGuard;
                    myThief = this.opponentThief;
                    theirGuard = this.hostGuard;
                }

                myGuard.validateMove(action.getX(), action.getY(), myThief, theirGuard);
                myGuard.executeMove(action.getX(), action.getY());
                break;
            }
            case MOVE_THIEF: {
                final Thief myThief;
                final Guard myGuard;

                if (action.getPlayer() == Player.HOST) {
                    myThief = this.hostThief;
                    myGuard = this.hostGuard;
                } else {
                    myThief = this.opponentThief;
                    myGuard = this.opponentGuard;
                }

                myThief.validateMove(action.getX(), action.getY(), myGuard);
                myThief.executeMove(action.getX(), action.getY());
                break;
            }
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

    private void endTurn() {
        if (Status.HOST_TURN == this.status) {
            this.status = Status.OPPONENT_TURN;
        } else if (Status.OPPONENT_TURN == this.status) {
            this.status = Status.HOST_TURN;
        }

        this.remainingActions = ACTIONS_PER_TURN;
    }

}
