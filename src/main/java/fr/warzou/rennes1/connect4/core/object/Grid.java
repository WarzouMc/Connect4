package fr.warzou.rennes1.connect4.core.object;

import fr.warzou.rennes1.connect4.core.entity.Player;
import fr.warzou.rennes1.connect4.utils.ObjectMatrix;

public class Grid {

    private final Piece nonPiece;
    private final ObjectMatrix<Piece> grid;

    public Grid(int width, int height) {
        this.nonPiece = new Piece(null, this, -1);
        this.grid = new ObjectMatrix<>(Piece.class, Piece[].class, width, height, nonPiece);
    }

    public void placePiece(Player player) {
    }

    public ObjectMatrix<Piece> getGrid() {
        return this.grid;
    }
}
