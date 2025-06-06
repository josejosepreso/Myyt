import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.regex.Pattern;

class YouTubeSearcher {
    public static boolean matchesVideoInfo(String s) {
	return Pattern.matches(Configuration.VIDEO_INFO_REGEX, s);
    }
    
    public static Video[] search(String query) throws IOException, URISyntaxException, InterruptedException {
	String url = FileHandler.getContent(Configuration.YT_KEY_PATH)
	    .map(key -> Configuration.YT_URL + query + Configuration.YT_URL_1 + key)
	    .orElse("");
	
	Video[] videos = new Video[Configuration.N_RESULTS];
	
	HttpHandler.GETRequest(url).ifPresent(res -> {
		if (res.statusCode() == 200 && !res.body().equals("")) {		    
		    String[] results = Arrays.stream(((String) res.body()).split("\n"))
			.filter(YouTubeSearcher::matchesVideoInfo)
			.map(line -> line.trim().split(":")[1].trim().replace(",","").replace("\"",""))
			.toArray(String[]::new);
		    
		    for (int i = 0, j = 0; i < results.length; i += 4, j++) {
			videos[j] = new Video(j, results[i], results[i + 1], results[i + 2], results[i + 3]);
		    }
		}
	    });
	
	return videos;
    }
}
