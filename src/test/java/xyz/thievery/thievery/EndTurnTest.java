package xyz.thievery.thievery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.thievery.thievery.exceptions.IllegalActionException;

class EndTurnTest {

    @Test
    void testAHostMayImmemdiatelyEndTheirTurn() throws IllegalActionException {
        final Game game = new Game(PlayerProvider.HOST, PlayerProvider.OPPONENT);
        game.performAction(new Action(PlayerProvider.HOST.getIdentifier(), ActionType.END_TURN));
        Assertions.assertEquals(Turn.OPPONENT, game.getTurn());
    }

    @Test
    void testAnOpponentMayImmemdiatelyEndTheirTurn() throws IllegalActionException {
        final Game game = new Game(PlayerProvider.HOST, PlayerProvider.OPPONENT);
        game.performAction(new Action(PlayerProvider.HOST.getIdentifier(), ActionType.END_TURN));
        game.performAction(new Action(PlayerProvider.OPPONENT.getIdentifier(), ActionType.END_TURN));
        Assertions.assertEquals(Turn.HOST, game.getTurn());
    }

}
