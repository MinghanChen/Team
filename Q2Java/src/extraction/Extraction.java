package extraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Extraction {
	// create sentiment words map

	private static final char CONTROL_LIMIT = ' ';
	private static final char PRINTABLE_LIMIT = '\u007e';
	private static final char[] HEX_DIGITS = new char[] { '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static Map<String, Integer> createSMap() throws IOException {
		String urlString = "https://s3.amazonaws.com/F14CloudTwitterData/AFINN.txt";
		URL url = new URL(urlString);
		InputStream in = url.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		// InputStream in = Extraction.class.getResourceAsStream("/AFINN.txt");
		// BufferedReader br = new BufferedReader(new InputStreamReader(in));
		// File sentiment = new File("/AFINN.txt");
		// BufferedReader br = new BufferedReader(new FileReader(sentiment));
		String input;
		String word = null;
		int score = 0;
		Map<String, Integer> sMap = new HashMap<String, Integer>();

		while ((input = br.readLine()) != null) {
			String[] parts = input.split("\t");
			word = parts[0];
			score = Integer.parseInt(parts[1]);
			sMap.put(word, score);
		}
		br.close();
		return sMap;
	}

	public static String unRot13(String input) {
		String output = "";
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c >= 'a' && c <= 'm')
				c += 13;
			else if (c >= 'A' && c <= 'M')
				c += 13;
			else if (c >= 'n' && c <= 'z')
				c -= 13;
			else if (c >= 'N' && c <= 'Z')
				c -= 13;
			output += c;
		}
		return output;
	}

	// create censor words set
	public static HashSet<String> createCSet() throws Exception {
		HashSet<String> cSet = new HashSet<String>();
		String urlString = "https://s3.amazonaws.com/F14CloudTwitterData/banned.txt";
		URL url = new URL(urlString);
		InputStream in = url.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		// InputStream in = Extraction.class.getResourceAsStream("/banned.txt");
		// BufferedReader br = new BufferedReader(new InputStreamReader(in));
		// File censor =new File("/banned.txt");
		// BufferedReader br = new BufferedReader(new FileReader(censor));
		String rotWord = null;
		String word = null;

		while ((rotWord = br.readLine()) != null) {
			word = unRot13(rotWord);
			cSet.add(word);
		}
		br.close();
		return cSet;
	}

	// process text string(score and censor)
	// @param: string to be processed, sentiment words map, censor words set
	// @return: object ScoreText with fields score and text
	public static ScoreText processText(String text, Map sMap, HashSet cSet)
			throws Exception {
		int score = 0;
		char first, last;
		int number;
		String content, replaceContent, lowerWord;

		//lowerText = text.toLowerCase();
		String[] words = text.split("[^a-zA-Z0-9]+");
		for (int i = 0; i < words.length; i++) {
			//System.out.println(words[i]);//
			lowerWord = words[i].toLowerCase();
			// calculate score
			if (sMap.containsKey(lowerWord)) {
				score += (int) sMap.get(lowerWord);
			}
			// censor words
			if (cSet.contains(lowerWord)) {
				first = lowerWord.charAt(0);
				last = lowerWord.charAt(lowerWord.length() - 1);
				content = lowerWord.substring(1, lowerWord.length() - 1);
				number = lowerWord.length() - 2;
				char[] chars = new char[number];
				Arrays.fill(chars, '*');
				replaceContent = new String(chars);
				text = text.replaceAll("(?i)(?<=" + "[^a-zA-Z0-9]" + first + ")" + content
                        + "(?=" + last + "[^a-zA-Z0-9]" + ")", replaceContent);
                if(i == 0)
                    text = text.replaceAll("(?i)(?<=" + "^" + first + ")" + content
                            + "(?=" + last + "[^a-zA-Z0-9]" + ")", replaceContent);
                if(i == (words.length - 1))
                    text = text.replaceAll("(?i)(?<=" + "[^a-zA-Z0-9]" + first + ")" + content
                            + "(?=" + last + "$" + ")", replaceContent);
                if(i == (words.length - 1) && i == 0)
                    text = text.replaceAll("(?i)(?<=" + "^" + first + ")" + content
                            + "(?=" + last + "$" + ")", replaceContent);
			}

		}
		// System.out.println("the final score is: "+score);
		// System.out.println("the final text is: "+text);
		return new ScoreText(score, text);
	}

	public static void main(String args[]) throws Exception {
		Map sMap = new HashMap();
		sMap = createSMap();
		/*******/
		HashSet cSet = new HashSet();
		cSet = createCSet();

		//File file = new File("cc_sample");
		BufferedReader bufferedreader = new BufferedReader(
				new InputStreamReader(System.in));
		//BufferedReader bufferedreader = new BufferedReader(new
			//	FileReader(file));
		StringBuffer content = new StringBuffer();
		 //File afterextraction = new File("afterextraction");
		// FileWriter fileWritter = new FileWriter(afterextraction,true);
		 //BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
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
					//System.out.println(time);
					String[] str = time.split(" ");
//					System.out.println(str.length);
//					System.out.println("Str0: "+str[0]);
//					System.out.println("Str1: "+str[1]);
//					System.out.println("Str2: "+str[2]);
//					System.out.println("Str3: "+str[3]);
//					System.out.println("Str4: "+str[4]);
//					System.out.println("Str5: "+str[5]);
					SimpleDateFormat sdf1 = new SimpleDateFormat(
							"EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
					Date d = sdf1.parse(time);
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf2.format(d);
					//System.out.println(date);
					//System.out.println(str[3]);
					String final_time = date + "+" + str[3];
					//System.out.println(final_time);
					final_time = final_time.replaceAll("[-+:]", "");
					//System.out.println("final time is : "+final_time);
					
					// System.out.println(time);
					String tweet_id = first_layer.get("id").toString();
					// System.out.println(tweet_id);
					String text = first_layer.get("text").toString();

					JSONObject user = (JSONObject) first_layer.get("user");
					String user_id = user.get("id").toString();
					// System.out.println(user_id);
					content.delete(0, content.length());
					/************ jian *******************************************/
					ScoreText scoreText = processText(text, sMap, cSet);
					int score = scoreText.getScore();
					text = scoreText.getText();
					// the two lines below show the processed text and score of
					// the text
					// System.out.println("the returned score is: "+score);
					// System.out.println("the returned text is: "+text);

					/************ jian *****************************************/
					//System.out.println("user_id is : "+user_id);
					String result = final_time +" ass " +  user_id + " ass "
							+ toPrintableRepresentation(text) + " ass " + tweet_id + " ass " + score;
					// System.out.print(result.format(result, "UTF-8"));
					//String result = final_time+","+tweet_id+","+text+","+score+","+user_id;
					System.out.println(result);
					 //bufferWritter.write(result);
				}
			} else
				break;
		}
		bufferedreader.close();
		//bufferWritter.close();
		System.exit(0);
	}

	public static String toPrintableRepresentation(String source) {

		if (source == null)
			return null;
		else {

			final StringBuilder sb = new StringBuilder();
			final int limit = source.length();
			char[] hexbuf = null;

			int pointer = 0;

			sb.append('"');

			while (pointer < limit) {

				int ch = source.charAt(pointer++);

				switch (ch) {

				case '\0':
					sb.append("\\0");
					break;
				case '\t':
					sb.append("\\t");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\"':
					sb.append("\\\"");
					break;
				case '\\':
					sb.append("\\\\");
					break;

				default:
					if (CONTROL_LIMIT <= ch && ch <= PRINTABLE_LIMIT)
						sb.append((char) ch);
					else {

						sb.append("\\u");

						if (hexbuf == null)
							hexbuf = new char[4];

						for (int offs = 4; offs > 0;) {

							hexbuf[--offs] = HEX_DIGITS[ch & 0xf];
							ch >>>= 4;
						}

						sb.append(hexbuf, 0, 4);
					}
				}
			}

			return sb.append('"').toString();
		}
	}
}

class ScoreText {
	public final int score;
	public final String text;

	public ScoreText(int score, String text) {
		this.score = score;
		this.text = text;
	}

	public int getScore() {
		return score;
	}

	public String getText() {
		return text;
	}

}
