package xyz.thievery.thievery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;

class GameTest {

    @Test
    void testHostGoesFirstInNewGame() {
        final Game game = new Game();
        Assertions.assertEquals(Status.HOST_TURN, game.getStatus());
    }

    @Test
    void testUnitCreation() {
        final Game game = new Game();
        Assertions.assertNotNull(game.getHostGuard());
        Assertions.assertNotNull(game.getHostThief());
        Assertions.assertNotNull(game.getOpponentGuard());
        Assertions.assertNotNull(game.getOpponentThief());
    }

    @Test
    void testStartingSteals() {
        final Game game = new Game();
        Assertions.assertEquals(0, game.getHostSteals());
        Assertions.assertEquals(0, game.getOpponentSteals());
    }

    @Test
    void testActionCanNotBePerformedOnEndedGame() throws IllegalActionException {
        final Game game = new Game();

        TestUtils.enactHostSteal(game);
        game.performAction(new Action(ActionType.END_TURN));
        TestUtils.enactHostSteal(game);
        game.performAction(new Action(ActionType.END_TURN));
        TestUtils.enactHostSteal(game);

        try {
            game.performAction(new Action(ActionType.END_TURN));
            Assertions.fail();
        } catch (final IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.GAME_ENDED, e.getReason());
        }
    }

}
