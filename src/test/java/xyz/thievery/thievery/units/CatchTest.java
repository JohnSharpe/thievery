package xyz.thievery.thievery.units;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.thievery.thievery.Action;
import xyz.thievery.thievery.ActionType;
import xyz.thievery.thievery.Game;
import xyz.thievery.thievery.Player;
import xyz.thievery.thievery.exceptions.IllegalActionException;

class CatchTest {

    @Test
    void testHostGuardCanNotCatchFromHomeRow() throws IllegalActionException {
        final Game game = new Game();

        game.performAction(new Action(Player.HOST, ActionType.END_TURN));

        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_THIEF, 0, 5));
        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_THIEF, 0, 4));
        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_THIEF, 0, 3));

        game.performAction(new Action(Player.HOST, ActionType.END_TURN));

        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_THIEF, 0, 2));
        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_THIEF, 0, 1));
        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_THIEF, 0, 0));

        Assertions.assertEquals(Game.HOST_HOME_ROW, game.getOpponentThief().getY());
    }

    @Test
    void testOpponentGuardCanNotCatchFromHomeRow() throws IllegalActionException {
        final Game game = new Game();

        game.performAction(new Action(Player.HOST, ActionType.MOVE_THIEF, 0, 1));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_THIEF, 0, 2));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_THIEF, 0, 3));

        game.performAction(new Action(Player.OPPONENT, ActionType.END_TURN));

        game.performAction(new Action(Player.HOST, ActionType.MOVE_THIEF, 0, 4));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_THIEF, 0, 5));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_THIEF, 0, 6));

        Assertions.assertEquals(Game.OPPONENT_HOME_ROW, game.getHostThief().getY());
    }

    @Test
    void testHostThiefIsCaughtWhenMovingOntoOpponentGuard() throws IllegalActionException {
        final Game game = new Game();

        game.performAction(new Action(Player.HOST, ActionType.MOVE_THIEF, 2, 1));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_THIEF, 2, 2));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_THIEF, 2, 3));

        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, 2, 5));
        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, 2, 4));
        game.performAction(new Action(Player.OPPONENT, ActionType.END_TURN));

        game.performAction(new Action(Player.HOST, ActionType.MOVE_THIEF, 2, 4));

        Assertions.assertEquals(Game.HOST_HOME_ROW, game.getHostThief().getY());
    }

    @Test
    void testOpponentThiefIsCaughtWhenMovingOntoHostGuard() throws IllegalActionException {
        final Game game = new Game();

        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 1));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 2));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 3));

        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_THIEF, 2, 5));
        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_THIEF, 2, 4));
        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_THIEF, 2, 3));

        Assertions.assertEquals(Game.OPPONENT_HOME_ROW, game.getOpponentThief().getY());
    }

    @Test
    void testHostThiefIsCaughtWhenMovedOntoByOpponentGuard() throws IllegalActionException {
        final Game game = new Game();

        game.performAction(new Action(Player.HOST, ActionType.MOVE_THIEF, 2, 1));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_THIEF, 2, 2));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_THIEF, 2, 3));

        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, 2, 5));
        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, 2, 4));
        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, 2, 3));

        Assertions.assertEquals(Game.HOST_HOME_ROW, game.getHostThief().getY());
    }

    @Test
    void testOpponentThiefIsCaughtWhenMovedOntoByHostGuard() throws IllegalActionException {
        final Game game = new Game();

        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 1));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 2));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 3));

        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_THIEF, 2, 5));
        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_THIEF, 2, 4));
        game.performAction(new Action(Player.OPPONENT, ActionType.END_TURN));

        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 4));

        Assertions.assertEquals(Game.OPPONENT_HOME_ROW, game.getOpponentThief().getY());
    }

    // TODO Test catches remove 'carrying' status
}
