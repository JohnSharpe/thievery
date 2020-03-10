package xyz.thievery.thievery.units;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.thievery.thievery.*;
import xyz.thievery.thievery.exceptions.IllegalActionException;

class StealTest {

    @Test
    void testThievesDoNotStartCarrying() {
        final Game game = TestUtils.createNoNonsenseGame();

        Assertions.assertFalse(game.getHostThief().isCarrying());
        Assertions.assertFalse(game.getOpponentThief().isCarrying());
    }

    @Test
    void testAHostThiefIsCarryingWhenEnteringOpponentHome() throws IllegalActionException {
        final Game game = TestUtils.createNoNonsenseGame();

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
    void testAnOpponentThiefIsCarryingWhenEnteringHostHome() throws IllegalActionException {
        final Game game = TestUtils.createNoNonsenseGame();

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

    @Test
    void testACarryingHostThiefStealsWhenTheyGetHome() throws IllegalActionException {
        final Game game = TestUtils.createNoNonsenseGame();
        TestUtils.enactHostSteal(game);
        Assertions.assertFalse(game.getHostThief().isCarrying());
        Assertions.assertEquals(1, game.getHostSteals());
    }

    @Test
    void testACarryingOpponentThiefStealsWhenTheyGetHome() throws IllegalActionException {
        final Game game = TestUtils.createNoNonsenseGame();
        TestUtils.enactOpponentSteal(game);
        Assertions.assertFalse(game.getOpponentThief().isCarrying());
        Assertions.assertEquals(1, game.getOpponentSteals());
    }

    @Test
    void testGameEndsAtThreeHostSteals() throws IllegalActionException {
        final Game game = TestUtils.createNoNonsenseGame();
        TestUtils.enactHostSteal(game);
        Assertions.assertEquals(1, game.getHostSteals());
        game.performAction(new Action(ActionType.END_TURN));
        TestUtils.enactHostSteal(game);
        Assertions.assertEquals(2, game.getHostSteals());
        game.performAction(new Action(ActionType.END_TURN));
        TestUtils.enactHostSteal(game);
        Assertions.assertEquals(3, game.getHostSteals());
        Assertions.assertEquals(Status.END, game.getStatus());
    }

    @Test
    void testGameEndsAtThreeOpponentSteals() throws IllegalActionException {
        final Game game = TestUtils.createNoNonsenseGame();
        TestUtils.enactOpponentSteal(game);
        Assertions.assertEquals(1, game.getOpponentSteals());
        TestUtils.enactOpponentSteal(game);
        Assertions.assertEquals(2, game.getOpponentSteals());
        TestUtils.enactOpponentSteal(game);
        Assertions.assertEquals(3, game.getOpponentSteals());
        Assertions.assertEquals(Status.END, game.getStatus());
    }

}
