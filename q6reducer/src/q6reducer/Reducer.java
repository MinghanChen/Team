package q6reducer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;

public class Reducer {
	public static void main(String[] args) throws IOException{
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out,"UTF-8"));
		String line;
		String result;
		String preUid=null;
		String uid = null;
		int count =0;
		HashSet<String> tidSet= new HashSet<String>();
		
		if((line=br.readLine())!=null){
			line = line.trim();
			String[] first1 = line.split("\t");
			String[] second1 = first1[1].split(",,,");
			String[] words1 = new String[3];
			words1[0] = first1[0];
			words1[1] = second1[0];
			words1[2] = second1[1];
			
			preUid = words1[0];
			tidSet.add(words1[2]);
			count = Integer.parseInt(words1[1]);
			
	
			while((line=br.readLine())!=null){
				line = line.trim();
				String[] first = line.split("\t");
				String[] second = first[1].split(",,,");
				String[] words = new String[3];
				words[0] = first[0];
				words[1] = second[0];
				words[2] = second[1];
				
				if(words[0].equals(preUid)){
					if(!tidSet.contains(words[2])){
						tidSet.add(words[2]);
						count = count + Integer.parseInt(words[1]);
					}
				}
				else{
					result = preUid+"\t"+count;
					pw.write(result + "\n");
					pw.flush();
					preUid = words[0];
					count = Integer.parseInt(words[1]);
					tidSet.clear();
					tidSet.add(words[2]);
				}
			}
			result = preUid + "\t" + count;
			pw.write(result+"\n");
			pw.flush();
		}
		br.close();
		pw.close();
	}

}
