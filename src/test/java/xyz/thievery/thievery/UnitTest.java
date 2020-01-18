package xyz.thievery.thievery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UnitTest {

    @Test
    void testHostGuardStartsAtHostHome() {
        final Game game = new Game();
        Assertions.assertEquals(0, game.getHostGuard().getX());
        Assertions.assertEquals(0, game.getHostGuard().getY());
    }

    @Test
    void testOpponentGuardStartsAtHostHome() {
        final Game game = new Game();
        Assertions.assertEquals(0, game.getOpponentGuard().getX());
        Assertions.assertEquals(6, game.getOpponentGuard().getY());
    }

    // TODO Thieves will probably need this too

}
