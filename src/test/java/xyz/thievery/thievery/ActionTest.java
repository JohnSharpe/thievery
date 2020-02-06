package xyz.thievery.thievery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;

class ActionTest {

    @Test
    void testActionNeedsActionType() {
        try {
            new Action(null);
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.MALFORMED_ACTION, e.getReason());
        }

        try {
            new Action(null, 2, 1);
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.MALFORMED_ACTION, e.getReason());
        }
    }

    @Test
    void testMoveGuardNeedsDestinationCoordinates() {
        try {
            new Action(ActionType.MOVE_GUARD);
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.MALFORMED_ACTION, e.getReason());
        }

        try {
            new Action(ActionType.MOVE_GUARD, 1 ,null);
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.MALFORMED_ACTION, e.getReason());
        }

        try {
            new Action(ActionType.MOVE_GUARD, null ,1);
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.MALFORMED_ACTION, e.getReason());
        }

        try {
            new Action(ActionType.MOVE_GUARD, null ,null);
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.MALFORMED_ACTION, e.getReason());
        }
    }

    @Test
    void testMoveThiefNeedsDestinationCoordinates() {
        try {
            new Action(ActionType.MOVE_THIEF);
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.MALFORMED_ACTION, e.getReason());
        }

        try {
            new Action(ActionType.MOVE_THIEF, 1 ,null);
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.MALFORMED_ACTION, e.getReason());
        }

        try {
            new Action(ActionType.MOVE_THIEF, null ,1);
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.MALFORMED_ACTION, e.getReason());
        }

        try {
            new Action(ActionType.MOVE_THIEF, null ,null);
            Assertions.fail();
        } catch (IllegalActionException e) {
            Assertions.assertEquals(IllegalActionReason.MALFORMED_ACTION, e.getReason());
        }
    }

}
