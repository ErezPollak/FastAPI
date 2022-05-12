import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;


public class StateDescriber {

    private final int[] DIRECTION_POINTS_X = {-1, 0, 1, 0};
    private final int[] DIRECTION_POINTS_Y = {0, -1, 0, 1};

    public boolean isWaiting = true;
    public Point food = new Point();
    public LinkedList<Point> heads = new LinkedList<Point>();
    public ArrayList<LinkedList<Point>> tails = new ArrayList<LinkedList<Point>>();

    public StateDescriber setAttributes(JSONObject json) {

        this.isWaiting = false;

        this.food = getPointFromString((String) json.get("food"));

        for (var p : (JSONArray) json.get("players")) {

            JSONObject player = (JSONObject) p;

            Point head = getPointFromString(player.get("head").toString());

            this.heads.add(head);

            this.tails.add(getTailFromString((String) player.get("tail"), head));
        }

        return this;
    }

    private LinkedList<Point> getTailFromString(String stringTail, Point head) {

        if (stringTail.equals(""))
            return null;

        System.out.println("before Paring: " + stringTail);

        LinkedList<Point> points = new LinkedList<>();

        Point lastPoint = head;
        for (String s : stringTail.split(" ")) {
            int direction = Integer.parseInt(s);
            Point nextPoint = getPointFromDirection(lastPoint, direction);
            points.add(nextPoint);
            lastPoint = nextPoint;
        }

        System.out.println("After parsing: " + points.toString());

        return points;
    }

    private Point getPointFromDirection(Point lastPoint, int direction) {
        return new Point(lastPoint.x + DIRECTION_POINTS_X[direction],
                lastPoint.y + DIRECTION_POINTS_Y[direction]);
    }

    private Point getPointFromString(String point) {
        String[] splitPoint = point.split(" ");
        return new Point(Integer.parseInt(splitPoint[0]),
                Integer.parseInt(splitPoint[1]));
    }

}
