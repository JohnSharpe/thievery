package xyz.thievery.thievery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExampleTest {

    @Test
    void temporaryTest() {
        final Game game = new Game();
        Assertions.assertEquals(1, game.getOne());
    }

}
