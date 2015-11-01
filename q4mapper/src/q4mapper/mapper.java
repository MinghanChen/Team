package q4mapper;

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

public class mapper {
	
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
					/* String time = first_layer.get("created_at").toString(); */
					String time = first_layer.get("created_at").toString();
					String[] str = time.split(" ");
					SimpleDateFormat sdf1 = new SimpleDateFormat(
							"EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
					Date d = sdf1.parse(time);
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf2.format(d);
					//String final_time = date + "+" + str[3];
					String final_time = date;
					// System.out.println(time);
					String tweet_id = first_layer.get("id").toString();
					// System.out.println(tweet_id);
					//String text = first_layer.get("text").toString();
					
					//find location
					String location = null;
					JSONObject place = (JSONObject) first_layer.get("place");
					boolean flag = false;
					if(place != null){
						String placeName = (String) place.get("name");
						if( placeName != null && !placeName.equals("")){
							location = placeName;
							flag = true;
						}
					}
					if(flag == false){
						JSONObject user = (JSONObject) first_layer.get("user");
						String timeZone = (String) user.get("time_zone");
						if(timeZone != null){
							String regex = "(?i)\\btime\\b";
							Pattern pattern = Pattern.compile(regex);
							Matcher m = pattern.matcher(timeZone);
							if(!m.find()){
								location = timeZone;
							}
							else{
								content.delete(0, content.length());
								continue;
							}
						}else{
							content.delete(0, content.length());
							continue;
						}
					}
					

					//find hashtags	
					JSONObject entities = (JSONObject) first_layer.get("entities");
					JSONArray hashtags = (JSONArray) entities.get("hashtags");
					if(hashtags.size() != 0){
						String tagText;
						String tagPlace;
						String result = null;
						JSONObject hashtag;
						HashSet<String> tagSet = new HashSet<String>();
						for(int i=0;i<hashtags.size();i++){
							hashtag = (JSONObject) hashtags.get(i);
							tagText = (String) hashtag.get("text");
							tweet_id = ("00000000000000000000"+tweet_id).substring(tweet_id.length());
							tagPlace = String.format("%02d",i);
							
							if(tagSet.contains(tagText)){continue;}
							else{
								tagSet.add(tagText);
								result = final_time + location + ",,,"+ tagText + "\t" + tweet_id + tagPlace + "\n"; 
								pw.write(result);
								pw.flush();
							}
									//String result = String.format("%06d", middle);
						
							//bufferedWriter.write(result);
							//bufferedWriter.flush();
						}
						tagSet.clear();
					}
					else{
						content.delete(0, content.length());
						continue;
					}
					content.delete(0, content.length());
				}
			} else
				break;
		}
		pw.close();
		bufferedreader.close();
		//bufferedWriter.close();

		
	}


}
