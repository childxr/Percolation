package percolation;

public class PercolationStats {
	// perform T independent computational experiments on an N-by-N grid
	private double[] x;
	private int N = 0;
	private int T = 0;
	private int count = 0;
	private double mean = 0;
	private double stddev = 0;
	private double confLow = 0;
	private double confHigh = 0;
	
	   public PercolationStats(int N, int T)    {
		   if (N >= 1 && T >= 1) {
			   this.N = N;
			   this.T = T;
			   x = new double[T];
		   }
	   }
	   // sample mean of percolation threshold
	   public double mean()  {
		   double sum = 0;
		   for (int i = 0; i < T; i++) {
			   sum += x[i];
		   }
		   mean = sum/T;
		   return mean;
	   }
	   
	   // sample standard deviation of percolation threshold
	   public double stddev()  {
		   if (T == 1) return Double.NaN;
		   double sum = 0;
		   for (int i = 0; i < T; i++) {
			   sum += (x[i] - mean)*(x[i]-mean);
		   }
		   stddev = sum / (T-1);
		   return stddev;
	   }
	   
	   // returns lower bound of the 95% confidence interval
	   public double confidenceLo()  {
		   confLow = mean - 1.96*stddev/Math.sqrt(T);
		   return confLow;
	   }
	   
	   // returns upper bound of the 95% confidence interval
	   public double confidenceHi()   {
		   confHigh = mean + 1.96*stddev/Math.sqrt(T);
		   return confHigh;
	   }
	   
	   // test client, described below
	   public static void main(String[] args)   {
		   int N = Integer.parseInt(args[0]);
		   int T = Integer.parseInt(args[1]);
		   if (N < 1 || T < 1) return;
		   PercolationStats stats = new PercolationStats(N,T);
		   for (int i = 0; i < T; i++) {
			   Percolation perc = new Percolation(N);
			   
		   }

	   }
}
