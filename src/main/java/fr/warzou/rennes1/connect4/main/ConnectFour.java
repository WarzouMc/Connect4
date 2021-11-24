package fr.warzou.rennes1.connect4.main;

import fr.warzou.rennes1.connect4.core.game.GameStarter;

public class ConnectFour {

    public static void main(String[] args) {
        GameStarter starter = GameStarter.createGameStarter();
        starter.start();
    }
}
