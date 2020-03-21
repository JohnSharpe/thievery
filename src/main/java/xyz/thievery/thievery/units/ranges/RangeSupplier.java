package xyz.thievery.thievery.units.ranges;

import xyz.thievery.thievery.exceptions.RangeNotAvailableException;

import java.util.HashSet;
import java.util.Set;

public abstract class RangeSupplier {

    private RangeSupplier() {}

    public static Range getRangeFromAvailable(final RangeType range, final Set<RangeType> availableRanges) throws RangeNotAvailableException {
        // A runtime exception is fine, if this happens then something has gone very wrong.
        if (range == null || availableRanges ==  null) {
            // TODO Maybe add some text to this
            throw new IllegalArgumentException();
        }

        if (availableRanges.contains(range)) {
            availableRanges.remove(range);
            switch (range) {
                case VERTICAL:
                    return new VerticalRange();
                case HORIZONTAL:
                    return new HorizontalRange();
                case REVEAL:
                    return new RevealRange();
                default:
                    // Not sure how to test this impossible case.
                    throw new IllegalArgumentException();
            }
        } else {
            throw new RangeNotAvailableException();
        }
    }

    public static Set<RangeType> createStartingRanges() {
        final Set<RangeType> startingRanges = new HashSet<>();
        startingRanges.add(RangeType.VERTICAL);
        startingRanges.add(RangeType.HORIZONTAL);
        startingRanges.add(RangeType.REVEAL);
        return startingRanges;
    }

}
