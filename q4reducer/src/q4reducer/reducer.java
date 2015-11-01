package q4reducer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;

public class reducer {
	
	
	public static void main(String[] args) throws IOException{
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out,"UTF-8"),true);
		//File file = new File("afterSort");
		BufferedReader bufferedreader = new BufferedReader(
				new InputStreamReader(System.in,"UTF-8"));
		//BufferedReader bufferedreader = new BufferedReader(new
				//FileReader(file));
		// File reduceResult = new File("reduceResult");
		// FileWriter fileWritter = new FileWriter(reduceResult,true);
		 //BufferedWriter bufferedWriter = new BufferedWriter(fileWritter);
		String inputLine;
		String currentWord=null;
		String currentWord0=null;
		int count = 0;
		String result;
		String ids = null;
		String number = null;
		int reCount=0;
		String tid;
		String[] splitIds=null;
		String[] rids=null;
		HashSet<String> IdIndex = new HashSet<String>();

		
		while((inputLine = bufferedreader.readLine()) != null){
			inputLine = inputLine.trim();
			String outWords[] = inputLine.split("\t");
			String inWords[] = outWords[0].split(",,,");
			tid = outWords[1].substring(0, outWords[1].length()-2);
			String word = inWords[0] + inWords[1];
			number = outWords[1];
			
 			if(currentWord!=null&&currentWord.equals(word)){
 				if(IdIndex.contains(outWords[1])){
 					continue;
 				}
 				IdIndex.add(outWords[1]);
				count++;
				ids = ids + "," +outWords[1];
			}
			else{
				if(currentWord != null){
					reCount = 99999-count;
					splitIds = ids.split(":");
					rids = splitIds[1].split(",");
					Arrays.sort(rids);
					number = Integer.toString(reCount)+rids[0];
					rids[0] = rids[0].substring(0,rids[0].length()-2);
					rids[0] = rids[0].replaceFirst("^0*","");
					ids = splitIds[0] + ":" + rids[0];
					for(int i=1;i<rids.length;i++){
						rids[i]=rids[i].substring(0,rids[i].length()-2);
						rids[i]=rids[i].replaceFirst("^0*", "");
						ids = ids+","+rids[i];
					}
					result = currentWord0 + ",,," + number + "\t" + ids;
					pw.write(result + "\n");
					pw.flush();
				}
				ids = inWords[1] + ":" + outWords[1];
				count = 1;
				currentWord = word;
				currentWord0 = inWords[0];
				IdIndex.clear();
				IdIndex.add(outWords[1]);
			}
		}
		//for the last line, print unprinted results
		reCount = 99999-count;
		splitIds = ids.split(":");
		rids = splitIds[1].split(",");
		Arrays.sort(rids);
		number = Integer.toString(reCount)+rids[0];
		rids[0] = rids[0].substring(0,rids[0].length()-2);
		rids[0] = rids[0].replaceFirst("^0*","");
		ids = splitIds[0] + ":" + rids[0];
		for(int i=1;i<rids.length;i++){
			rids[i]=rids[i].substring(0,rids[i].length()-2);
			rids[i]=rids[i].replaceFirst("^0*", "");
			ids = ids+","+rids[i];
		}
		result = currentWord0 + ",,," + number + "\t" + ids;
		pw.write(result + "\n");
		pw.flush();
		pw.close();
		bufferedreader.close();
	}

}
