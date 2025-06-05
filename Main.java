import java.util.stream.*;
import java.util.regex.*;
import java.util.*;
import java.net.URI;
import java.net.http.*;
import java.net.*;
import java.io.*;

class Configuration {
    public static final int N_RESULTS = 20;
    public static final String YT_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=";
    public static final String YT_URL_1 = String.format("&type=video&maxResults=%d&key=", N_RESULTS);
    public static final String YT_KEY_PATH = "/home/jose/api/youtube";
    public static final String YT = "https://youtu.be/";
    public static final String VIDEO_INFO_REGEX = ".*videoId.*|.*title.*|.*channelTitle.*|.*publishTime.*";
    public static final String NIGHTZ = "BjJ_fH4uzRU";
}

class FileHandler {
    public static Optional<String> getContent(String path) throws IOException {
	try (BufferedReader file = new BufferedReader(new FileReader(path))) {
	    return Optional.of(file.lines().collect(Collectors.joining("\n")));
	} catch (Exception e) {
	    return Optional.empty();
	}
    }
}

class HttpHandler {
    public static Optional<HttpResponse> GETRequest(String url) throws IOException, URISyntaxException, InterruptedException {
	try {
	    HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
	    return Optional.of(HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()));
	} catch (Exception e) {
	    return Optional.empty();
	}
    }
}

class Video {
    private int id;
    private String videoId;
    private String title;
    private String channel;
    private String date;

    public Video(int id, String videoId, String title, String channel, String date) {
	this.id = id;
	this.videoId = videoId;
	this.title = title;
	this.channel = channel;
	this.date = date;
    }

    public String getVideoId() {
	return this.videoId;
    }

    public String toString() {
	return String.format("%d: %s (%s, %s)", this.id, this.title, this.channel, this.date);
    }
}

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

class CommandCaller {
    public static void shellCommand(String command) throws IOException {	
	try {
	    Process p = (new ProcessBuilder("sh", "-c", command)).start();
	    (new BufferedReader(new InputStreamReader(p.getInputStream()))).lines().forEach(System.out::println);
	} catch (Exception e) {
	    e.printStackTrace();
	}	
    }
}

class Main {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {		
	if (args.length == 0) {
	    CommandCaller.shellCommand(String.format("mpv %s%s", Configuration.YT, Configuration.NIGHTZ));
	    return;
	}

	Video[] results = YouTubeSearcher.search(Arrays.stream(args).collect(Collectors.joining("+")).replace("-a+", ""));
	int idx = 0;

	if (!args[0].equals("-a")) {
	    System.out.println("Select a video:");
	    
	    Arrays.stream(results)
		.map(video -> video.toString())
		.forEach(System.out::println);

	    System.out.print("> ");
	    idx = Integer.parseInt((new Scanner(System.in)).nextLine());
	}
	
	CommandCaller.shellCommand(String.format("mpv %s%s", Configuration.YT, results[idx].getVideoId()));
    }
}
