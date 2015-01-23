import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Collections;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JSON {
	
	private ArrayList<Video> videos = new ArrayList<Video>();
	private ArrayList<Integer> viewCounts = new ArrayList<Integer>();
	
	public JSON()
	{
		try {
			URL website = new URL("http://gdata.youtube.com/feeds/api/standardfeeds/most_popular?v=2&alt=json");
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream("lab14.json");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		
			FileReader reader = new FileReader("lab14.json");
			JSONParser parser = new JSONParser();
			JSONObject file = (JSONObject) parser.parse(reader);
			
			JSONObject feed = (JSONObject) file.get("feed");
			JSONArray entries = (JSONArray) feed.get("entry");
			
			for(int i=0; i<entries.size(); i++)
			{
				JSONObject video = (JSONObject) entries.get(i);
				JSONObject title = (JSONObject) video.get("title");
				JSONObject stats = (JSONObject) video.get("yt$statistics");
				videos.add(new Video((String) title.get("$t"), (String) stats.get("viewCount")));
				viewCounts.add(Integer.parseInt((String) stats.get("viewCount")));
			}
			
			Collections.sort(viewCounts);
			
			int vidCount = 0;
			for(int i=viewCounts.size()-1; i>=0; i--)
			{
				for(int j=0; j<videos.size(); j++){
					if(viewCounts.get(i)==videos.get(j).viewCount)
					{
						System.out.println(videos.get(j).title + " has " + videos.get(j).viewCount + " views!");
						vidCount++;
						if(vidCount==10)
							break;
					}
				}
				if(vidCount==10)
					break;
			}
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void main(String [] args)
	{
		new JSON();
	}
	
	class Video {
		
		protected int viewCount;
		protected String title;
		
		public Video(String title, String viewCount)
		{
			this.viewCount = Integer.parseInt(viewCount);
			this.title = title;
		}
	}
}