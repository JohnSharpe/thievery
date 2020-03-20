package xyz.thievery.thievery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.units.ranges.RangeType;

class EndTurnTest {

    @Test
    void testAHostMayImmemdiatelyEndTheirTurn() throws IllegalActionException {
        final Game game = new Game(RangeType.VERTICAL, RangeType.VERTICAL);
        game.performAction(new Action(ActionType.END_TURN));
        Assertions.assertEquals(Status.OPPONENT_TURN, game.getStatus());
    }

    @Test
    void testAnOpponentMayImmemdiatelyEndTheirTurn() throws IllegalActionException {
        final Game game = new Game(RangeType.VERTICAL, RangeType.VERTICAL);
        game.performAction(new Action(ActionType.END_TURN));
        game.performAction(new Action(ActionType.END_TURN));
        Assertions.assertEquals(Status.HOST_TURN, game.getStatus());
    }

    @Test
    void testAPlayerMayEndTheirTurnAfterOneAction() throws IllegalActionException {
        final Game game = new Game(RangeType.VERTICAL, RangeType.VERTICAL);
        game.performAction(new Action(ActionType.MOVE_GUARD, 2, 1));
        game.performAction(new Action(ActionType.END_TURN));
        Assertions.assertEquals(Status.OPPONENT_TURN, game.getStatus());
    }

    @Test
    void testAPlayerMayEndTheirTurnAfterTwoActions() throws IllegalActionException {
        final Game game = new Game(RangeType.VERTICAL, RangeType.VERTICAL);
        game.performAction(new Action(ActionType.MOVE_GUARD, 2, 1));
        game.performAction(new Action(ActionType.MOVE_GUARD, 2, 2));
        game.performAction(new Action(ActionType.END_TURN));
        Assertions.assertEquals(Status.OPPONENT_TURN, game.getStatus());
    }

}
