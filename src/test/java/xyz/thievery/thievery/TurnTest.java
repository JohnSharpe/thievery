package xyz.thievery.thievery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;

class TurnTest {

    @Test
    void testOpponentCannotPerformActionWhenItIsNotTheirTurn() {
        final Game game = new Game();

        try {
            game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, 2, 1));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NOT_YOUR_TURN, e.getReason());
        }
    }

    @Test
    void testHostCannotPerformActionWhenItIsNotTheirTurn() throws IllegalActionException {
        final Game game = new Game();
        // Make it opponent's turn
        game.performAction(new Action(Player.HOST, ActionType.END_TURN));

        try {
            game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 1, 1));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NOT_YOUR_TURN, e.getReason());
        }
    }

    @Test
    void testATurnIsAutomaticallyEndedAfterThreeValidActions() throws IllegalActionException {
        final Game game = new Game();

        Assertions.assertEquals(Status.HOST_TURN, game.getStatus());
        Assertions.assertEquals(3, game.getRemainingActions());
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 1));

        Assertions.assertEquals(Status.HOST_TURN, game.getStatus());
        Assertions.assertEquals(2, game.getRemainingActions());
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 2));

        Assertions.assertEquals(Status.HOST_TURN, game.getStatus());
        Assertions.assertEquals(1, game.getRemainingActions());
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 3));

        Assertions.assertEquals(Status.OPPONENT_TURN, game.getStatus());
        Assertions.assertEquals(3, game.getRemainingActions());

    }

//    @Test
//    void testActionsCanNotBePerformedOnEndedGames() {
//        // TODO Test drive your way to a game that can end first
//    }

}
