package xyz.thievery.thievery.units;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.thievery.thievery.Action;
import xyz.thievery.thievery.ActionType;
import xyz.thievery.thievery.Game;
import xyz.thievery.thievery.TestUtils;
import xyz.thievery.thievery.exceptions.IllegalActionException;

class CatchTest {

    @Test
    void testHostGuardCanNotCatchFromHomeRow() throws IllegalActionException {
        final Game game = TestUtils.createNoNonsenseGame();

        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 5));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 4));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 3));

        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 2));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 1));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 0));

        Assertions.assertEquals(Game.HOST_HOME_ROW, game.getOpponentThief().getY());
    }

    @Test
    void testOpponentGuardCanNotCatchFromHomeRow() throws IllegalActionException {
        final Game game = TestUtils.createNoNonsenseGame();

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 1));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 2));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 3));

        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 4));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 5));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 6));

        Assertions.assertEquals(Game.OPPONENT_HOME_ROW, game.getHostThief().getY());
    }

    @Test
    void testHostThiefIsCaughtWhenMovingOntoOpponentGuard() throws IllegalActionException {
        final Game game = TestUtils.createNoNonsenseGame();

        game.performAction(new Action(ActionType.MOVE_THIEF, 2, 1));
        game.performAction(new Action(ActionType.MOVE_THIEF, 2, 2));
        game.performAction(new Action(ActionType.MOVE_THIEF, 2, 3));

        game.performAction(new Action(ActionType.MOVE_GUARD, 2, 5));
        game.performAction(new Action(ActionType.MOVE_GUARD, 2, 4));
        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_THIEF, 2, 4));

        Assertions.assertEquals(Game.HOST_HOME_ROW, game.getHostThief().getY());
        Assertions.assertFalse(game.getHostThief().isCarrying());
    }

    @Test
    void testOpponentThiefIsCaughtWhenMovingOntoHostGuard() throws IllegalActionException {
        final Game game = TestUtils.createNoNonsenseGame();

        game.performAction(new Action(ActionType.MOVE_GUARD, 2, 1));
        game.performAction(new Action(ActionType.MOVE_GUARD, 2, 2));
        game.performAction(new Action(ActionType.MOVE_GUARD, 2, 3));

        game.performAction(new Action(ActionType.MOVE_THIEF, 2, 5));
        game.performAction(new Action(ActionType.MOVE_THIEF, 2, 4));
        game.performAction(new Action(ActionType.MOVE_THIEF, 2, 3));

        Assertions.assertEquals(Game.OPPONENT_HOME_ROW, game.getOpponentThief().getY());
        Assertions.assertFalse(game.getOpponentThief().isCarrying());
    }

    @Test
    void testHostThiefIsCaughtWhenMovedOntoByOpponentGuard() throws IllegalActionException {
        final Game game = TestUtils.createNoNonsenseGame();

        game.performAction(new Action(ActionType.MOVE_THIEF, 2, 1));
        game.performAction(new Action(ActionType.MOVE_THIEF, 2, 2));
        game.performAction(new Action(ActionType.MOVE_THIEF, 2, 3));

        game.performAction(new Action(ActionType.MOVE_GUARD, 2, 5));
        game.performAction(new Action(ActionType.MOVE_GUARD, 2, 4));
        game.performAction(new Action(ActionType.MOVE_GUARD, 2, 3));

        Assertions.assertEquals(Game.HOST_HOME_ROW, game.getHostThief().getY());
        Assertions.assertFalse(game.getHostThief().isCarrying());
    }

    @Test
    void testOpponentThiefIsCaughtWhenMovedOntoByHostGuard() throws IllegalActionException {
        final Game game = TestUtils.createNoNonsenseGame();

        game.performAction(new Action(ActionType.MOVE_GUARD, 2, 1));
        game.performAction(new Action(ActionType.MOVE_GUARD, 2, 2));
        game.performAction(new Action(ActionType.MOVE_GUARD, 2, 3));

        game.performAction(new Action(ActionType.MOVE_THIEF, 2, 5));
        game.performAction(new Action(ActionType.MOVE_THIEF, 2, 4));
        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_GUARD, 2, 4));

        Assertions.assertEquals(Game.OPPONENT_HOME_ROW, game.getOpponentThief().getY());
        Assertions.assertFalse(game.getOpponentThief().isCarrying());
    }

    @Test
    void testCatchingRemovesCarryingStatusOnHost() throws IllegalActionException {
        final Game game = TestUtils.createNoNonsenseGame();

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 1));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 2));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 3));

        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 4));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 5));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 6));

        Assertions.assertTrue(game.getHostThief().isCarrying());

        game.performAction(new Action(ActionType.MOVE_GUARD, 0, 5));
        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 5));

        Assertions.assertEquals(Game.HOST_HOME_ROW, game.getHostThief().getY());
        Assertions.assertFalse(game.getHostThief().isCarrying());
    }

    @Test
    void testCatchingRemovesCarryingStatusOnOpponent() throws IllegalActionException {
        final Game game = TestUtils.createNoNonsenseGame();

        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 5));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 4));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 3));

        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 2));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 1));
        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 0));

        Assertions.assertTrue(game.getOpponentThief().isCarrying());

        game.performAction(new Action(ActionType.MOVE_GUARD, 0, 1));
        game.performAction(new Action(ActionType.END_TURN));

        game.performAction(new Action(ActionType.MOVE_THIEF, 0, 1));

        Assertions.assertEquals(Game.OPPONENT_HOME_ROW, game.getOpponentThief().getY());
        Assertions.assertFalse(game.getOpponentThief().isCarrying());
    }

}
