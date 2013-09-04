package percolation;

public class Percolation {
	
	private int N;
	private WeightedQuickUnionUF u;
	private boolean[] openSite;
	private static final int LEFT = 0;
	private static final int RIGHT = 1;
	private static final int TOP = 2;
	private static final int DOWN = 3;
	
	// create N-by-N grid, with all sites blocked
	public Percolation(int N) {
		this.N = N;
		u = new WeightedQuickUnionUF(N*N);
		this.openSite = new boolean[N*N];
		for (int i = 0; i < N*N; i++)
			this.openSite[i] = false;
	}
	   
	// open site (row i, column j) if it is not already
	public void open(int i, int j)  {
		if (!validateIndexes(i,j)) return;
		openSite[getGrid(i,j)] = true;
		for (int direction = LEFT; direction <= DOWN; direction++)
			check(i,j, direction);
		
	}
	   
	   // is site (row i, column j) open?
	public boolean isOpen(int i, int j)  {
	   if (!validateIndexes(i,j)) return false;
	   return openSite[getGrid(i,j)];
	}
	   
	   // is site (row i, column j) full?
	public boolean isFull(int i, int j)   {
		
		int grid = getGrid(i,j);
		for (int k = 0; k < N; k++) {
			if(openSite[k]) {
				if (u.connected(k, grid))
					return true;
			}
		}
		return false;
	}
	   
	   // does the system percolate?
	public boolean percolates() {
	  for (int i = 1; i <= N; i++) {
		  int p = getGrid(N,i);
		  if (openSite[p]) {
			  for (int j = 1; j <= N; j++) {
				  int q = getGrid(1,j);
				  if (openSite[q]) {
					  if (u.connected(p, q)) return true;
				  }
			  }
		  }
	  }
	  return false;
	}
	
	// validate indexes
	private boolean validateIndexes(int i, int j) {
		if (i <= 0 || j <= 0 || i > N || j > N) return false;
		return true;
	}
	
	private int getGrid(int i, int j) {
		return (i-1) * N + (j-1);
	}
	
	private void check(int i, int j, int direction) {
		int p = 0, q = 0;
		switch (direction) {
		case LEFT:
			p = i; q = j-1; break;
		case RIGHT:
			p = i; q = j+1; break;
		case TOP:
			p = i-1; q = j; break;
		case DOWN:
			p = i+1; q = j; break;
		}
		if (!validateIndexes(p,q)) return;
		merge(i,j,p,q);
	}
	
	private void merge(int i, int j, int p, int q) {
		u.union(getGrid(i,j), getGrid(p,q));
	}
}
