package xyz.thievery.thievery;

import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;

public class Game {

    private final Player host;
    private final Player opponent;

    private Turn turn;

    public Game(final Player host, final Player opponent) {
        if (host == null || opponent == null) {
            throw new IllegalArgumentException("Neither host nor opponent may be null.");
        }

        if (host.getIdentifier().equals(opponent.getIdentifier())) {
            throw new IllegalArgumentException("Host and opponent may not have the same identifier.");
        }

        this.host = host;
        this.opponent = opponent;
        this.turn = Turn.HOST;
    }

    public Player getHost() {
        return host;
    }

    public Player getOpponent() {
        return opponent;
    }

    public Turn getTurn() {
        return turn;
    }

    public void performAction(final Action action) throws IllegalActionException {
//        if (Turn.END == this.turn) {
//            throw new IllegalActionException("An action cannot be performmed on an ended game.");
//        }

        if (Turn.HOST == this.turn && !action.getPlayerIdentifier().equals(this.host.getIdentifier())) {
            throw new IllegalActionException(IllegalActionReason.NOT_YOUR_TURN, "It is not the host's turn.");
        }
        if (Turn.OPPONENT == this.turn && !action.getPlayerIdentifier().equals(this.opponent.getIdentifier())) {
            throw new IllegalActionException(IllegalActionReason.NOT_YOUR_TURN, "It is not the opponent's turn.");
        }

        switch (action.getActionType()) {
            case END_TURN:
                this.endTurn();
                break;
//            case MOVE_THIEF:
//                break;
//            case MOVE_GUARD:
//                break;
//            case REVEAL:
//                break;
//
//            default:
//                throw new IllegalActionException(
//                        IllegalActionReason.UNRECOGNISED_ACTION_TYPE, "Unrecognised action type.");
        }
    }

    private void endTurn() {
        if (Turn.HOST == this.turn) {
            this.turn = Turn.OPPONENT;
        } else if (Turn.OPPONENT == this.turn) {
            this.turn = Turn.HOST;
        }
    }

}
