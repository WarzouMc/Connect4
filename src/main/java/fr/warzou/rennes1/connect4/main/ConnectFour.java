package fr.warzou.rennes1.connect4.main;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class ConnectFour {

    private final int width;
    private final int height;
    private final int[][] grid;
    private final boolean ai;
    private final String[] playerNames;

    private int currentPlayer;

    private static final String PLAY_MESSAGE = "Quel coup pour le joueur %s ? -> ";
    private static final String AI_MOMENT_MESSAGE = "%s joue... ? -> ";
    private static final String NOT_INT_MESSAGE = "Le message '%s' ne représente pas un entier naturel !";
    private static final String NOT_IN_BOUND_MESSAGE = "L'entier '%s' n'est pas entre 1 et 7 compris !";
    private static final String WIN_MESSAGE = "Le joueur gagnant est %s !!!\nGrille final :\n";
    private static final char[] ICONS = new char[] {'o', 'x'};

    /**
     * Init game parameters.
     * @param ai true is first player play against an AI.
     * @param playerNames player names
     */
    private ConnectFour(boolean ai, String[] playerNames) {
        this.width = 7;
        this.height = 6;
        this.grid = new int[this.width][this.height];
        this.ai = ai;
        this.playerNames = playerNames;
    }

    /**
     * Start the game.
     * @param player player who start.
     */
    private void start(int player) {
        this.currentPlayer = player;
        loop();
    }

    /**
     * This method creates the game loop and allows you to change players between two turns.
     */
    private void loop() {
        Scanner scanner = new Scanner(System.in);
        while (!hasWin(0, this.grid) && !hasWin(1, this.grid) && !isDrawn(this.grid)) {
            printGrid();

            if (this.ai && this.currentPlayer == 1) {
                System.out.printf((AI_MOMENT_MESSAGE) + "%n", this.playerNames[1]);
                if (!playAI())
                    playRandom();
                this.currentPlayer = 0;
                continue;
            }

            int column = nextColumn(scanner);
            while (!play(this.currentPlayer, column - 1, this.grid))
                column = nextColumn(scanner);
            this.currentPlayer = this.currentPlayer == 0 ? 1 : 0;
        }
        win();
        scanner.close();
    }

    /**
     * This method is only call when game is finish.<br>
     * That just say who won if someone won the game.
     */
    private void win() {
        System.out.println("\n" +
                "#".repeat(29) +
                "\n");
        if (isDrawn(this.grid)) {
            System.out.println("Match nulle \n" +
                    "Grille finale :");
            printGrid();
            return;
        }

        System.out.printf((WIN_MESSAGE) + "%n", hasWin(0, this.grid) ? this.playerNames[0] : this.playerNames[1]);
        printGrid();
    }

    /**
     * Returns the column from then entered.
     * @param scanner scanner
     * @return the column from then entered.
     */
    private int nextColumn(Scanner scanner) {
        System.out.printf(PLAY_MESSAGE, this.playerNames[this.currentPlayer]);
        String line;
        int column;
        while (Utils.isNotInteger(line = scanner.nextLine()) || ((column = Integer.parseInt(line)) < 1 || column > 7)) {
            if (Utils.isNotInteger(line)) {
                System.out.printf(NOT_INT_MESSAGE + '\n', line);
                System.out.printf(PLAY_MESSAGE, this.playerNames[this.currentPlayer]);
                continue;
            }
            column = Integer.parseInt(line);
            System.out.printf(NOT_IN_BOUND_MESSAGE + '\n', column);
            System.out.printf(PLAY_MESSAGE, this.playerNames[this.currentPlayer]);
        }
        return column;
    }

    /**
     * Allow to place a piece in a target column
     * @param player play who play
     * @param column target column where shot will be play
     * @param grid target grid
     * @return true if shot can be play, false else.
     */
    private boolean play(int player, int column, int[][] grid) {
        if (isFull(column, grid)) {
            System.out.println("Colonne complete !!!");
            return false;
        }
        int row = firstEmpty(column, grid);
        grid[column][row] = player + 1;
        return true;
    }

    /**
     * When {@code this.ai} is on true this method is call to make play AI.
     * @return true if shot can be play, false else.
     */
    private boolean playAI() {
        return playRandom4();
    }

    /**
     * Simple random shot (only for AI).
     * @return true if shot can be play, false else.
     */
    private boolean playRandom() {
        int column = ThreadLocalRandom.current().nextInt(7);
        while (isFull(column, this.grid))
            column = ThreadLocalRandom.current().nextInt(7);
        return play(1, column, this.grid);
    }

    /**
     * Play a random shot for AI.<br>
     * If a shot allow a win for AI, this shot is play.
     * @return true if shot can be play, false else.
     */
    private boolean playRandom2() {
        int[][] clone = Utils.deepArrayClone(this.grid);
        int[] winColumns = new int[this.width];
        int count = 0;
        for (int i = 0; i < this.width; i++) {
            if (isFull(i, clone))
                continue;
            play(1, i, clone);
            if (hasWin(1, clone))
                winColumns[count++] = i;
            clone = Utils.deepArrayClone(this.grid);
        }

        if (count > 0) {
            int random = ThreadLocalRandom.current().nextInt(count);
            return play(1, winColumns[random], grid);
        }
        return playRandom();
    }

    /**
     * Play a random shot for AI.<br>
     * If a shot allow a win for AI, this shot is play.<br>
     * If a shot allow a win to the other player, this shot is not play.
     * @return true if shot can be play, false else.
     */
    private boolean playRandom3() {
        int[][] clone = Utils.deepArrayClone(this.grid);
        int[] winColumns = new int[this.width];
        int countWin = 0;

        int[] loseColumns = new int[this.width * this.width];
        int countLose = 0;
        for (int i = 0; i < this.width; i++) {
            if (isFull(i, clone))
                continue;
            play(1, i, clone);
            if (hasWin(1, clone))
                winColumns[countWin++] = i;

            int[][] cloneLose = Utils.deepArrayClone(clone);
            for (int j = 0; j < this.width; j++) {
                if (isFull(j, cloneLose))
                    continue;
                play(0, j, cloneLose);
                if (!hasWin(0, cloneLose))
                    loseColumns[countLose++] = j;
                cloneLose = Utils.deepArrayClone(clone);
            }

            clone = Utils.deepArrayClone(this.grid);
        }

        if (countWin > 0) {
            int random = ThreadLocalRandom.current().nextInt(countWin);
            return play(1, winColumns[random], grid);
        }

        if (countLose > 0) {
            int random = ThreadLocalRandom.current().nextInt(countLose);
            return play(1, loseColumns[random], grid);
        }
        return playRandom();
    }

    /**
     * Play a random shot for AI.<br>
     * If a shot allow a win for AI, this shot is play.<br>
     * If a shot allow a win to the other player, this shot is not play.
     * @return true if shot can be play, false else.
     */
    private boolean playRandom4() {
        int[][] clone = Utils.deepArrayClone(this.grid);
        int[] columns = new int[this.width];
        int count = 0;

        for (int i = 0; i < this.width; i++) {
            if (isFull(i, clone))
                continue;
            play(1, i, clone);
            if (hasWin(1, clone))
                columns[count++] = i;
            clone = Utils.deepArrayClone(this.grid);
        }

        if (count > 0) {
            int random = ThreadLocalRandom.current().nextInt(count);
            return play(1, columns[random], grid);
        }

        clone = Utils.deepArrayClone(this.grid);
        columns = new int[this.width];
        count = 0;

        for (int i = 0; i < clone.length; i++) {
            if (isFull(i, clone))
                continue;
            play(0, i, clone);
            if (hasWin(0, clone))
                columns[count++] = i;
            clone = Utils.deepArrayClone(this.grid);
        }
        if (count > 0) {
            int random = ThreadLocalRandom.current().nextInt(count);
            return play(1, columns[random], this.grid);
        }
        return playRandom3();
    }

    /**
     * Print current grid in {@link System#out}
     */
    private void printGrid() {
        StringBuilder builder = new StringBuilder();
        for (int i = this.height - 1; i >= 0; i--) {
            builder.append("| ");
            for (int j = 0; j < this.width; j++)
                builder.append(this.grid[j][i] == 0 ? ' ' : ICONS[this.grid[j][i] - 1]).append(" | ");
            builder.replace(builder.length() - 2, builder.length(), "|\n");
        }
        System.out.println(builder.substring(0, builder.length() - 1) + '\n' +
                "  1 - 2 - 3 - 4 - 5 - 6 - 7");
    }

    /**
     * Returns true if target columns is full.
     * @param column column
     * @param grid target grid to check
     * @return true if target columns is full.
     */
    private boolean isFull(int column, int[][] grid) {
        return firstEmpty(column, grid) == grid[0].length;
    }

    /**
     * Returns index of first empty slot in target column.
     * @param column target column in grid
     * @param grid grid
     * @return index of first empty slot in target column.
     */
    private int firstEmpty(int column, int[][] grid) {
        int i = 0;
        while (grid[column][i++] != 0) {
            if (i == grid[0].length)
                return i;
        }
        return i - 1;
    }

    /**
     * Returns true if game is drawn.
     * @param grid game grid
     * @return true if game is drawn.
     */
    private boolean isDrawn(int[][] grid) {
        boolean result = true;
        for (int i = 0; i < this.width; i++) {
            result = result && isFull(i, grid);
        }
        return result && !hasWin(0, grid) && !hasWin(1, grid);
    }

    /**
     * Returns true if player has won.
     * @param player target player
     * @param grid game grid
     * @return true if player has won.
     */
    private boolean hasWin(int player, int[][] grid) {
        boolean win = false;
        for(int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) win = win || hasWin(player, i, j, grid);
        }
        return win;
    }

    /**
     * Returns true if player has won
     * @param player targe player
     * @param x slot x
     * @param y slot y
     * @param grid game grid
     * @return true if player has won.
     */
    private boolean hasWin(int player, int x, int y, int[][] grid) {
        player++;
        return (hasWinHorizontal(player, x, grid) || hasWinVertical(player, y, grid) || hasWinRisingDiagonal(player, x, y, grid)
                || hasWinDownwardDiagonal(player, x, y, grid));
    }

    /**
     * Returns true if a horizontal alignment of 4 same piece is detected.
     * @param player target player
     * @param x row
     * @param grid game grid
     * @return true if a horizontal alignment of 4 same piece is detected.
     */
    private boolean hasWinHorizontal(int player, int x, int[][] grid) {
        int count = 0;
        for (int i = 0; i < this.height; i++) {
            int checkPlayer = grid[x][i];
            if (checkPlayer != player) {
                count = 0;
                continue;
            }
            count++;
            if (count >= 4)
                return true;
        }
        return false;
    }

    /**
     * Returns true if a vertical alignment of 4 same piece is detected.
     * @param player target player
     * @param y column
     * @param grid game grid
     * @return true if a vertical alignment of 4 same piece is detected.
     */
    private boolean hasWinVertical(int player, int y, int[][] grid) {
        int count = 0;
        for (int i = 0; i < this.width; i++) {
            int check = grid[i][y];
            if (check != player) {
                count = 0;
                continue;
            }
            count++;
            if (count >= 4)
                return true;
        }
        return false;
    }

    /**
     * Returns true if a diagonal alignment of 4 same piece is detected.
     * @param player target slot
     * @param x x slot
     * @param y y slot
     * @param grid game grid
     * @return true if a diagonal alignment of 4 same piece is detected.
     */
    private boolean hasWinRisingDiagonal(int player, int x, int y, int[][] grid) {
        int countUp = 0;
        int countDown = 0;

        int checkY = y - 1;
        for (int checkX = x - 1; checkX >= 0; checkX--) {
            if (checkY < 0)
                break;
            int check = grid[checkX][checkY--];
            if (player != check)
                break;
            countUp++;
        }

        checkY = y;
        for (int checkX = x; checkX < grid.length; checkX++) {
            if (checkY >= grid[x].length)
                break;
            int check = grid[checkX][checkY++];
            if (player != check)
                break;
            countDown++;
        }

        return countUp + countDown >= 4;
    }

    /**
     * Returns true if a diagonal alignment of 4 same piece is detected.
     * @param player target player
     * @param x x slot
     * @param y y slot
     * @param grid game grid
     * @return true if a diagonal alignment of 4 same piece is detected.
     */
    private boolean hasWinDownwardDiagonal(int player, int x, int y, int[][] grid) {
        int countUp = 0;
        int countDown = 0;

        int checkY = y;
        for (int checkX = x - 1; checkX >= 0; checkX--) {
            if (checkY >= grid[x].length)
                break;
            int check = grid[checkX][checkY++];
            if (player != check)
                break;
            countUp++;
        }

        checkY = y - 1;
        for (int checkX = x; checkX < grid.length; checkX++) {
            if (checkY < 0)
                break;
            int check = grid[checkX][checkY--];
            if (player != check)
                break;
            countDown++;
        }

        return countUp + countDown >= 4;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Jouer contre l'ordi ? -> ");
        boolean ai = scanner.nextBoolean();

        String secondPlayer = ai ? "ai" : "2";

        ConnectFour connectFour;

        if (args.length == 0) connectFour = new ConnectFour(ai, new String[]{"1", secondPlayer});
        else if (args.length == 1) connectFour = new ConnectFour(ai, new String[]{args[0], secondPlayer});
        else connectFour = new ConnectFour(ai, new String[] {args[0], args[1]});

        connectFour.start(ThreadLocalRandom.current().nextInt(2));
    }

    @Override
    public String toString() {
        return "ConnectFour{" +
                "width=" + width +
                ", height=" + height +
                ", grid=" + Arrays.deepToString(grid) +
                ", ia=" + ai +
                ", playerNames=" + Arrays.toString(playerNames) +
                ", currentPlayer=" + currentPlayer +
                '}';
    }

    /**
     * Some utils methods.
     */
    public static class Utils {

        /**
         * Returns true if target string is not an integer.
         * @param string target {@link String}
         * @return true if target string is not an integer.
         */
        private static boolean isNotInteger(String string) {
            if (string.isEmpty() || string.isBlank())
                return true;
            boolean integer = true;
            for (char c : string.toCharArray())
                integer = integer && Character.isDigit(c);
            return !integer;
        }

        /**
         * Returns a deep copy of {@code array}.
         * @param array source array
         * @return deep copy of {@code array}.
         */
        public static int[][] deepArrayClone(int[][] array) {
            int[][] clone = new int[array.length][];
            for (int i = 0; i < array.length; i++)
                clone[i] = cloneArray(array[i]);
            return clone;
        }

        /**
         * Returns a copy of {@code array}.
         * @param array source array
         * @return copy of {@code array}.
         */
        public static int[] cloneArray(int[] array) {
            int[] clone = new int[array.length];
            for (int i = 0; i < array.length; i++)
                clone[i] = array[i];
            return clone;
        }
    }
}
