package xyz.thievery.thievery;

import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.RangeNotAvailableException;
import xyz.thievery.thievery.units.ranges.RangeType;

public class TestUtils {

    public static Game createNoNonsenseGame() {
        try {
            return new Game(RangeType.VERTICAL, RangeType.VERTICAL);
        } catch (RangeNotAvailableException e) {
            throw new IllegalArgumentException();
        }
    }

    public static void enactHostSteal(final Game game) throws IllegalActionException {
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 1));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 2));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 3));

        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 4));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 5));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 6));

        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 5));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 4));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 3));

        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 2));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 1));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 0));
    }

    public static void enactOpponentSteal(final Game game) throws IllegalActionException {
        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 5));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 4));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 3));

        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 2));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 1));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 0));

        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 1));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 2));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 3));

        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 4));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 5));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 6));
    }

}
