package xyz.thievery.thievery.units.ranges;

public interface Range {

    /**
     * Assume that landing directly on the guard is already dealt with.
     * Assume that off-map movement has already been filtered out.
     * Assume that if a thief is in one of the home rows, this function will not be called.
     *
     * @param guardX    guard's horizontal positioning
     * @param guardY    guard's vertical positioning
     * @param thiefX    thief's horizontal positioning
     * @param thiefY    thief's vertical positioning
     * @param reveal    is this a reveal turn
     * @return whether the guard has caught the thief by their range
     */
    boolean hasCaught(final int guardX, final int guardY, final int thiefX, final int thiefY, final boolean reveal);

}
