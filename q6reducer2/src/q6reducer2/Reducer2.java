package q6reducer2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;

public class Reducer2 {
	public static void main(String[] args) throws IOException{
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out,"UTF-8"));
		String line;
		String result;

		String userid = null;
		int count = 0;
		
		while((line=br.readLine())!=null){
			line = line.trim();
			String words[] = line.split("\t");
			userid = words[0].replaceFirst("^0*","");
			count += Integer.parseInt(words[1]);
			result = userid + "\t" + count;
			pw.write(result + "\n");
			pw.flush();
		}
		br.close();
		pw.close();
	}

}
