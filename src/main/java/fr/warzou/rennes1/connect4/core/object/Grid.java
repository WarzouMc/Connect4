package fr.warzou.rennes1.connect4.core.object;

public class Grid {

    private final int[] piecesID;

    public Grid(int width, int height) {
        this.piecesID = new int[width * height];

    }
}
