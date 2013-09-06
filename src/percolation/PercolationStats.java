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
    private double[] x;  // record the threshold for T trials
    private int T = 0;  // # of trials
    
    /**
     * Constructor
     * @param N size of grid in 1d
     * @param T number of trials
     */
    public PercolationStats(int N, int T) {	
        if (N >= 1 && T >= 1) {
            this.T = T;
            x = new double[T];
        }
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
            x[i] = ((double) count) / (N * N);
        }
        System.out.println("mean                    = " + mean());
        System.out.println("stddev                  = " + stddev());
        System.out.println("95% confidence interval = " + confidenceLo()
				+ ", " + confidenceHi());
    }
    
    /**
     * compute the mean of current simulation
     * @return mean of current simulation
     */
    public double mean() {
        double sum = 0;
        for (int i = 0; i < T; i++) {
            sum += x[i];
        }
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
        double sum = 0;
        for (int i = 0; i < T; i++) {
            sum += (x[i] - mean) * (x[i] - mean);
        }
        double test = Math.sqrt(sum / (T - 1));
        System.out.println(test);
        System.out.println(sum / (T - 1));
        return sum / (T - 1);
    }
    
    /**
     * compute the confidence low bound
     * @return lower bound of the 95% confidence interval
     */
    public double confidenceLo() {
        double mean = mean();
        double stddev = stddev();
        return mean - 1.96 * Math.sqrt(stddev) / Math.sqrt(T);
    }

    /**
     * compute the confidence high bound
     * @return upper bound of the 95% confidence interval
     */
    public double confidenceHi() {
        double mean = mean();
        double stddev = stddev();
        double confHigh = mean + 1.96 * Math.sqrt(stddev) / Math.sqrt(T);
        return confHigh;
    }
    
    /**
     * test client
     * @param args
     */
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        if (N < 1 || T < 1) throw new IllegalArgumentException("N or T are illegal");
        PercolationStats stats = new PercolationStats(N, T);
    }
}
