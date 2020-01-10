package xyz.thievery.thievery;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * A single player of thievery.
 * Neither identifier nor name can be null, empty or blank.
 */
public class Player {

    // This field should uniquely identify a player in some database.
    private final String identifier;
    // This field should publicly identify one human by another.
    private final String name;

    public Player(String identifier, String name) {
        if (StringUtils.isAnyBlank(identifier, name)) {
            throw new IllegalArgumentException("Neither identifier nor name may be blank.");
        }

        this.identifier = identifier;
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return identifier.equals(player.identifier) &&
                name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), identifier, name);
    }

}
