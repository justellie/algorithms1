
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

// Estimates percolation threshold for an N-by-N percolation system.
public class PercolationStats {
    private final int experimentTime; // T
    private final double[] sumAverage;   // this array is using for calculating the mean and stddev
    private static final double CONFIDENCE_95 = 1.96;
    // Perform T independent experiments (Monte Carlo simulations) on an
    // N-by-N grid.
    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        Percolation perc;
        int size = n;  // the size of the grid
        experimentTime = t;   // number of executions
        sumAverage = new double[experimentTime];

        // Creating experiment_time of perc
        // Open sites randomly
        for (int i = 0; i < experimentTime; i++) {
            perc = new Percolation(size);
            while (!perc.percolates()) {
                int x = StdRandom.uniform(0, size);
                int j = StdRandom.uniform(0, size);
                if (!perc.isOpen(x, j)) {
                    perc.open(x, j);
                }
            }
            sumAverage[i] = (double) perc.numberOfOpenSites() / (size * size);
        }
    }

    // Sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(sumAverage);
    }

    // Sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddev(sumAverage);
    }

    // Low endpoint of the 95% confidence interval.
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev()) / Math.sqrt(experimentTime);
    }

    // High endpoint of the 95% confidence interval.
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev()) / Math.sqrt(experimentTime);
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t);
        StdOut.printf("mean           = %f\n", stats.mean());
        StdOut.printf("stddev         = %f\n", stats.stddev());
        StdOut.printf("confidenceLow  = %f\n", stats.confidenceLo());
        StdOut.printf("confidenceHigh = %f\n", stats.confidenceHi());
    }
}