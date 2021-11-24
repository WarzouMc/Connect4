package fr.warzou.rennes1.connect4.core.game;

import fr.warzou.rennes1.connect4.core.entity.Player;
import fr.warzou.rennes1.connect4.core.object.Grid;

import java.util.concurrent.ThreadLocalRandom;

public class GameStarter {

    private final Game game;
    private final GameLoop gameLoop;

    public GameStarter(Game game, GameLoop gameLoop) {
        this.game = game;
        this.gameLoop = gameLoop;
    }


    public static GameStarter createGameStarter() {
        return createGameStarter(new Player('.'));
    }

    public static GameStarter createGameStarter(Player player) {
        return createGameStarter(7, 6, player);
    }

    public static GameStarter createGameStarter(int column, int row, Player player) {
        Grid grid = new Grid(column, row);
        return createGameStarter(true, grid, player, null);
    }

    private static GameStarter createGameStarter(boolean ai, Grid grid, Player firstPlayer, Player secondPlayer) {
        int random = ThreadLocalRandom.current().nextInt(2);
        Game game = random == 0 ? new Game(ai, grid, firstPlayer, secondPlayer)
                : new Game(ai, grid, secondPlayer, firstPlayer);
        GameLoop loop = new GameLoop();
        return new GameStarter(game, loop);
    }

    public void start() {
        //todo
        System.out.println(this.game.getGrid().getGrid().toPrettyString());
    }

    public Game getGame() {
        return this.game;
    }
}
