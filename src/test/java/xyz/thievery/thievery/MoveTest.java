package xyz.thievery.thievery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;
import xyz.thievery.thievery.units.Unit;

import java.util.stream.Stream;

abstract class MoveTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void testHostUnitCanMoveOutOfHome(final int destinationX) {
        final Game game = new Game();

        Assertions.assertDoesNotThrow(() -> game.performAction(
                new Action(Player.HOST, this.getActionType(), destinationX, 1)
        ));

        Assertions.assertEquals(destinationX, this.getHostUnit(game).getX());
        Assertions.assertEquals(1, this.getHostUnit(game).getY());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void testOpponentUnitCanMoveOutOfHome(final int destinationX) throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.END_TURN));

        Assertions.assertDoesNotThrow(() -> game.performAction(
                new Action(Player.OPPONENT, this.getActionType(), destinationX, 5)
        ));

        Assertions.assertEquals(destinationX, this.getOpponentUnit(game).getX());
        Assertions.assertEquals(5, this.getOpponentUnit(game).getY());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void testHostUnitCanMoveFromHomeToAnyColumn(final int destinationX) throws IllegalActionException {
        final Game game = new Game();

        game.performAction(new Action(Player.HOST, this.getActionType(), destinationX, 1));

        Assertions.assertEquals(destinationX, this.getHostUnit(game).getX());
        Assertions.assertEquals(1, this.getHostUnit(game).getY());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void testOpponentUnitCanMoveFromHomeToAnyColumn(final int destinationX) throws IllegalActionException {
        final Game game = new Game();

        game.performAction(new Action(Player.HOST, ActionType.END_TURN));
        game.performAction(new Action(Player.OPPONENT, this.getActionType(), destinationX, 5));

        Assertions.assertEquals(destinationX, this.getOpponentUnit(game).getX());
        Assertions.assertEquals(5, this.getOpponentUnit(game).getY());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 5})
    void testHostUnitCanNotMoveHorizontallyOffMapFromHome(final int destinationX) {
        final Game game = new Game();

        try {
            game.performAction(new Action(Player.HOST, this.getActionType(), destinationX, 0));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NO_SUCH_POSITION, e.getReason());
        }
    }

    @Test
    void testHostUnitCanNotMoveVerticallyOffMapFromHome() {
        final Game game = new Game();

        try {
            game.performAction(new Action(Player.HOST, this.getActionType(), 0, -1));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NO_SUCH_POSITION, e.getReason());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 5})
    void testOpponentUnitCanNotMoveHorizontallyOffMapFromHome(final int destinationX) throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.END_TURN));

        try {
            game.performAction(new Action(Player.OPPONENT, this.getActionType(), destinationX, 0));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NO_SUCH_POSITION, e.getReason());
        }
    }

    @Test
    void testOpponentUnitCanNotMoveVerticallyOffMapFromHome() throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.END_TURN));

        try {
            game.performAction(new Action(Player.OPPONENT, this.getActionType(), 0, 7));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NO_SUCH_POSITION, e.getReason());
        }
    }

    @Test
    void testUnitCanMoveToAdjacentPoint() throws IllegalActionException {
        final Game game = new Game();
        Assertions.assertDoesNotThrow(() -> game.performAction(new Action(Player.HOST, this.getActionType(), 0, 1)));
        Assertions.assertEquals(0, this.getHostUnit(game).getX());
        Assertions.assertEquals(1, this.getHostUnit(game).getY());
        Assertions.assertDoesNotThrow(() -> game.performAction(new Action(Player.HOST, this.getActionType(), 1, 1)));
        Assertions.assertEquals(1, this.getHostUnit(game).getX());
        Assertions.assertEquals(1, this.getHostUnit(game).getY());
        Assertions.assertDoesNotThrow(() -> game.performAction(new Action(Player.HOST, this.getActionType(), 1, 2)));
        Assertions.assertEquals(1, this.getHostUnit(game).getX());
        Assertions.assertEquals(2, this.getHostUnit(game).getY());

        game.performAction(new Action(Player.OPPONENT, ActionType.END_TURN));

        Assertions.assertDoesNotThrow(() -> game.performAction(new Action(Player.HOST, this.getActionType(), 0, 2)));
        Assertions.assertEquals(0, this.getHostUnit(game).getX());
        Assertions.assertEquals(2, this.getHostUnit(game).getY());
        Assertions.assertDoesNotThrow(() -> game.performAction(new Action(Player.HOST, this.getActionType(), 0, 1)));
        Assertions.assertEquals(0, this.getHostUnit(game).getX());
        Assertions.assertEquals(1, this.getHostUnit(game).getY());
    }

    @ParameterizedTest
    @MethodSource("offMapCoordinatesProvider")
    void testUnitCanNotMoveOffMap(final int firstX, final int secondX) throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, this.getActionType(), firstX, 1));
        try {
            game.performAction(new Action(Player.HOST, this.getActionType(), secondX, 1));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NO_SUCH_POSITION, e.getReason());
        }
    }

    @Test
    void testUnitCanNotMoveMoreThanOneSpaceFromHome() {
        final Game game = new Game();

        try {
            game.performAction(new Action(Player.HOST, this.getActionType(), 0, 2));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NOT_WITHIN_REACH, e.getReason());
        }
    }

    @ParameterizedTest
    @MethodSource("tooFarHorizontalMovesProvider")
    void testUnitCanNotMoveMoreThanOneSpaceHorizontally(final int firstX, final int secondX) throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, this.getActionType(), firstX, 1));

        try {
            game.performAction(new Action(Player.HOST, this.getActionType(), secondX, 1));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NOT_WITHIN_REACH, e.getReason());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5})
    void testUnitCanNotMoveMoreThanOneSpaceVertically(final int destinationY) throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, this.getActionType(), 2, 1));
        game.performAction(new Action(Player.HOST, this.getActionType(), 2, 2));
        game.performAction(new Action(Player.HOST, this.getActionType(), 2, 3));
        game.performAction(new Action(Player.OPPONENT, ActionType.END_TURN));

        try {
            game.performAction(new Action(Player.HOST, this.getActionType(), 2, destinationY));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NOT_WITHIN_REACH, e.getReason());
        }
    }

    @ParameterizedTest
    @MethodSource("diagonalMoves")
    void testUnitCanNotMoveDiagonally(final int destinationX, final int destinationY) throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, this.getActionType(), 2, 1));
        game.performAction(new Action(Player.HOST, this.getActionType(), 2, 2));
        game.performAction(new Action(Player.HOST, this.getActionType(), 2, 3));
        game.performAction(new Action(Player.OPPONENT, ActionType.END_TURN));

        try {
            game.performAction(new Action(Player.HOST, this.getActionType(), destinationX, destinationY));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.NOT_WITHIN_REACH, e.getReason());
        }
    }

    @Test
    void testHostUnitCanNotZeroMove() throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, this.getActionType(), 2, 1));

        try {
            game.performAction(new Action(Player.HOST, this.getActionType(), 2, 1));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.ILLEGAL_MOVE, e.getReason());
        }
    }

    @Test
    void testOpponentUnitCanNotZeroMove() throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.END_TURN));

        try {
            game.performAction(new Action(Player.OPPONENT, this.getActionType(), 0, 6));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.ILLEGAL_MOVE, e.getReason());
        }
    }

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

    protected abstract ActionType getActionType();

    protected abstract Unit getHostUnit(final Game game);

    protected abstract Unit getOpponentUnit(final Game game);

}
