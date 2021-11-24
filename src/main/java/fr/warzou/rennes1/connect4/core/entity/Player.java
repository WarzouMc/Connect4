package fr.warzou.rennes1.connect4.core.entity;

public class Player {

    private final char icon;

    public Player(char icon) {
        this.icon = icon;
    }

    public char getIcon() {
        return this.icon;
    }
}
