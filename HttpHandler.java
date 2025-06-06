import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

class HttpHandler {
    public static Optional<HttpResponse<String>> GETRequest(String url)
	throws IOException, URISyntaxException, InterruptedException
    {
	try {
	    HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
	    return Optional.of(HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()));
	} catch (Exception e) {
	    return Optional.empty();
	}
    }
}
