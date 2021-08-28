import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private enum STATUS { BLOCKED, OPEN }
    private final WeightedQuickUnionUF wqunion;
    private final WeightedQuickUnionUF fullUnion; // used for checking whether is field full or not
    private STATUS[][] table;
    private int openSize;
    private final int size;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Index n is invalid, it should be greather than 0");
        table = new STATUS[n+1][n+1];
        this.size = n;
        wqunion = new WeightedQuickUnionUF(n * n + 2 + 1); // two additional virtual nodes for faster percolate checking
        fullUnion = new WeightedQuickUnionUF(n * n + 1 + 1); // dont'use last virtual node
        initBlockedTable();
    }

    private void initBlockedTable() {
        for (int i = 1; i <= size; ++i) {
            for (int j = 1; j <= size; ++j) {
                table[i][j] = STATUS.BLOCKED;
            }
        }
    }

    private void validate(int row, int col) {
        if (row > size || row <= 0 || col > size || col <= 0)
            throw new IllegalArgumentException("Invalid (row, col) indexes");
    }

    private int mapToArrayPos(int row, int col) {
        return (row-1) * size + col;
    }

    private void connectToNeighbor(int rowCurr, int colCurr, int rowOther, int colOther) {
        if (rowOther > size || rowOther <= 0 || colOther > size || colOther <= 0)
            return;
        if (isOpen(rowOther, colOther)) {
            wqunion.union(mapToArrayPos(rowCurr, colCurr), mapToArrayPos(rowOther, colOther));
            fullUnion.union(mapToArrayPos(rowCurr, colCurr), mapToArrayPos(rowOther, colOther));
        }
    }

    private void openAndCheckRow(int row, int col) {
        table[row][col] = STATUS.OPEN;
        this.openSize++;
        if (row == 1) {
            wqunion.union(mapToArrayPos(row, col), size * size + 1);
            fullUnion.union(mapToArrayPos(row, col), size * size + 1);
        } else if (row == size) {
            wqunion.union(mapToArrayPos(row, col), size * size + 2);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            openAndCheckRow(row, col);
            connectToNeighbor(row, col, row + 1, col);
            connectToNeighbor(row, col, row - 1, col);
            connectToNeighbor(row, col, row, col + 1);
            connectToNeighbor(row, col, row, col - 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return table[row][col] != STATUS.BLOCKED;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int find1 = wqunion.find(mapToArrayPos(row, col));
        int find2 = wqunion.find(size * size + 1);
        return find1 == find2;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSize;
    }

    // does the system percolate?
    public boolean percolates() {
        int find1 = wqunion.find(size * size + 1);
        int find2 = wqunion.find(size * size + 2);
        return find1 == find2;
    }

    // test client (optional)
    public static void main(String[] args) {
        System.out.println("proba");
    }
}