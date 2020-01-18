package xyz.thievery.thievery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;

class GuardMoveTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void testHostGuardCanMoveOutOfHome(final int destinationX) {
        final Game game = new Game();

        Assertions.assertDoesNotThrow(() -> game.performAction(
                new Action(Player.HOST, ActionType.MOVE_GUARD, destinationX, 1)
        ));

        Assertions.assertEquals(destinationX, game.getHostGuard().getX());
        Assertions.assertEquals(1, game.getHostGuard().getY());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void testOpponentGuardCanMoveOutOfHome(final int destinationX) throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.END_TURN));

        Assertions.assertDoesNotThrow(() -> game.performAction(
                new Action(Player.OPPONENT, ActionType.MOVE_GUARD, destinationX, 5)
        ));

        Assertions.assertEquals(destinationX, game.getOpponentGuard().getX());
        Assertions.assertEquals(5, game.getOpponentGuard().getY());
    }

    @Test
    void testHostGuardCanNotMoveIntoOwnHome() throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 3, 1));

        try {
            game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 3, 0));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NOT_WITHIN_REACH, e.getReason());
        }
    }

    @Test
    void testOpponentGuardCanNotMoveIntoOwnHome() throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.END_TURN));
        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, 4, 5));

        try {
            game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, 4, 6));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NOT_WITHIN_REACH, e.getReason());
        }
    }

    @Test
    void testHostGuardCanNotMoveIntoOtherHome() throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 3, 1));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 3, 2));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 3, 3));
        game.performAction(new Action(Player.OPPONENT, ActionType.END_TURN));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 3, 4));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 3, 5));

        try {
            game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 3, 6));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NOT_WITHIN_REACH, e.getReason());
        }
    }

    @Test
    void testOpponentGuardCanNotMoveIntoOtherHome() throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.END_TURN));
        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, 0, 5));
        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, 0, 4));
        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, 0, 3));
        game.performAction(new Action(Player.HOST, ActionType.END_TURN));
        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, 0, 2));
        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, 0, 1));

        try {
            game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, 0, 0));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NOT_WITHIN_REACH, e.getReason());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void testHostGuardCanMoveFromHomeToAnyColumn(final int destinationX) throws IllegalActionException {
        final Game game = new Game();

        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, destinationX, 1));

        Assertions.assertEquals(destinationX, game.getHostGuard().getX());
        Assertions.assertEquals(1, game.getHostGuard().getY());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void testOpponentGuardCanMoveFromHomeToAnyColumn(final int destinationX) throws IllegalActionException {
        final Game game = new Game();

        game.performAction(new Action(Player.HOST, ActionType.END_TURN));
        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, destinationX, 1));

        Assertions.assertEquals(destinationX, game.getOpponentGuard().getX());
        Assertions.assertEquals(1, game.getOpponentGuard().getY());
    }

//    @Test
//    void testGuardCanMoveToAdjacentPoint() {
//
//    }
//
//    @Test
//    void testGuardCanNotMoveOffMap() {
//
//    }
//
//    @Test
//    void testGuardCanNotMoveMoreThanOneSpace() {
//
//    }

    // TODO Later
    // Guard can not move into space occupied by opponent guard etc

}
