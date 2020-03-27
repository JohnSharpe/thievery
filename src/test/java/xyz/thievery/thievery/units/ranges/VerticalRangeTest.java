package xyz.thievery.thievery.units.ranges;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import xyz.thievery.thievery.Action;
import xyz.thievery.thievery.ActionType;
import xyz.thievery.thievery.Game;
import xyz.thievery.thievery.exceptions.IllegalActionException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class VerticalRangeTest {

    @ParameterizedTest
    @MethodSource("catches")
    void testVerticalRangeCatches(final int guardX, final int guardY, final int thiefX, final int thiefY) {
        // Given
        final VerticalRange verticalRange = new VerticalRange();

        // When
        final boolean caught = verticalRange.hasCaught(guardX, guardY, thiefX, thiefY, false);

        // Then
        Assertions.assertTrue(caught);
    }

    @ParameterizedTest
    @MethodSource("notCatches")
    void testVerticalRangeNotCatches(final int guardX, final int guardY, final int thiefX, final int thiefY) {
        // Given
        final VerticalRange verticalRange = new VerticalRange();

        // When
        final boolean caught = verticalRange.hasCaught(guardX, guardY, thiefX, thiefY, false);

        // Then
        Assertions.assertFalse(caught);
    }

    // Where the Host Guard catches the Opponent thief
    @ParameterizedTest
    @MethodSource("hostVerticalCatchesProvider")
    void TestHostVerticalCatches(final List<Action> preparatoryActions, final Action catchAction) throws IllegalActionException {
        // Given
        final Game game = new Game(RangeType.VERTICAL, RangeType.VERTICAL);
        for (final Action action : preparatoryActions) {
            game.performAction(action);
        }

        // Host Thief should NOT be at home at this point
        Assertions.assertNotEquals(Game.OPPONENT_HOME_ROW, game.getOpponentThief().getY());

        // When
        game.performAction(catchAction);

        // Then
        Assertions.assertEquals(Game.OPPONENT_HOME_ROW, game.getOpponentThief().getY());
        Assertions.assertFalse(game.getOpponentThief().isCarrying());
    }

    // Where the Host Guard does not catch the Opponent thief
    @ParameterizedTest
    @MethodSource("hostVerticalNotCatchesProvider")
    void TestHostVerticalNotCatches(final List<Action> preparatoryActions, final Action catchAction) throws IllegalActionException {
        // Given
        final Game game = new Game(RangeType.VERTICAL, RangeType.VERTICAL);
        for (final Action action : preparatoryActions) {
            game.performAction(action);
        }

        // Host Thief should NOT be at home at this point
        Assertions.assertNotEquals(Game.OPPONENT_HOME_ROW, game.getOpponentThief().getY());

        // When
        game.performAction(catchAction);

        // Then
        Assertions.assertNotEquals(Game.OPPONENT_HOME_ROW, game.getOpponentThief().getY());
    }

    private static Stream<Arguments> catches() {
        return Stream.of(
                Arguments.of(1, 2, 1, 3),
                Arguments.of(1, 2, 1, 1),
                Arguments.of(4, 5, 4, 4),
                Arguments.of(0, 1, 0, 2),
                Arguments.of(2, 3, 2, 2),
                Arguments.of(2, 3, 2, 4)
        );
    }

    private static Stream<Arguments> notCatches() {
        return Stream.of(
                Arguments.of(1, 2, 4, 3),
                Arguments.of(1, 2, 0, 1),
                Arguments.of(4, 5, 4, 3),
                Arguments.of(0, 1, 0, 3),
                Arguments.of(2, 3, 2, 0),
                Arguments.of(2, 3, 2, 1),
                Arguments.of(2, 3, 2, 5)
        );
    }

    private static Stream<Arguments> hostVerticalCatchesProvider() throws IllegalActionException {
        return Stream.of(
                Arguments.of(
                        Arrays.asList(
                               new Action(ActionType.MOVE_GUARD, 2, 1),
                                new Action(ActionType.MOVE_GUARD, 2, 2),
                                new Action(ActionType.MOVE_GUARD, 2, 3),
                                new Action(ActionType.MOVE_THIEF, 3, 5),
                                new Action(ActionType.MOVE_THIEF, 3, 4)
                        ),
                        new Action(ActionType.MOVE_THIEF, 2, 4)
                ),
                Arguments.of(
                        Arrays.asList(
                                new Action(ActionType.MOVE_GUARD, 2, 1),
                                new Action(ActionType.MOVE_GUARD, 2, 2),
                                new Action(ActionType.MOVE_GUARD, 2, 3),
                                new Action(ActionType.MOVE_THIEF, 1, 5),
                                new Action(ActionType.MOVE_THIEF, 1, 4),
                                new Action(ActionType.MOVE_THIEF, 1, 3),
                                new Action(ActionType.END_TURN),
                                new Action(ActionType.MOVE_THIEF, 1, 2)
                        ),
                        new Action(ActionType.MOVE_THIEF, 2, 2)
                )
        );
    }

    private static Stream<Arguments> hostVerticalNotCatchesProvider() throws IllegalActionException {
        return Stream.of(
                Arguments.of(
                        Arrays.asList(
                                new Action(ActionType.MOVE_GUARD, 2, 1),
                                new Action(ActionType.END_TURN),
                                new Action(ActionType.MOVE_THIEF, 3, 5),
                                new Action(ActionType.MOVE_THIEF, 3, 4),
                                new Action(ActionType.MOVE_THIEF, 3, 3),
                                new Action(ActionType.END_TURN),
                                new Action(ActionType.MOVE_THIEF, 3, 2),
                                new Action(ActionType.MOVE_THIEF, 3, 1),
                                new Action(ActionType.MOVE_THIEF, 3, 0),
                                new Action(ActionType.END_TURN)
                        ),
                        new Action(ActionType.MOVE_THIEF, 1, 1)
                )
        );
    }

}
