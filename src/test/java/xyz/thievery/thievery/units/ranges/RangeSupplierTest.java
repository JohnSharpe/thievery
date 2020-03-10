package xyz.thievery.thievery.units.ranges;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import xyz.thievery.thievery.exceptions.RangeNotAvailableException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

class RangeSupplierTest {

    @Test
    void testStartingRanges() {
        final Set<RangeType> startingRanges = RangeSupplier.createStartingRanges();

        Assertions.assertNotNull(startingRanges);
        Assertions.assertEquals(3, startingRanges.size());
        Assertions.assertTrue(startingRanges.contains(RangeType.VERTICAL));
        Assertions.assertTrue(startingRanges.contains(RangeType.HORIZONTAL));
        Assertions.assertTrue(startingRanges.contains(RangeType.REVEAL));
    }

    @Test
    void testDumbGets() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> RangeSupplier.getRangeFromAvailable(null, new HashSet<>()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> RangeSupplier.getRangeFromAvailable(RangeType.VERTICAL, null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> RangeSupplier.getRangeFromAvailable(null, null));
    }

    @ParameterizedTest
    @MethodSource("validGetFromAvailableRangesProvider")
    void testValidGets(final RangeType range, final Set<RangeType> availableRanges, final Class<?> expected) throws RangeNotAvailableException {
        final int startsWith = availableRanges.size();

        final Range actual = RangeSupplier.getRangeFromAvailable(range, availableRanges);

        Assertions.assertEquals(startsWith - 1, availableRanges.size());
        Assertions.assertEquals(expected, actual.getClass());
    }

    @ParameterizedTest
    @MethodSource("invalidGetFromAvailableRangesProvider")
    void testInvalidGets(final RangeType range, final Set<RangeType> availableRanges) {
        Assertions.assertThrows(RangeNotAvailableException.class, () -> RangeSupplier.getRangeFromAvailable(range, availableRanges));
    }

    private static Stream<Arguments> validGetFromAvailableRangesProvider() {
        return Stream.of(
                Arguments.of(
                        RangeType.VERTICAL,
                        RangeSupplier.createStartingRanges(),
                        VerticalRange.class
                ),
                Arguments.of(
                        RangeType.HORIZONTAL,
                        RangeSupplier.createStartingRanges(),
                        HorizontalRange.class
                ),
                Arguments.of(
                        RangeType.REVEAL,
                        RangeSupplier.createStartingRanges(),
                        RevealRange.class
                ),
                Arguments.of(
                        RangeType.HORIZONTAL,
                        createAvailabilitySet(RangeType.HORIZONTAL, RangeType.VERTICAL),
                        HorizontalRange.class
                ),
                Arguments.of(
                        RangeType.REVEAL,
                        createAvailabilitySet(RangeType.HORIZONTAL, RangeType.REVEAL),
                        RevealRange.class
                ),
                Arguments.of(
                        RangeType.VERTICAL,
                        createAvailabilitySet(RangeType.VERTICAL),
                        VerticalRange.class
                )
        );
    }

    private static Stream<Arguments> invalidGetFromAvailableRangesProvider() {
        return Stream.of(
                Arguments.of(
                        RangeType.REVEAL,
                        createAvailabilitySet(RangeType.HORIZONTAL, RangeType.VERTICAL),
                        new HorizontalRange()
                ),
                Arguments.of(
                        RangeType.VERTICAL,
                        createAvailabilitySet(RangeType.HORIZONTAL, RangeType.REVEAL),
                        new RevealRange()
                ),
                Arguments.of(
                        RangeType.HORIZONTAL,
                        createAvailabilitySet(RangeType.VERTICAL),
                        new VerticalRange()
                ),
                Arguments.of(
                        RangeType.VERTICAL,
                        createAvailabilitySet(RangeType.REVEAL),
                        new VerticalRange()
                ),
                Arguments.of(
                        RangeType.HORIZONTAL,
                        new HashSet<>(),
                        new VerticalRange()
                ),
                Arguments.of(
                        RangeType.VERTICAL,
                        new HashSet<>(),
                        new VerticalRange()
                )
        );
    }

    private static Set<RangeType> createAvailabilitySet(final RangeType... rangeTypes) {
        return new HashSet<>(Arrays.asList(rangeTypes));
    }

}
