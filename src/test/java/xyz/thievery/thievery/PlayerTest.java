package xyz.thievery.thievery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
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

    @Test
    void testPlayerEqualityByReference() {
        final Player player = new Player("A", "B");
        Assertions.assertEquals(player, player);
    }

    @Test
    void testPlayerEqualityByValue() {
        final Player firstPlayer = new Player("A", "B");
        final Player secondPlayer = new Player("A", "B");
        Assertions.assertEquals(firstPlayer, secondPlayer);
    }

    @ParameterizedTest
    @MethodSource("disparatePlayersProvider")
    void testPlayerInequalityByValue(final Player firstPlayer, final Player secondPlayer) {
        Assertions.assertNotEquals(firstPlayer, secondPlayer);
    }

    @Test
    void testPlayerInequalityByType() {
        final Player firstPlayer = new Player("A", "B");
        final List<String> secondObject = new ArrayList<>();
        Assertions.assertNotEquals(firstPlayer, secondObject);
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

    private static Stream<Arguments> disparatePlayersProvider() {
        return Stream.of(
                Arguments.of(
                        new Player("A", "B"),
                        new Player("C", "D")
                ),
                Arguments.of(
                        new Player("A", "B"),
                        new Player("A", "D")
                ),
                Arguments.of(
                        new Player("A", "B"),
                        new Player("C", "B")
                )
        );
    }

}
