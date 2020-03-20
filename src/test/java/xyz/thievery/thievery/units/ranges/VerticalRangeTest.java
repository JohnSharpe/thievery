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
    @MethodSource("hostVerticalCatchesProvider")
    void TestHostVerticalCatches(final List<Action> preparatoryActions, final Action catchAction) throws IllegalActionException {
        // Given
        final Game game = new Game(RangeType.VERTICAL, RangeType.VERTICAL);
        for (final Action action : preparatoryActions) {
            game.performAction(action);
        }

        // Host Thief should NOT be at home at this point
        Assertions.assertNotEquals(Game.HOST_HOME_ROW, game.getHostThief().getY());

        // When
        game.performAction(catchAction);

        // Then
        Assertions.assertEquals(Game.HOST_HOME_ROW, game.getHostThief().getY());
        Assertions.assertFalse(game.getHostThief().isCarrying());
    }

    private static Stream<Arguments> hostVerticalCatchesProvider() throws IllegalActionException {
        return Stream.of(
                Arguments.of(
                        Arrays.asList(
                               // TODO Preparatory actions
                        ),
                        // TODO Catching action
                        new Action(ActionType.MOVE_THIEF, 2, 2)
                )
        );
    }

}
