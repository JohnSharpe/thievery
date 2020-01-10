package xyz.thievery.thievery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class PlayerTest {

    @ParameterizedTest
    @MethodSource("validPlayerDetailsProvider")
    void testPlayerConstruction(final String identifier, final String name) {
        final Player player = new Player(identifier, name);

        Assertions.assertEquals(identifier, player.getIdentifier());
        Assertions.assertEquals(name, player.getName());
    }

    @ParameterizedTest
    @MethodSource("invalidPlayerDetailsProvider")
    void testPlayerCannotExistWithBlankDetails(final String identifier, final String name) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Player(identifier, name));
    }

    private static Stream<Arguments> validPlayerDetailsProvider() {
        return Stream.of(
                Arguments.of("12345678", "John"),
                Arguments.of("0", "A"),
                Arguments.of("4315-123-21536", "Barry")
        );
    }

    private static Stream<Arguments> invalidPlayerDetailsProvider() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of("1234", null),
                Arguments.of(null, "John"),
                Arguments.of("", "\n"),
                Arguments.of("\t\n\t", "Charlie"),
                Arguments.of("abcd", "  "),
                Arguments.of("", null)
        );
    }

}
