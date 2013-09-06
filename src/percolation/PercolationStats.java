package percolation;

/*----------------------------------------------------------------
 *  Author:        RONG XIE
 *  Written:       9/5/2013
 *  Last updated:  9/5/2013
 *
 *  Compilation:   javac PercolationStats.java
 *  Execution:     java PercolationStats 100 50
 *  
 *  Purpose: show statics for Percolation Sim in 100 * 100 grid 
 *           for 50 trials
 *----------------------------------------------------------------*/

public class PercolationStats {
    private int T = 0;  // # of trials
    private double sum = 0;  // sum of all threshold
    private double sum2 = 0;  // square sum of all threshold
    
    /**
     * Constructor
     * @param N size of grid in 1d
     * @param T number of trials
     */
    public PercolationStats(int N, int T) {	
    	if (N < 1 || T < 1) throw new IllegalArgumentException("N or T are illegal");
        this.T = T;
        for (int i = 0; i < T; i++) {
            int count = 0;
            Percolation perc = new Percolation(N);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(N) + 1;
                int col = StdRandom.uniform(N) + 1;
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    count++;
                }
            }
            double x = ((double) count) / (N * N);
            sum += x;
            sum2 += x * x;
        }
    }
    
    /**
     * compute the mean of current simulation
     * @return mean of current simulation
     */
    public double mean() {
        return sum / T;
    }
    
    /**
     * compute the standard deviation of percolation threshold
     * @return stddev for current simulation
     */
    public double stddev() {
        if (T == 1)
            return Double.NaN;
        double mean = mean();
        return Math.sqrt((sum2 + T * mean * mean - 2 * mean * sum) / (T - 1));
    }
    
    /**
     * compute the confidence low bound
     * @return lower bound of the 95% confidence interval
     */
    public double confidenceLo() {
        double mean = mean();
        double stddev = stddev();
        return mean - 1.96 * stddev / Math.sqrt(T);
    }

    /**
     * compute the confidence high bound
     * @return upper bound of the 95% confidence interval
     */
    public double confidenceHi() {
        double mean = mean();
        double stddev = stddev();
        return mean + 1.96 * stddev / Math.sqrt(T);
    }
    
    /**
     * test client
     * @param args
     */
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() 
                + ", " + stats.confidenceHi());
    }
}
