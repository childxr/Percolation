package percolation;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;

public class In {
	
	private ArrayList<Integer> in = new ArrayList<Integer> ();
	private int curr = 0;
	
	public In(String filename) {
		try {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = "";
		String tmp = "";
		while ((tmp = br.readLine()) != null) {
			line += tmp;
		}
		String[] nums = line.split("\\s+");
		for (String num : nums) {
			in.add(Integer.parseInt(num));
		}
		br.close();
		//for (int i = 0; i < 8; i++) System.out.println(" "+in.get(i));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public int readInt() {
		if (curr < in.size()) {
			curr++;
			return in.get(curr-1);
		}
		else return -1;
	}
	
	public boolean isEmpty() {
		return (curr == in.size())? true : false;
	}

}
