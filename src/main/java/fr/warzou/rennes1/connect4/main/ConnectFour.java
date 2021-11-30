package fr.warzou.rennes1.connect4.main;

import fr.warzou.rennes1.connect4.core.game.Game;

public class ConnectFour {

    public static void main(String[] args) {
        Game game = Game.createGame();
        System.out.println(game.getGrid().getGrid().toPrettyString() + '\n');
        for (int i = 0; i < 6; i++) {
            game.getGrid().placePiece(game.getFirstPlayer(), game.getGrid().getGrid().getHeight());
        }
        System.out.println(game.getGrid().getGrid().toPrettyString());
    }
}
