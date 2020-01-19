package xyz.thievery.thievery.units;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.thievery.thievery.Action;
import xyz.thievery.thievery.ActionType;
import xyz.thievery.thievery.Game;
import xyz.thievery.thievery.Player;
import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;

class ThiefMoveTest extends MoveTest {

    @Override
    protected ActionType getActionType() {
        return ActionType.MOVE_THIEF;
    }

    @Override
    protected Unit getHostUnit(final Game game) {
        return game.getHostThief();
    }

    @Override
    protected Unit getOpponentUnit(final Game game) {
        return game.getOpponentThief();
    }

    @Test
    void testThiefCanNotMoveOntoYourGuard() throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 1));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 2));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_THIEF, 2, 1));

        game.performAction(new Action(Player.OPPONENT, ActionType.END_TURN));

        try {
            game.performAction(new Action(Player.HOST, ActionType.MOVE_THIEF, 2, 2));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.BLOCKED, e.getReason());
        }
    }

}
