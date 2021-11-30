package fr.warzou.rennes1.connect4.core.object;

import fr.warzou.rennes1.connect4.core.entity.Player;
import fr.warzou.rennes1.connect4.utils.ObjectMatrix;

public class Grid {

    private final Piece nonPiece;
    private final ObjectMatrix<Piece> grid;

    public Grid(int width, int height) {
        this.nonPiece = new Piece(null, this, -1);
        //1.2
        this.grid = new ObjectMatrix<>(Piece.class, Piece[].class, width, height, nonPiece);
    }

    //2.1
    public void placePiece(Player player, int column) {
        for (int i = 0; i < this.grid.getHeight(); i++) {
            Piece piece = this.grid.getValue(column, i);
            if (piece.getPlayer() != null && i == 0)
                throw new IllegalArgumentException("Column '" + column + "' is full !");
            if (piece.getPlayer() != null) {
                setPiece(player, column, i);
                return;
            }
        }

        setPiece(player, column, this.grid.getHeight());
    }

    public ObjectMatrix<Piece> getGrid() {
        return this.grid;
    }

    private void setPiece(Player player, int column, int row) {
        this.grid.setValue(column, row - 1, new Piece(player, this, column + row * this.grid.getWidth()));
        if (checkWin(column, row - 1))
            System.out.println("test");
    }

    private boolean checkWin(int column, int row) {
        Piece piece = this.grid.getValue(column, row);
        Player player = piece.getPlayer();
        if (player == null)
            return false;
        return checkVerticalWin(player, column, row) || checkHorizontaleWin(player, column, row);
    }

    private boolean checkVerticalWin(Player player, int column, int row) {
        int countUp = 0;
        int countDown = 0;
        for (int i = row - 1; i >= 0; i--) {
            Piece piece = this.grid.getValue(column, i);
            Player check = piece.getPlayer();
            if (check == null)
                break;
            if (!check.equals(player))
                break;
            countUp++;
        }

        for (int i = row; i < this.grid.getHeight(); i++) {
            Piece piece = this.grid.getValue(column, i);
            Player check = piece.getPlayer();
            if (check == null)
                break;
            if (!check.equals(player))
                break;
            countDown++;
        }

        return countUp + countDown >= 4;
    }

    private boolean checkHorizontaleWin(Player player, int column, int row) {
        int countLeft = 0;
        int countRight = 0;
        for (int i = column - 1; i >= 0; i--) {
            Piece piece = this.grid.getValue(i, row);
            Player check = piece.getPlayer();
            if (check == null)
                break;
            if (!check.equals(player))
                break;
            countLeft++;
        }

        for (int i = column; i < this.grid.getWidth(); i++) {
            Piece piece = this.grid.getValue(i, row);
            Player check = piece.getPlayer();
            if (check == null)
                break;
            if (!check.equals(player))
                break;
            countRight++;
        }

        return countLeft + countRight >= 4;
    }
}
