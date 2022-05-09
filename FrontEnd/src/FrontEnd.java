
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FrontEnd {
    public static void main(String[] args) {


            JSONObject resetValue = new JSONObject();
            resetValue.put("input_string", "0");

            Runnable resetValve = () -> {
                try {
                    for (int i = 0; i < 3; i++) {
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(new URI("http://127.0.0.1:8000/n"))
                                .version(HttpClient.Version.HTTP_1_1)
                                .POST(HttpRequest.BodyPublishers.ofString(resetValue.toJSONString()))
                                .build();

                        HttpResponse<String> response = HttpClient.newBuilder()
                                .build()
                                .send(request, HttpResponse.BodyHandlers.ofString());

                        JSONParser parser = new JSONParser();
                        JSONObject json = (JSONObject) parser.parse(response.body());

                        System.out.println(json.get("massage"));

                        Thread.sleep(5000);
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            };

            Runnable getAndIncValue = () -> {
                try {
                    for (int i = 0; i < 50; i++) {
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(new URI("http://127.0.0.1:8000/n"))
                                .version(HttpClient.Version.HTTP_1_1)
                                .GET()
                                .build();

                        HttpResponse<String> response = HttpClient.newBuilder()
                                .build()
                                .send(request, HttpResponse.BodyHandlers.ofString());

                        JSONParser parser = new JSONParser();
                        JSONObject json = (JSONObject) parser.parse(response.body());

                        System.out.println(json.get("massage"));

                        Thread.sleep(500);
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            };

        Thread thread1 = new Thread(resetValve);
        Thread thread2 = new Thread(getAndIncValue);

        thread1.start();
        thread2.start();



    }
}
