
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final WeightedQuickUnionUF uf;
    private boolean [][] system;
    private int open;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n <= 0) {
            throw new IllegalArgumentException("n > 0");
        }
        this.n = n;
        this.system = new boolean[this.n][this.n];
        this.open = 0;
        this.uf = new WeightedQuickUnionUF(((this.n*this.n)+2));
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        assertIndexes(row-1, col-1);
        this.system[row-1][col-1] = true;
        this.connectNeighbours(row-1, col-1);
        this.open++;
        int curIndex = xyTo1D(row-1, col-1);
        if (row == 1 && !(this.uf.find(0) == this.uf.find(curIndex))) {
            uf.union(0, curIndex);
        }
        if (row == n && !(this.uf.find(0) == this.uf.find((n*n)+1))) {
            uf.union(curIndex, (n*n)+1);
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        this.assertIndexes(row-1, col-1);
        return (this.system[row-1][col-1]);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        this.assertIndexes(row-1, col-1);
        int index = xyTo1D(row-1, col-1);
        return (this.uf.find(0) == this.uf.find(index));
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return this.open;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return (this.uf.find(0) == this.uf.find((n*n)+1));
    }

    private void assertIndexes(int row, int col) {
        if (row < 0 || col < 0 || row >= n || col >= n) {
            throw new  IllegalArgumentException("1 <= row <= n & 1 <= col <= n");
        }
    }
    private boolean verify(int posRow, int posColumn)
    {
        return ((posRow >= 0 && posRow < this.n) || (posColumn >= 0 && posColumn < this.n));

    }
    private int xyTo1D(int posRow, int posColumn)
    {
        int resp = -1;
        if (verify(posRow, posColumn))
        {
            resp = (posRow*n)+(posColumn+1);
        }

        return resp;

    }

    private void connectNeighbours(int row, int col) {
        int rowUp = row - 1;
        int rowDown = row + 1;
        int colLeft = col - 1;
        int colRight = col + 1;
        int curIndex = xyTo1D(row, col);
        boolean band = false, bandRoot = false;

        //  uf.union(0, curIndex);
        if (rowUp >= 0) {
            int index = xyTo1D(rowUp, col);
            if (this.system[rowUp][col]) {
                bandRoot = this.isFull(rowUp+1,col+1);
                band = this.isFull(row+1,col+1) || band ;
                if (!(bandRoot && band)) // falso en cualquier caso
                {
                    if (band) // Si el actual esta aputando a 0
                    {
                        this.uf.union(curIndex, index);
                    }
                    else // en cualquier otro caso que apunte al vecino
                    {
                            this.uf.union(index, curIndex);
                    }
                }
            }
        }

        if (rowDown < n) {
            int index = xyTo1D(rowDown, col);
            if (this.system[rowDown][col]) {
                bandRoot = this.isFull(rowDown+1,col+1);
                band = this.isFull(row+1,col+1) || band;
                if (!(bandRoot && band)) // falso en cualquier caso
                {
                    if (band) // Si el actual esta aputando a 0
                    {
                        this.uf.union(curIndex, index);
                    }
                    else // en cualquier otro caso que apunte al vecino
                    {
                        this.uf.union(index, curIndex);
                    }
                }
            }
        }

        if (colLeft >= 0) {
            int index = xyTo1D(row, colLeft);
            if (this.system[row][colLeft]) {
                bandRoot = this.isFull(row+1,colLeft+1);
                band = this.isFull(row+1,col+1) || band;
                if (!(bandRoot && band)) // falso en cualquier caso
                {
                    if (band) // Si el actual esta aputando a 0
                    {
                        this.uf.union(curIndex, index);
                    }
                    else // en cualquier otro caso que apunte al vecino
                    {
                        this.uf.union(index, curIndex);
                    }
                }
            }
        }

        if (colRight < n) {
            int index = xyTo1D(row, colRight);
            if (this.system[row][colRight]) {
                bandRoot = this.isFull(row+1,colRight+1);
                band = this.isFull(row+1,col+1) || band;
                if (!(bandRoot && band)) // falso en cualquier caso
                {
                    if (band) // Si el actual esta aputando a 0
                    {
                        this.uf.union(curIndex, index);
                    }
                    else // en cualquier otro caso que apunte al vecino
                    {
                        this.uf.union(index, curIndex);
                    }
                }
            }
        }

    }
    // test client (optional)
    public static void main(String[] args)
    {
        Percolation bloque = new Percolation(10);
        System.out.println(bloque.isOpen(11, 5));
    }
}