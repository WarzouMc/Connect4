package fr.warzou.rennes1.connect4.core.entity;

public class Player {

    private final char icon;

    public Player(char icon) {
        this.icon = icon;
    }

    public char getIcon() {
        return this.icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return icon == player.icon;
    }

    @Override
    public int hashCode() {
        return icon;
    }
}
