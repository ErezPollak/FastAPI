import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Board {

    public static void main(String[] args) throws URISyntaxException {

        try {

            HttpClient hc1 = HttpClient.newBuilder().build();
            HttpClient hc2 = HttpClient.newBuilder().build();

            JSONParser parser = new JSONParser();

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
            direction.put("new_direction" , "3");

            HttpRequest set_user_direction_request = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:8000/api/direction"))
                    .version(HttpClient.Version.HTTP_1_1)
                    .PUT(HttpRequest.BodyPublishers.ofString(direction.toJSONString()))
                    .build();

            HttpResponse<String> hand_shake_response1 = hc1.send(hand_shake_request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> hand_shake_response2 = hc2.send(hand_shake_request, HttpResponse.BodyHandlers.ofString());

            JSONObject hand_shake_json1 = (JSONObject) parser.parse(hand_shake_response1.body());
            JSONObject hand_shake_json2 = (JSONObject) parser.parse(hand_shake_response2.body());

            System.out.println(hand_shake_json1.toJSONString());
            System.out.println(hand_shake_json2.toJSONString());

            HttpResponse<String> response;

            for (int i = 0; i < 100; i++) {

                if(i == 10){
                    response = hc1.send(set_user_direction_request, HttpResponse.BodyHandlers.ofString());

                    JSONObject json = (JSONObject) parser.parse(response.body());

                    System.out.println(json.toJSONString());
                }

                if(i == 5){
                    response = hc2.send(set_user_direction_request, HttpResponse.BodyHandlers.ofString());

                    JSONObject json = (JSONObject) parser.parse(response.body());

                    System.out.println(json.toJSONString());
                }

                response = hc1.send(get_state_request, HttpResponse.BodyHandlers.ofString());
                response = hc2.send(get_state_request, HttpResponse.BodyHandlers.ofString());

                JSONObject json = (JSONObject) parser.parse(response.body());

                System.out.println(json.toJSONString());

                Thread.sleep(500);

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}


