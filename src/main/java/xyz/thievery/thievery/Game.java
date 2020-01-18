package xyz.thievery.thievery;

import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;
import xyz.thievery.thievery.units.Guard;

public class Game {

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
        if (action.getX() < 0 || action.getX() > 4 || action.getY() < 0 || action.getY() > 6) {
            throw new IllegalActionException(IllegalActionReason.NO_SUCH_POSITION, "No unit can move off-map");
        }

        // TODO This looks like it'd be relevant to thieves too
        // TODO Consider making 0 and 6 (and other magic numbers) fields
        final int xDifference;
        if (myGuard.getY() == 0 || myGuard.getY() == 6) {
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

        if (action.getY() == 0 || action.getY() == 6) {
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
