package xyz.thievery.thievery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;
import xyz.thievery.thievery.units.Unit;

class GuardMoveTest extends MoveTest {

    @Override
    protected ActionType getActionType() {
        return ActionType.MOVE_GUARD;
    }

    @Override
    protected Unit getHostUnit(Game game) {
        return game.getHostGuard();
    }

    @Override
    protected Unit getOpponentUnit(Game game) {
        return game.getOpponentGuard();
    }

    @Test
    void testGuardCanNotMoveOntoOtherGuard() throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 1));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 2));
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 2, 3));

        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, 2, 5));
        game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, 2, 4));

        try {
            game.performAction(new Action(Player.OPPONENT, ActionType.MOVE_GUARD, 2, 3));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.BLOCKED, e.getReason());
        }
    }

    @Test
    void testGuardCanNotEnterHome() throws IllegalActionException {
        final Game game = new Game();
        game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 0, 1));

        try {
            game.performAction(new Action(Player.HOST, ActionType.MOVE_GUARD, 0, 0));
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.ILLEGAL_MOVE, e.getReason());
        }
    }

    // TODO Later, Guard cannot move onto your own thief!

}
