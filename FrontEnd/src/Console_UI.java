import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Console_UI {

    private static void printResponse(HttpResponse<String> response) {

        //System.out.println(response.body());

        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.body());
            System.out.println(json.toJSONString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws URISyntaxException {

        try {

            HttpClient hc1 = HttpClient.newBuilder().build();
            HttpClient hc2 = HttpClient.newBuilder().build();
            HttpClient hc3 = HttpClient.newBuilder().build();


            HttpRequest hand_shake_request = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:8000/api/hello"))
                    .version(HttpClient.Version.HTTP_1_1)
                    .GET()
                    .build();

            HttpRequest get_state_request = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:8000/api/state"))
                    .version(HttpClient.Version.HTTP_1_1)
                    .GET()
                    .build();

            JSONObject direction = new JSONObject();
            direction.put("param", "3");
            direction.put("function", "0");

            HttpRequest set_user_direction_request = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:8000/api/set_param"))
                    .version(HttpClient.Version.HTTP_1_1)
                    .PUT(HttpRequest.BodyPublishers.ofString(direction.toJSONString()))
                    .build();

            HttpResponse<String> hand_shake_response1 = hc1.send(hand_shake_request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> hand_shake_response2 = hc2.send(hand_shake_request, HttpResponse.BodyHandlers.ofString());

            //ERRORS//
            HttpResponse<String> hand_shake_response3 = hc2.send(hand_shake_request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> hand_shake_response4 = hc3.send(hand_shake_request, HttpResponse.BodyHandlers.ofString());

            printResponse(hand_shake_response1);
            printResponse(hand_shake_response2);
            printResponse(hand_shake_response3);
            printResponse(hand_shake_response4);

            HttpResponse<String> response;

            for (int i = 0; i < 100; i++) {

                if (i == 10) {
                    response = hc1.send(set_user_direction_request, HttpResponse.BodyHandlers.ofString());

                    printResponse(response);
                }

                if (i == 5) {
                    response = hc2.send(set_user_direction_request, HttpResponse.BodyHandlers.ofString());

                    printResponse(response);
                }

                response = hc1.send(get_state_request, HttpResponse.BodyHandlers.ofString());

                printResponse(response);

                Thread.sleep(500);

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
