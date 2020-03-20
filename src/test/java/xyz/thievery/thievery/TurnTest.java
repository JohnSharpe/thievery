package xyz.thievery.thievery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.units.ranges.RangeType;

class TurnTest {

    @Test
    void testATurnIsAutomaticallyEndedAfterThreeValidActions() throws IllegalActionException {
        final Game game = new Game(RangeType.VERTICAL, RangeType.VERTICAL);

        Assertions.assertEquals(Status.HOST_TURN, game.getStatus());
        Assertions.assertEquals(3, game.getRemainingActions());
        game.performAction(new Action(ActionType.MOVE_GUARD, 2, 1));

        Assertions.assertEquals(Status.HOST_TURN, game.getStatus());
        Assertions.assertEquals(2, game.getRemainingActions());
        game.performAction(new Action(ActionType.MOVE_GUARD, 2, 2));

        Assertions.assertEquals(Status.HOST_TURN, game.getStatus());
        Assertions.assertEquals(1, game.getRemainingActions());
        game.performAction(new Action(ActionType.MOVE_GUARD, 2, 3));

        Assertions.assertEquals(Status.OPPONENT_TURN, game.getStatus());
        Assertions.assertEquals(3, game.getRemainingActions());

    }

//    @Test
//    void testActionsCanNotBePerformedOnEndedGames() {
//        // TODO Test drive your way to a game that can end first
//    }

}
