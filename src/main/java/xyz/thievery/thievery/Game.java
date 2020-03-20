package xyz.thievery.thievery;

import xyz.thievery.thievery.exceptions.IllegalActionException;
import xyz.thievery.thievery.exceptions.IllegalActionReason;
import xyz.thievery.thievery.exceptions.RangeNotAvailableException;
import xyz.thievery.thievery.units.Guard;
import xyz.thievery.thievery.units.Thief;
import xyz.thievery.thievery.units.ranges.RangeSupplier;
import xyz.thievery.thievery.units.ranges.RangeType;

import java.util.Set;

/**
 * A single game of Thievery.
 *
 * The map looks like this
 *                ________________________
 * 6 (Opp Home)  |                        |
 *               |________________________|
 * 5             |    |    |    |    |    |
 *               |____|____|____|____|____|
 * 4             |    |    |    |    |    |
 *               |____|____|____|____|____|
 * 3             |    |    |    |    |    |
 *               |____|____|____|____|____|
 * 2             |    |    |    |    |    |
 *               |____|____|____|____|____|
 * 1             |    |    |    |    |    |
 *               | ___|____|____|____|____|
 * 0 (Host Home) |                        |
 *               |________________________|
 *
 *                 0    1    2    3    4
 */
public class Game {

    public static final int LEFTMOST_COLUMN = 0;
    public static final int RIGHTMOST_COLUMN = 4;

    public static final int HOST_HOME_ROW = 0;
    public static final int OPPONENT_HOME_ROW = 6;

    private static final int ACTIONS_PER_TURN = 3;

    // TODO Eventually this will be unnecessary
    private static final int STEALS_TO_WIN = 3;

    private final Set<RangeType> hostAvailableRanges;
    private final Guard hostGuard;
    private final Thief hostThief;
    // TODO Will be unnecessary
    private int hostSteals;

    private final Set<RangeType> opponentAvailableRanges;
    private final Guard opponentGuard;
    private final Thief opponentThief;
    // TODO Will be unnecessary
    private int opponentSteals;

    private Status status;
    private int remainingActions;

    public Game(final RangeType hostRangeType, final RangeType opponentRangeType) {
        try {
            this.hostAvailableRanges = RangeSupplier.createStartingRanges();
            this.hostGuard = new Guard(Player.HOST, RangeSupplier.getRangeFromAvailable(hostRangeType, this.hostAvailableRanges));
            this.hostThief = new Thief(Player.HOST);
            // TODO Will be unnecessary
            this.hostSteals = 0;

            this.opponentAvailableRanges = RangeSupplier.createStartingRanges();
            this.opponentGuard = new Guard(Player.OPPONENT, RangeSupplier.getRangeFromAvailable(opponentRangeType, this.opponentAvailableRanges));
            this.opponentThief = new Thief(Player.OPPONENT);
            // TODO Will be unnecessary
            this.opponentSteals = 0;

            this.status = Status.HOST_TURN;
            this.remainingActions = ACTIONS_PER_TURN;
        } catch (RangeNotAvailableException e) {
            // If this happens, we should just get out of here.
            throw new RuntimeException(e);
        }
    }

    public Status getStatus() {
        return status;
    }

    public int getRemainingActions() {
        return remainingActions;
    }

    public Guard getHostGuard() {
        return hostGuard;
    }

    public Thief getHostThief() {
        return hostThief;
    }

    public int getHostSteals() {
        return hostSteals;
    }

    public Guard getOpponentGuard() {
        return opponentGuard;
    }

    public Thief getOpponentThief() {
        return opponentThief;
    }

    public int getOpponentSteals() {
        return opponentSteals;
    }

    public void performAction(final Action action) throws IllegalActionException {
        if (Status.END == this.status) {
            throw new IllegalActionException(IllegalActionReason.GAME_ENDED, "An action cannot be performmed on an ended game.");
        }

        final Guard myGuard;
        final Thief myThief;
        final Guard theirGuard;
        // Are we ever concerned with their thief?
        // final Thief theirThief;

        final int myHome;
        final int theirHome;

        switch (this.status) {
            case HOST_TURN: {
                myGuard = this.hostGuard;
                myThief = this.hostThief;
                theirGuard = this.opponentGuard;
                // theirThief = this.opponentThief;
                myHome = HOST_HOME_ROW;
                theirHome = OPPONENT_HOME_ROW;
                break;
            }
            case OPPONENT_TURN: {
                myGuard = this.opponentGuard;
                myThief = this.opponentThief;
                theirGuard = this.hostGuard;
                // theirThief = this.hostThief;
                myHome = OPPONENT_HOME_ROW;
                theirHome = HOST_HOME_ROW;
                break;
            }
//            case END: {
//                throw new IllegalActionException(IllegalActionReason.GAME_ENDED,
//                        "An action cannot be performed on an ended game.");
//            }
            default: {
                // It should not be possible to reach this clause, but the uses of the final variables
                // would not compile otherwise.
                // TODO See if deferring this decision (switch) could save us having to include this clause.
                throw new IllegalArgumentException("This game has null status! This should not be allowed.");
            }
        }

        // Per-action validation should occur here before making any changes
        switch (action.getActionType()) {
            case END_TURN: {
                this.endTurn();
                return;
            }
            case MOVE_GUARD: {
                myGuard.validateMove(action.getX(), action.getY(), myThief, theirGuard);
                myGuard.executeMove(action.getX(), action.getY());
                break;
            }
            case MOVE_THIEF: {
                myThief.validateMove(action.getX(), action.getY(), myGuard);
                myThief.executeMove(action.getX(), action.getY());

                if (myThief.getY() == theirHome) {
                    myThief.setCarrying(true);
                }

                if (myThief.isCarrying() && myThief.getY() == myHome) {
                    // TODO What else does a successful steal mean?
                    myThief.setCarrying(false);
                    // TODO Lives are not intended to work this way so it is likely this inefficiency
                    // TODO will go away once more behaviour is implemented.
                    if (this.status == Status.HOST_TURN) {
                        this.hostSteals++;

                        if (this.hostSteals == STEALS_TO_WIN) {
                            this.status = Status.END;
                        }

                    } else {
                        this.opponentSteals++;

                        if (this.opponentSteals == STEALS_TO_WIN) {
                            this.status = Status.END;
                        }
                    }
                }

                break;
            }
//            case REVEAL:
//                this.remainingActions--;
//                break;
        }


        // Check for catches
        //// Host guard catches Opponent thief
        if (this.hostGuard.hasCaught(this.opponentThief)) {
            this.opponentThief.setCarrying(false);
            this.opponentThief.executeMove(0, OPPONENT_HOME_ROW);
        }

        //// Opponent guard catches Host thief
        if (this.opponentGuard.hasCaught(this.hostThief)) {
            this.hostThief.setCarrying(false);
            this.hostThief.executeMove(0, HOST_HOME_ROW);
        }

        this.remainingActions--;

        if (this.remainingActions == 0) {
            this.endTurn();
        }

    }

    private void endTurn() {
        if (Status.HOST_TURN == this.status) {
            this.status = Status.OPPONENT_TURN;
        } else if (Status.OPPONENT_TURN == this.status) {
            this.status = Status.HOST_TURN;
        }

        this.remainingActions = ACTIONS_PER_TURN;
    }

}
