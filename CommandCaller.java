import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class CommandCaller {
    public static void shellCommand(String command) throws IOException {
        Process p = (new ProcessBuilder("sh", "-c", command)).start();
	try (BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
	    b.lines().forEach(System.out::println);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
