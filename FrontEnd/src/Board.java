import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Board extends JFrame {

    private GraphicsClass gc;

    private HttpClient httpClient;

    private HttpRequest set_user_direction_request;

    public Board() {
        /////////Communications//////////
        try {

            //creating the client
            this.httpClient = HttpClient.newBuilder().build();

            //hand shaking with the server
            HttpRequest hand_shake_request = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:8000/api/hello"))
                    .version(HttpClient.Version.HTTP_1_1)
                    .GET()
                    .build();

            //sending the response
            HttpResponse<String> hand_shake_response = httpClient.send(hand_shake_request, HttpResponse.BodyHandlers.ofString());

            //the returned json will contain ether a massage of success or failure
            if(!JsonParser.isResponseSuccessful(hand_shake_response)){
                System.exit(-1);
            }


        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        //////////End Communication section////////
        //after connection was successful we can go and creat a window:

        setBounds(5, 5, 1015, 565);

        this.gc = new GraphicsClass(this.httpClient);

        //adding the graphics class as a component to the frame.
        add(gc);

        //adding the listeners of the keyboard to the frame as a component too.
        addKeyListener(new TAdapter());
    }

    class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {

            JSONObject direction = new JSONObject();
            direction.put("function", "0");

            int d = 0;

            int keycode = e.getKeyCode();
            switch (keycode) {
                case KeyEvent.VK_LEFT -> {
                    d = 2;
                }
                case KeyEvent.VK_RIGHT -> {
                    d = 0;
                }
                case KeyEvent.VK_UP -> {
                    d = 3;
                }
                case KeyEvent.VK_DOWN -> {
                    d = 1;
                }
            }

            direction.put("param",d );

            try {
                set_user_direction_request =  HttpRequest.newBuilder()
                        .uri(new URI("http://127.0.0.1:8000/api/set_param"))
                        .version(HttpClient.Version.HTTP_1_1)
                        .PUT(HttpRequest.BodyPublishers.ofString(direction.toJSONString()))
                        .build();


                HttpResponse<String> response = httpClient.send(set_user_direction_request, HttpResponse.BodyHandlers.ofString());

                System.out.println(response.body());

            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }


        }
    }

    public static void main(String[] args) {
        Board b = new Board();
        b.setVisible(true);
    }

}


