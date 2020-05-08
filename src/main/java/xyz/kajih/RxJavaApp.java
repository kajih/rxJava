package xyz.kajih;

import io.reactivex.rxjava3.core.Observable;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RxJavaApp {

  public static final String lnk = "https://kajih.xyz/text/line%d.txt";
  static final HttpClient client = HttpClient.newHttpClient();

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    HttpRequest request = getHTTPRequest(String.format(lnk, 1));

    var future = getStringHTTPFuture(request);


    // Blocking Async
    System.out.printf(future.get());

    // Thread Joined
    getStringHTTPFuture(request)
      .thenAccept(System.out::println)
      .join();

    var rx = Observable.fromFuture(getStringHTTPFuture(request));

    rx.subscribe(
      s -> System.out.println(s),
      e -> System.out.println(e.getMessage())
    );

    System.out.println("-Exit");
  }

  // Create an immutable HTTP Request
  private static HttpRequest getHTTPRequest(String lnk) {
    return HttpRequest.newBuilder()
      .uri(URI.create(lnk))
      .build();
  }

  // HTTP Call a Request
  private static CompletableFuture<String> getStringHTTPFuture(HttpRequest request) {
    return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
      .thenApply(HttpResponse::body);
  }
}
