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

				final String cachedVideoPath = VideoCache.getPathByName(String.join("_", args)).orElse(Configuration.NOT_FOUND);

				if (!cachedVideoPath.equals(Configuration.NOT_FOUND)) {
						CommandCaller.shellCommand(String.format("mpv %s", cachedVideoPath));
						return;
				}

				final String query = Arrays.stream(args)
						.collect(Collectors.joining("+"))
						.replace("-a+", "")
						.replace("-f+", "");

				Video[] results = YouTubeSearcher.search(query);

				int idx = 0;

				if (!args[0].equals("-a")) {
						System.out.println("Select a video:");

						Arrays.stream(results)
								.filter(video -> video != null)
								.map(Video::toString)
								.forEach(System.out::println);

						System.out.print("> ");

						try (Scanner s = new Scanner(System.in)) {
								idx = Integer.parseInt(s.nextLine());
						}

						if (args[0].equals("-f")) {
								CommandCaller.shellCommand(String.format("yt-dlp -F \"%s%s\"", Configuration.YT, results[idx].getVideoId()));
								return;
						}
				}

				CommandCaller.shellCommand(String.format("mpv --stream-record=%s%s.mkv %s%s", Configuration.VIDEO_CACHE_PATH, String.join("_", args), Configuration.YT, results[idx].getVideoId()));
		}
}
