package fr.warzou.rennes1.connect4.core.object;

import fr.warzou.rennes1.connect4.utils.ObjectMatrix;

public class Grid {

    private final ObjectMatrix<Piece> grid;

    public Grid(int width, int height) {
        this.grid = new ObjectMatrix<>(Piece.class, Piece[].class, width, height, null);
    }
}
