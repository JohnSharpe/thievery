package xyz.thievery.thievery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    void testHostGoesFirstInNewGame() {
        final Game game = new Game();
        Assertions.assertEquals(Status.HOST_TURN, game.getStatus());
    }

}
