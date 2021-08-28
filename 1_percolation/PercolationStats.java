import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] results;
    private final int n;
    private final int trials;
    private final double confidence95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("N and Trials must be positive!");
        this.n = n;
        this.trials = trials;
        results = new double[trials];
        startExecution();
    }

    private double tresholdForSinglePerc() {
        Percolation perc = new Percolation(this.n);
        do {
            int randomRow = StdRandom.uniform(1, n + 1);
            int randomCol = StdRandom.uniform(1, n + 1);
            if (!perc.isOpen(randomRow, randomCol))
                perc.open(randomRow, randomCol);
        } while (!perc.percolates());
        return (perc.numberOfOpenSites() * 1.0) / (n * n);
    }


    private void startExecution() {
        for (int i = 0; i < trials; ++i) {
            results[i] = tresholdForSinglePerc();
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - confidence95 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + confidence95 * stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trails);
        double mean = stats.mean();
        double stddev = stats.stddev();
        double low = stats.confidenceLo();
        double high = stats.confidenceHi();
        System.out.printf("%-25s= %.15f\n", "mean", mean);
        System.out.printf("%-25s= %.15f\n", "stddev", stddev);
        System.out.printf("%-25s= [%.15f, %.15f]\n", "95% confidence interval", low, high);

    }

}