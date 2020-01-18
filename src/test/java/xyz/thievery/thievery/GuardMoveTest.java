package xyz.thievery.thievery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;

import java.util.stream.Stream;

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
            Assertions.assertEquals(IllegalActionReason.ILLEGAL_MOVE, e.getReason());
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
            Assertions.assertEquals(IllegalActionReason.ILLEGAL_MOVE, e.getReason());
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
            Assertions.assertEquals(IllegalActionReason.ILLEGAL_MOVE, e.getReason());
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
            Assertions.assertEquals(IllegalActionReason.ILLEGAL_MOVE, e.getReason());
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
        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, destinationX, 5));

        Assertions.assertEquals(destinationX, game.getOpponentGuard().getX());
        Assertions.assertEquals(5, game.getOpponentGuard().getY());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 5})
    void testHostGuardCanNotMoveHorizontallyOffMapFromHome(final int destinationX) {
        final Game game = new Game();

        try {
            game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, destinationX, 0));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NO_SUCH_POSITION, e.getReason());
        }
    }

    @Test
    void testHomeGuardCanNotMoveVerticallyOffMapFromHome() {
        final Game game = new Game();

        try {
            game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 0, -1));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NO_SUCH_POSITION, e.getReason());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 5})
    void testOpponentGuardCanNotMoveHorizontallyOffMapFromHome(final int destinationX) throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.END_TURN));

        try {
            game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, destinationX, 0));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NO_SUCH_POSITION, e.getReason());
        }
    }

    @Test
    void testOpponentGuardCanNotMoveVerticallyOffMapFromHome() throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.END_TURN));

        try {
            game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, 0, 7));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NO_SUCH_POSITION, e.getReason());
        }
    }

    @Test
    void testGuardCanMoveToAdjacentPoint() throws IllegalActionException {
        final Game game = new Game();
        Assertions.assertDoesNotThrow(() -> game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 0, 1)));
        Assertions.assertEquals(0, game.getHostGuard().getX());
        Assertions.assertEquals(1, game.getHostGuard().getY());
        Assertions.assertDoesNotThrow(() -> game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 1, 1)));
        Assertions.assertEquals(1, game.getHostGuard().getX());
        Assertions.assertEquals(1, game.getHostGuard().getY());
        Assertions.assertDoesNotThrow(() -> game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 1, 2)));
        Assertions.assertEquals(1, game.getHostGuard().getX());
        Assertions.assertEquals(2, game.getHostGuard().getY());

        game.performAction(new Action(Player.OPPONENT, ActionType.END_TURN));

        Assertions.assertDoesNotThrow(() -> game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 0, 2)));
        Assertions.assertEquals(0, game.getHostGuard().getX());
        Assertions.assertEquals(2, game.getHostGuard().getY());
        Assertions.assertDoesNotThrow(() -> game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 0, 1)));
        Assertions.assertEquals(0, game.getHostGuard().getX());
        Assertions.assertEquals(1, game.getHostGuard().getY());
    }

    @ParameterizedTest
    @MethodSource("offMapCoordinatesProvider")
    void testGuardCanNotMoveOffMap(final int firstX, final int secondX) throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, firstX, 1));
        try {
            game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, secondX, 1));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NO_SUCH_POSITION, e.getReason());
        }
    }

    @Test
    void testGuardCanNotMoveMoreThanOneSpaceFromHome() {
        final Game game = new Game();

        try {
            game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 0, 2));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NOT_WITHIN_REACH, e.getReason());
        }
    }

    @ParameterizedTest
    @MethodSource("tooFarHorizontalMovesProvider")
    void testGuardCanNotMoveMoreThanOneSpaceHorizontally(final int firstX, final int secondX) throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, firstX, 1));

        try {
            game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, secondX, 1));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NOT_WITHIN_REACH, e.getReason());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5})
    void testGuardCanNotMoveMoreThanOneSpaceVertically(final int destinationY) throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 1));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 2));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 3));
        game.performAction(new Action(Player.OPPONENT, ActionType.END_TURN));

        try {
            game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, destinationY));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NOT_WITHIN_REACH, e.getReason());
        }
    }

    @ParameterizedTest
    @MethodSource("diagonalMoves")
    void testGuardCanNotMoveDiagonally(final int destinationX, final int destinationY) throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 1));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 2));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 3));
        game.performAction(new Action(Player.OPPONENT, ActionType.END_TURN));

        try {
            game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, destinationX, destinationY));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NOT_WITHIN_REACH, e.getReason());
        }
    }

    @Test
    void testGuardCanNotZeroMove() throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 1));

        try {
            game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 1));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.ILLEGAL_MOVE, e.getReason());
        }
    }

    // TODO Later
    // Guard can not move into space occupied by opponent guard etc

    private static Stream<Arguments> offMapCoordinatesProvider() {
        return Stream.of(
                Arguments.of(0, -1),
                Arguments.of(4, 5)
        );
    }

    private static Stream<Arguments> tooFarHorizontalMovesProvider() {
        return Stream.of(
                Arguments.of(0, 2),
                Arguments.of(0, 3),
                Arguments.of(0, 4),
                Arguments.of(1, 3),
                Arguments.of(1, 4),
                Arguments.of(2, 0),
                Arguments.of(2, 4),
                Arguments.of(3, 1),
                Arguments.of(3, 0),
                Arguments.of(4, 2),
                Arguments.of(4, 1),
                Arguments.of(4, 0)
        );
    }

    private static Stream<Arguments> diagonalMoves() {
        return Stream.of(
                Arguments.of(3, 4),
                Arguments.of(3, 2),
                Arguments.of(1, 2),
                Arguments.of(1, 4)
        );
    }

}
