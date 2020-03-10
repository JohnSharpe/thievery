package xyz.thievery.thievery.units;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.thievery.thievery.Game;
import xyz.thievery.thievery.TestUtils;

class UnitTest {

    @Test
    void testHostGuardStartsAtHostHome() {
        final Game game = TestUtils.createNoNonsenseGame();
        Assertions.assertEquals(0, game.getHostGuard().getX());
        Assertions.assertEquals(0, game.getHostGuard().getY());
    }

    @Test
    void testOpponentGuardStartsAtHostHome() {
        final Game game = TestUtils.createNoNonsenseGame();
        Assertions.assertEquals(0, game.getOpponentGuard().getX());
        Assertions.assertEquals(6, game.getOpponentGuard().getY());
    }

    @Test
    void testHostThiefStartsAtHostHome() {
        final Game game = TestUtils.createNoNonsenseGame();
        Assertions.assertEquals(0, game.getHostThief().getX());
        Assertions.assertEquals(0, game.getHostThief().getY());
    }

    @Test
    void testOpponentThiefStartsAtHostHome() {
        final Game game = TestUtils.createNoNonsenseGame();
        Assertions.assertEquals(0, game.getOpponentThief().getX());
        Assertions.assertEquals(6, game.getOpponentThief().getY());
    }

}
