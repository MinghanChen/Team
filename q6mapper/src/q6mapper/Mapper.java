package q6mapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Mapper {
	
	public static void main(String[] args) throws IOException, ParseException, java.text.ParseException{
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out,"UTF-8"),true);
		//File file = new File("cc_sample");
		BufferedReader bufferedreader = new BufferedReader(
				new InputStreamReader(System.in));
		//BufferedReader bufferedreader = new BufferedReader(new
				//FileReader(file));
		StringBuffer content = new StringBuffer();
		// File afterextraction = new File("afterextraction");
		// FileWriter fileWritter = new FileWriter(afterextraction,true);
		 //BufferedWriter bufferedWriter = new BufferedWriter(fileWritter);
		
		while (true) {
			String inputLine;
			if ((inputLine = bufferedreader.readLine()) != null) {
				if (!inputLine.equals("")) {
					content.append(inputLine);
					String content_to_string = content.toString();
					JSONParser parser = new JSONParser();
					Object obj;
					obj = parser.parse(content_to_string);
					JSONObject first_layer = (JSONObject) obj;


					String tweet_id = first_layer.get("id").toString();
					JSONObject user = (JSONObject) first_layer.get("user");
					String user_id = (String) user.get("id").toString();

					//get entities -- media[] -- type:photo
					JSONObject entities = (JSONObject) first_layer.get("entities");
					JSONArray media = (JSONArray) entities.get("media");
					if(media!=null){
						JSONObject medium=null;
						String type=null;
						int count=0;
						boolean flag = false;
						if(media.size()!=0){
							for(int i=0;i<media.size();i++){
								medium = (JSONObject) media.get(i);
								type = (String) medium.get("type");
								if(type.equals("photo")){
									count++;
									flag = true;
								}
							}
							if(flag==true){
								user_id = ("000000000000000"+user_id).substring(user_id.length());
								String result = user_id + "\t" + count + ",,," + tweet_id;
								pw.write(result+"\n");
								pw.flush();
							}
						}
					}
					content.delete(0, content.length());
				}
			} else
				break;
		}
		pw.close();
		bufferedreader.close();

		
	}


}
