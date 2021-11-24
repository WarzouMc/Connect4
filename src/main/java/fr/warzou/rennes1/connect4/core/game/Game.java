package fr.warzou.rennes1.connect4.core.game;

import fr.warzou.rennes1.connect4.core.entity.Player;
import fr.warzou.rennes1.connect4.core.object.Grid;

public class Game {

    private final boolean hasAI;
    private final Grid grid;
    private final Player firstPlayer;
    private final Player secondPlayer;

    public Game(boolean hasAI, Grid grid, Player firstPlayer, Player secondPlayer) {
        this.hasAI = hasAI;
        this.grid = grid;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }
}
