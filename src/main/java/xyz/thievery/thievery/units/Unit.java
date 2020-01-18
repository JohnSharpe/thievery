package xyz.thievery.thievery.units;

import xyz.thievery.thievery.Game;
import xyz.thievery.thievery.Player;

public abstract class Unit {

    private int x;
    private int y;

    public Unit(final Player player) {
        if (Player.HOST == player) {
            this.x = 0;
            this.y = Game.HOST_HOME_ROW;
        } else {
            this.x = 0;
            this.y = Game.OPPONENT_HOME_ROW;
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
