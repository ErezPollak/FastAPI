import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Random;

public class GraphicsClass extends JPanel {

    private HttpClient httpClient;
    private HttpRequest get_state_request;
    private HttpResponse state_response;

    final int PIXEL_SIZE = 20;

    Random r = new Random();

    private int n = 0;

    private int max_n = 100;

    public GraphicsClass(HttpClient hc) {
        this.httpClient = hc;

        try {
            this.get_state_request = HttpRequest.newBuilder()
                    .uri(new URI("http://127.0.0.1:8000/api/state"))
                    .version(HttpClient.Version.HTTP_1_1)
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        ////communications part, asking for state.../////

        try {
            this.state_response = this.httpClient.send(get_state_request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ///////end of communication part///////////////

        StateDescriber stateDescriber = JsonParser.getGameStateOutOfRequest(this.state_response);

        if (stateDescriber.isWaiting) {

            n += 1;

            g.setColor(new Color(r.nextInt(50, 100), 0, 0));
            g.fillOval(n % max_n, 0, 100, 100);

            g.setColor(new Color(0, r.nextInt(50, 100), 0));
            g.fillOval(max_n - n % max_n, 200, 100, 100);

            repaint();
        } else {

            System.out.println(state_response.body());

            g.setColor(new Color(0, 255, 0));
            g.fillOval(stateDescriber.food.x * this.PIXEL_SIZE, stateDescriber.food.y * this.PIXEL_SIZE, this.PIXEL_SIZE, this.PIXEL_SIZE);

            g.setColor(new Color(255, 0, 0));
            for (Point head : stateDescriber.heads) {
                g.fillOval(head.x * this.PIXEL_SIZE, head.y * this.PIXEL_SIZE, this.PIXEL_SIZE, this.PIXEL_SIZE);
            }

            g.setColor(new Color(0, 0, 255));
            for (List<Point> tail : stateDescriber.tails) {
                if (tail != null) {
                    for (Point point : tail) {
                        g.fillOval(point.x * this.PIXEL_SIZE, point.y * this.PIXEL_SIZE, this.PIXEL_SIZE, this.PIXEL_SIZE);
                    }
                }
            }
            repaint();
        }

    }


}
