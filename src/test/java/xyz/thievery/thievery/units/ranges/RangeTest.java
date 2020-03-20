package xyz.thievery.thievery.units.ranges;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import xyz.thievery.thievery.Game;
import xyz.thievery.thievery.exceptions.RangeNotAvailableException;

import java.util.stream.Stream;

class RangeTest {

    @Test
    void testBothTeamsStartWithOneActiveRange() {
        final Game game = new Game(RangeType.VERTICAL, RangeType.VERTICAL);
        Assertions.assertNotNull(game.getHostGuard().getRange());
        Assertions.assertNotNull(game.getOpponentGuard().getRange());
    }

    @ParameterizedTest
    @MethodSource("rangeProvider")
    void testBothTeamsStartWithChosenRangeActive(final RangeType rangeType, final Class<?> range) {
        final Game game = new Game(rangeType, rangeType);
        Assertions.assertEquals(range, game.getHostGuard().getRange().getClass());
        Assertions.assertEquals(range, game.getOpponentGuard().getRange().getClass());
    }

//    @Test
//    void testBothTeamsHaveTwoRangesRemaining() {
//
//    }

    private static Stream<Arguments> rangeProvider() {
        return Stream.of(
                Arguments.of(RangeType.VERTICAL, VerticalRange.class),
                Arguments.of(RangeType.HORIZONTAL, HorizontalRange.class),
                Arguments.of(RangeType.REVEAL, RevealRange.class)
        );
    }

}
