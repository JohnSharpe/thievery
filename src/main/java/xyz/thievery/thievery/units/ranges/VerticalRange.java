package xyz.thievery.thievery.units.ranges;

/**
 * A vertical range looks like this, where G is the guard and * is where he can see.
 *                ________________________
 * 6 (Opp Home)  |                        |
 *               |________________________|
 * 5             |    |    |    |    |    |
 *               |____|____|____|____|____|
 * 4             |    | *  |    |    |    |
 *               |____|____|____|____|____|
 * 3             |    | G  |    |    |    |
 *               |____|____|____|____|____|
 * 2             |    | *  |    |    |    |
 *               |____|____|____|____|____|
 * 1             |    |    |    |    |    |
 *               | ___|____|____|____|____|
 * 0 (Host Home) |                        |
 *               |________________________|
 *
 *                 0    1    2    3    4
 */
public class VerticalRange implements Range {

    @Override
    public boolean hasCaught(
            final int guardX,
            final int guardY,
            final int thiefX,
            final int thiefY,
            final boolean reveal
    ) {

        final int guardAbove = guardY + 1;
        final int guardBelow = guardY - 1;

        return thiefX == guardX && (thiefY == guardAbove || thiefY == guardBelow);
    }
}
