package percolation;

/*----------------------------------------------------------------
 *  Author:        RONG XIE
 *  Written:       9/5/2013
 *  Last updated:  9/5/2013
 *
 *  Compilation:   javac Percolation.java
 *  Execution:     NONE
 *  
 *  Purpose:  provide API for Percolation Threshold Simulation
 *----------------------------------------------------------------*/

public class Percolation {
    private int N; // the number of each site
    private WeightedQuickUnionUF u;  // set data structure 
    private boolean[] openSite;  // array for indicating the opensite
    private static final int LEFT = 0; // left indicator
    private static final int RIGHT = 1;  // right indicator
    private static final int TOP = 2;  // top indicator
    private static final int DOWN = 3;  // down indicator
    
    /**
     * 
     * @param N size of the grid in 1d
     * Constructor
     */
    public Percolation(int N) {
        this.N = N;
        u = new WeightedQuickUnionUF(N * N);
        this.openSite = new boolean[N * N];
        for (int i = 0; i < N * N; i++)
            this.openSite[i] = false;
    }
	
    /**
     * open grid(i,j) if not opened
     * @param i row index
     * @param j column index
     * indicate the open status of specified grid
     */
    public void open(int i, int j)  {
        validateIndex(i, j);
        openSite[getGrid(i, j)] = true;
        for (int direction = LEFT; direction <= DOWN; direction++)
          check(i, j, direction);
    }
    
    /**
     * check grid (i,j) open status
     * @param i row index
     * @param j column index
     * @return true if grid (i,j) is open 
     */
    public boolean isOpen(int i, int j)  {
        validateIndex(i, j);
        return openSite[getGrid(i, j)];
    }
	
    /**
     * check grid (i,j) full status
     * @param i row index
     * @param j column index
     * @return true if grid (i,j) is full
     */
    public boolean isFull(int i, int j)   {
        validateIndex(i, j);
        int grid = getGrid(i, j);
        for (int k = 0; k < N; k++) {
            if(openSite[k]) {
                if (u.connected(k, grid))
                    return true;
            }
        }
        return false;
    }
    
    /**
     * check if the system percolate
     * @return true if the system percolate
     */
	public boolean percolates() {
	  for (int i = 1; i <= N; i++) {
		  int p = getGrid(N, i);
		  if (openSite[p]) {
			  for (int j = 1; j <= N; j++) {
				  int q = getGrid(1, j);
				  if (openSite[q]) {
					  if (u.connected(p, q)) return true;
				  }
			  }
		  }
	  }
	  return false;
	}
	
	/**
	 * validate indexes with throwing exception
	 * @param i row index
	 * @param j column index
	 * @return true if validation is positive
	 */
    private boolean validateIndex(int i, int j) {
        if (i <= 0 || j <= 0 || i > N || j > N) throw new IndexOutOfBoundsException("indexes i or j out of bounds");
        return true;
    }
	
    /**
     * validate indexes without throwing exception
     * @param i row index
     * @param j column index
     * @return true if validation is positive, false otherwise
     */
    private boolean validateIndexes(int i, int j) {
        if (i <= 0 || j <= 0 || i > N || j > N) { 
            return false;
        }
        return true;
    }
	
    /**
     * compute grid index in 1d
     * @param i row index
     * @param j column index
     * @return index of grid in 1d
     */
    private int getGrid(int i, int j) {
        return (i - 1) * N + (j - 1);
    }
	
    /**
     * check if neighbors are open and update connected parts
     * @param i row index
     * @param j column index
     * @param direction direction of neighbors
     */
    private void check(int i, int j, int direction) {
        int p = 0;
        int q = 0;
        switch (direction) {
        case LEFT: 
            p = i; 
            q = j - 1; 
            break;
        case RIGHT: 
            p = i; 
            q = j + 1; 
            break;
        case TOP: 
            p = i - 1; 
            q = j; 
            break;
        case DOWN: 
            p = i + 1; 
            q = j; 
            break;
        }
        if (!validateIndexes(p, q)) 
            return;
        if (openSite[getGrid(p, q)]) 
            merge(i, j, p, q);
    }
	
    /**
     * merge two grid together
     * @param i row index for grid 1
     * @param j column index for grid 1
     * @param p row index for grid 2
     * @param q column index for grid 2
     */
    private void merge(int i, int j, int p, int q) {
        u.union(getGrid(i, j), getGrid(p, q));
    }
}
