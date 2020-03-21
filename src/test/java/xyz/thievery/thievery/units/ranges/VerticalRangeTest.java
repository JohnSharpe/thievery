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

    // TODO Consider just testing the VerticalRange type!
//    @ParameterizedTest
//    @MethodSource("")
//    void testVerticalRange() {
//        final VerticalRange verticalRange = new VerticalRange();
//        verticalRange.
//    }

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
        Assertions.assertNotEquals(Game.HOST_HOME_ROW, game.getOpponentThief().getY());

        // When
        game.performAction(catchAction);

        // Then
        Assertions.assertEquals(Game.OPPONENT_HOME_ROW, game.getOpponentThief().getY());
        Assertions.assertFalse(game.getOpponentThief().isCarrying());
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

}
