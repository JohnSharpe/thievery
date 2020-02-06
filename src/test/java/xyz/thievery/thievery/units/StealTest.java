package xyz.thievery.thievery.units;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.thievery.thievery.Action;
import xyz.thievery.thievery.ActionType;
import xyz.thievery.thievery.Game;
import xyz.thievery.thievery.exceptions.IllegalActionException;

class StealTest {

    @Test
    void testThievesDoNotStartCarrying() {
        final Game game = new Game();

        Assertions.assertFalse(game.getHostThief().isCarrying());
        Assertions.assertFalse(game.getOpponentThief().isCarrying());
    }

    @Test
    void testAHostThiefIsCarryingWhenHeEntersOpponentHome() throws IllegalActionException {
        final Game game = new Game();

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 1));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 2));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 3));

        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 4));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 5));

        Assertions.assertFalse(game.getHostThief().isCarrying());
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 6));
        Assertions.assertTrue(game.getHostThief().isCarrying());
    }

    @Test
    void testAnOpponentThiefIsCarryingWhenHeLeavesHostHome() throws IllegalActionException {
        final Game game = new Game();

        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 5));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 4));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 3));

        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 2));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 1));

        Assertions.assertFalse(game.getOpponentThief().isCarrying());
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 0));
        Assertions.assertTrue(game.getOpponentThief().isCarrying());

    }

}
