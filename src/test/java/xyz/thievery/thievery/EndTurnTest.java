package xyz.thievery.thievery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.thievery.thievery.exceptions.IllegalActionException;

class EndTurnTest {

    @Test
    void testAHostMayImmemdiatelyEndTheirTurn() throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.END_TURN));
        Assertions.assertEquals(Status.OPPONENT_TURN, game.getStatus());
    }

    @Test
    void testAnOpponentMayImmemdiatelyEndTheirTurn() throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.END_TURN));
        game.performAction(new Action(Player.OPPONENT, ActionType.END_TURN));
        Assertions.assertEquals(Status.HOST_TURN, game.getStatus());
    }

    @Test
    void testAPlayerMayEndTheirTurnAfterOneAction() throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD));
        game.performAction(new Action(Player.HOST, ActionType.END_TURN));
        Assertions.assertEquals(Status.OPPONENT_TURN, game.getStatus());
    }

    @Test
    void testAPlayerMayEndTheirTurnAfterTwoActions() throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD));
        game.performAction(new Action(Player.HOST, ActionType.END_TURN));
        Assertions.assertEquals(Status.OPPONENT_TURN, game.getStatus());
    }

}
