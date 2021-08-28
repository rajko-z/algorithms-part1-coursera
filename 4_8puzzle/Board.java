/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.ResizingArrayStack;

public class Board {

    private int[] board;
    private int n; // size of the board
    private int xEmpty; // x coordinate of empty tile
    private int yEmpty; // y coordinate of empty tile

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        board = new int[n * n];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                board[i * n + j] = tiles[i][j];
                if (isEmptyTile(board[i * n + j])) {
                    xEmpty = i;
                    yEmpty = j;
                }
            }
        }
    }

    private boolean isEmptyTile(int tile) { return tile == 0; }
    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%2d ", board[i * n + j]));
            }
            sb.append("\n");
        }
        return sb.toString();

    }
    // get the right x and y coordinate for the number in the board
    private int[] xytile(int num) {
        int full = num / n;
        int leftover = num % n;
        if (leftover == 0) return new int[]{full - 1, n - 1};
        return new int[]{full, leftover - 1};
    }

    // board dimension n
    public int dimension() { return n; }

    // number of tiles out of place
    public int hamming() {
        int retVal = 0;
        int counter = 1;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (!isEmptyTile(board[i * n + j])) {
                    if (board[i * n + j] != counter) ++retVal;
                }
                ++counter;
            }
        }
        return retVal;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int retVal = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (!isEmptyTile(board[i * n + j])) {
                    int[] xy = xytile(board[i * n + j]);
                    retVal += (Math.abs(i - xy[0]) + Math.abs(j - xy[1]));
                }
            }
        }
        return retVal;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int counter = 1;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (isEmptyTile(board[i * n + j]) && i == (n-1) && j == (n-1)) return true;
                if (board[i * n + j] != counter) return false;
                ++counter;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board other = (Board) y;
        if (other.board.length != board.length) return false;

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (board[i * n + j] != other.board[i * n + j]) return false;
            }
        }
        return true;

    }

    // check if index valid
    private boolean isIndexValid(int index) { return (index >= 0 && index < n); }

    // getCopyBoard
    private Board getCopyBoard(Board bd) {
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                tiles[i][j] = bd.board[i * n + j];
            }
        }
        return new Board(tiles);
    }


    private Board getNeighbor(int xcord, int ycord) {
        Board bd = getCopyBoard(this);
        int index = xcord * n + ycord;
        bd.board[xEmpty * n + yEmpty] = board[index];
        bd.board[index] = 0;
        bd.xEmpty = xcord;
        bd.yEmpty = ycord;
        return bd;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ResizingArrayStack<Board> items = new ResizingArrayStack<>();
        if (isIndexValid(xEmpty + 1))
            items.push(getNeighbor(xEmpty + 1, yEmpty));
        if (isIndexValid(xEmpty - 1))
            items.push(getNeighbor(xEmpty - 1, yEmpty));
        if (isIndexValid(yEmpty - 1))
            items.push(getNeighbor(xEmpty, yEmpty - 1));
        if (isIndexValid(yEmpty + 1))
            items.push(getNeighbor(xEmpty, yEmpty + 1));
        return items;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // finding first two different from empty space;
        int i  = 0;
        int firstIndex, secondIndex;

        while (isEmptyTile(board[i])) ++i;
        firstIndex = i;

        ++i;
        while (isEmptyTile(board[i])) ++i;
        secondIndex = i;

        Board retVal = getCopyBoard(this);
        int temp = retVal.board[firstIndex];
        retVal.board[firstIndex] = retVal.board[secondIndex];
        retVal.board[secondIndex] = temp;
        return retVal;
    }


    public static void main(String[] args) {
        int[][] tales = new int[][]{{8, 1, 3},
                                    {4, 0, 2},
                                    {7, 6, 5}};

        for (int i = 0; i < tales.length; i++) {
            for (int j = 0; j < tales.length; j++) {
                System.out.print(tales[i][j] + " ");
            }
            System.out.println();
        }

        Board bd = new Board(tales);
        System.out.println(bd.isGoal());

        System.out.println(bd.xEmpty);
        System.out.println(bd.yEmpty);
        System.out.println(bd.dimension());
        System.out.println(bd.hamming());
        System.out.println(bd.manhattan());

        for (Board b: bd.neighbors()) {
            System.out.println(b);
            System.out.println("-----");
        }

    }
}
