import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

class FileHandler {
    public static Optional<String> getContent(String path) throws IOException {
	try (BufferedReader file = new BufferedReader(new FileReader(path))) {
	    return Optional.of(file.lines().collect(Collectors.joining("\n")));
	} catch (Exception e) {
	    return Optional.empty();
	}
    }
}