package percolation;


public class PercolationStats {
	// perform T independent computational experiments on an N-by-N grid
	private static double[] x;
	private int N = 0;
	private int T = 0;
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
		   confLow = mean - 1.96*Math.sqrt(stddev)/Math.sqrt(T);
		   return confLow;
	   }
	   
	   // returns upper bound of the 95% confidence interval
	   public double confidenceHi()   {
		   confHigh = mean + 1.96*Math.sqrt(stddev)/Math.sqrt(T);
		   return confHigh;
	   }
	   
	   // test client, described below
	   public static void main(String[] args)   {
		   int N = Integer.parseInt(args[0]);
		   int T = Integer.parseInt(args[1]);
		   if (N < 1 || T < 1) return;
		   PercolationStats stats = new PercolationStats(N,T);
		   for (int i = 0; i < T; i++) {
			   int count = 0;
			   Percolation perc = new Percolation(N);
			   while(!perc.percolates()) {
				   int row = StdRandom.uniform(stats.N)+1;
				   int col = StdRandom.uniform(stats.N)+1;
				   if (!perc.isOpen(row, col)) {
					   perc.open(row, col);
					   count++;
				   }
			   }
			   //System.out.println("count:"+count+",N*N:"+N*N);
			   x[i] = ((double)count)/(N*N);
			   //System.out.println(x[i]);
		   }
		   
		   System.out.println("mean                    = "+stats.mean());
		   System.out.println("stddev                  = "+stats.stddev());
		   System.out.println("95% confidence interval = "+stats.confidenceLo()+", "+stats.confidenceHi());
	   }
}
