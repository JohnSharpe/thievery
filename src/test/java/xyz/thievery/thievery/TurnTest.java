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
            game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD));
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
            game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NOT_YOUR_TURN, e.getReason());
        }
    }

    @Test
    void testATurnIsAutomaticallyEndedAfterThreeValidActions() throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD));

        try {
            game.performAction(new Action(Player.HOST, ActionType.END_TURN));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NOT_YOUR_TURN, e.getReason());
        }
    }

//    @Test
//    void testActionsCanNotBePerformedOnEndedGames() {
//        // TODO Test drive your way to a game that can end first
//    }

}
