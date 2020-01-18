package xyz.thievery.thievery.units;

import xyz.thievery.thievery.Player;

public abstract class Unit {

    private int x;
    private int y;

    public Unit(final Player player) {
        if (Player.HOST == player) {
            this.x = 0;
            this.y = 0;
        } else {
            this.x = 0;
            this.y = 6;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

}
