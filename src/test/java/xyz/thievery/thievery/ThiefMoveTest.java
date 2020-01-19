package xyz.thievery.thievery;

import xyz.thievery.thievery.units.Unit;

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

    // TODO Later, thief movements onto your/their guard

}
