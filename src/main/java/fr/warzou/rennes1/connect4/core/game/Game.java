package fr.warzou.rennes1.connect4.core.game;

import fr.warzou.rennes1.connect4.core.entity.Player;
import fr.warzou.rennes1.connect4.core.object.Grid;

import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private final boolean hasAI;
    private final Grid grid;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private Player current;

    public Game(boolean hasAI, Grid grid, Player firstPlayer, Player secondPlayer) {
        this.hasAI = hasAI;
        this.grid = grid;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.current = this.firstPlayer;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public static Game createGame() {
        return createGame(new Player('x'));
    }

    public static Game createGame(Player player) {
        return createGame(7, 6, player);
    }

    public static Game createGame(int column, int row, Player player) {
        //1.1
        Grid grid = new Grid(column, row);
        return createGame(true, grid, player, new Player('o'));
    }

    private static Game createGame(boolean ai, Grid grid, Player firstPlayer, Player secondPlayer) {
        int random = ThreadLocalRandom.current().nextInt(2);
        return random == 0 ? new Game(ai, grid, firstPlayer, secondPlayer)
                : new Game(ai, grid, secondPlayer, firstPlayer);
    }

    public Player getFirstPlayer() {
        return this.firstPlayer;
    }

    public Player getSecondPlayer() {
        return this.secondPlayer;
    }
}
