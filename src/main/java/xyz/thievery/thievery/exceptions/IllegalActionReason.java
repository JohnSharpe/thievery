package xyz.thievery.thievery.exceptions;

public enum IllegalActionReason {

    GAME_ENDED,
    NOT_YOUR_TURN,
    UNRECOGNISED_ACTION_TYPE,

    // e.g. Guard landing on other Guard
    BLOCKED,

    // e.g. Guard trying to enter home
    ILLEGAL_MOVE,

    NO_SUCH_POSITION,
    MALFORMED_ACTION,

    // e.g. Guard cannot reveal
    ACTION_NOT_AVAILABLE,

    // TODO Reconsider this name
    NOT_WITHIN_REACH

}
