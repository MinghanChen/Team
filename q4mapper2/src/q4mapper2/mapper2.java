package q4mapper2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class mapper2 {
	public static void main(String[] args) throws IOException{
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out,"UTF-8"));
		String line;
		while((line=br.readLine())!=null){
			line = line.trim();
			pw.write(line + "\n");
			pw.flush();
			
		}
		br.close();
		pw.close();
	}

}
