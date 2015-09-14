import java.io.*;

public class MatrixWrite {
	
	static int rows;
	static int cols;
	static int startnum;

	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("Enter rows and columns for matrix");
			System.exit(0);
		}
		try {	
			rows = Integer.parseInt(args[0]);
			cols = Integer.parseInt(args[1]);
			startnum = Integer.parseInt(args[2]);
		} catch(NumberFormatException e) {
			e.printStackTrace();
			System.exit(0);
		}
		String filename = "m" + rows + "x" + cols + "s" + startnum + ".txt";
		try {
			File fout = new File(filename);
			FileOutputStream fos = new FileOutputStream(fout);
	 
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 		bw.write(Integer.toString(rows));
	 		bw.write(" ");
	 		bw.write(Integer.toString(cols));
	 		bw.newLine();
			for (int i = 0; i < rows; i++){
				for(int j = 0; j < cols; j++) {
					bw.write(Integer.toString((startnum+i+j)%8));
					bw.write(" ");
				}
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}