import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

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
	    
	    Arrays.stream(results).map(video -> video.toString()).forEach(System.out::println);
	    
	    System.out.print("> ");
	    
	    try (Scanner s = new Scanner(System.in)) {
		idx = Integer.parseInt(s.nextLine());
	    }
	}
	
	CommandCaller.shellCommand(String.format("mpv %s%s", Configuration.YT, results[idx].getVideoId()));
    }
}
