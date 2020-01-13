package xyz.thievery.thievery;

import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;

public class Game {

    private static final int ACTIONS_PER_TURN = 3;

    private Status status;
    private int remainingActions;

    public Game() {
        this.status = Status.HOST_TURN;
        this.remainingActions = ACTIONS_PER_TURN;
    }

    public Status getStatus() {
        return status;
    }

    public void performAction(final Action action) throws IllegalActionException {
//        if (Status.END == this.status) {
//            throw new IllegalActionException("An action cannot be performmed on an ended game.");
//        }

        if (Status.HOST_TURN == this.status && Player.HOST != action.getPlayer()) {
            throw new IllegalActionException(IllegalActionReason.NOT_YOUR_TURN, "It is not the host's turn.");
        }
        if (Status.OPPONENT_TURN == this.status && Player.OPPONENT != action.getPlayer()) {
            throw new IllegalActionException(IllegalActionReason.NOT_YOUR_TURN, "It is not the opponent's turn.");
        }

        // if your guard cannot reveal but you've asked for a reveal, throw

        switch (action.getActionType()) {
            case END_TURN:
                this.endTurn();
                return;
            case MOVE_THIEF:
            case MOVE_GUARD:
            case REVEAL:
                this.remainingActions--;
                break;

//            default:
//                throw new IllegalActionException(
//                        IllegalActionReason.UNRECOGNISED_ACTION_TYPE, "Unrecognised action type.");
        }

        if (this.remainingActions == 0) {
            this.endTurn();
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
