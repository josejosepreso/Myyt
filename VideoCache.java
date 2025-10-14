import java.nio.file.*;
import java.io.*;
import java.util.List;
import java.util.stream.Stream;

class VideoCache {
		public static String getPathByName(final String videoId) throws IOException {
				File f = new File(Configuration.VIDEO_CACHE_PATH);
				if (!f.exists()) f.mkdir();

				try (Stream<Path> paths = Files.walk(Paths.get(Configuration.VIDEO_CACHE_PATH))) {
						final String cachedVideoPath = String.format("%s%s.mkv", Configuration.VIDEO_CACHE_PATH, videoId);

						List<Path> filesPaths = paths.filter(Files::isRegularFile)
								.filter(p -> p.toString().equals(cachedVideoPath))
								.toList();

						if (!filesPaths.isEmpty()) {
								return filesPaths.get(0).toString();
						}
				}

				return null;
		}
}
