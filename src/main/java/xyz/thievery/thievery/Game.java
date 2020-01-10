package xyz.thievery.thievery;

public class Game {

    private final Player host;
    private final Player opponent;

    private Turn turn;

    public Game(Player host, Player opponent) {
        if (host.getIdentifier().equals(opponent.getIdentifier())) {
            throw new IllegalArgumentException("Host and opponent may not have the same identifier.");
        }

        this.host = host;
        this.opponent = opponent;
        this.turn = Turn.HOST;
    }

    public Player getHost() {
        return host;
    }

    public Player getOpponent() {
        return opponent;
    }

    public Turn getTurn() {
        return turn;
    }
}
