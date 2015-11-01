package q4reducer2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class reducer2 {
	public static void main(String[] args) throws IOException{
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out,"UTF-8"));
		String line;
		String result;
		
		if((line=br.readLine())!=null){
			line = line.trim();
			String outWordsf[]=line.split("\t");
			String inWordsf[] = outWordsf[0].split(",,,");
			String currentWord = inWordsf[0];
			int i = 1;
			result = inWordsf[0] + "\t" + outWordsf[1] + "\t" + Integer.toString(i);
			pw.write(result+"\n");
			pw.flush();
			
			while((line=br.readLine())!=null){
				line = line.trim();
				String outWords[]=line.split("\t");
				String inWords[] = outWords[0].split(",,,");
				if(inWords[0].equals(currentWord)){
					i++;
				}
				else{
					i = 1;
					currentWord = inWords[0];
				}
				result = inWords[0] + "\t" + outWords[1] + "\t" + Integer.toString(i);
				pw.write(result+"\n");
				pw.flush();
			}			
		}
		br.close();
		pw.close();
	}

}
