package xyz.thievery.thievery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class GameTest {

    @ParameterizedTest
    @MethodSource("hostOpponentPlayersProvider")
    void testGameRetainsPlayers(final Player host, final Player opponent) {
        final Game game = new Game(host, opponent);

        Assertions.assertEquals(host, game.getHost());
        Assertions.assertEquals(opponent, game.getOpponent());
    }

    @Test
    void testSamePlayerByIdentifierCannotBeHostAndOpponent() {
        final Player host = new Player("123", "John");
        final Player opponent = new Player("123", "Colin");

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Game(host, opponent));
    }

    @Test
    void testHostGoesFirst() {
        final Player host = new Player("ABC", "John");
        final Player opponent = new Player("DEF", "Paul");
        final Game game = new Game(host, opponent);

        Assertions.assertEquals(Turn.HOST, game.getTurn());
    }

    private static Stream<Arguments> hostOpponentPlayersProvider() {
        return Stream.of(
                Arguments.of(
                        new Player("ABC", "John"),
                        new Player("DEF", "Paul")
                ),
                Arguments.of(
                        new Player("GHI", "Dan"),
                        new Player("JKL", "Simon")
                ),
                Arguments.of(
                        new Player("MNO", "Peter"),
                        new Player("PQR", "Peter")
                )
        );
    }

}
