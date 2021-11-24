package fr.warzou.rennes1.connect4.core.object;

import fr.warzou.rennes1.connect4.core.entity.Player;

public class Piece {

    private final Player player;
    private final Grid grid;
    private final int slot;

    private final char view;

    public Piece(Player player, Grid grid, int slot) {
        this.player = player;
        this.grid = grid;
        this.slot = slot;

        this.view = player == null ? '\\' : player.getIcon();
    }

    public Player getPlayer() {
        return this.player;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public int getSlot() {
        return this.slot;
    }

    @Override
    public String toString() {
        return String.valueOf(this.view);
    }
}
