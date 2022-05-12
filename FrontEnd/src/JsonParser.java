import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JsonParser {

    private static JSONParser parser = new JSONParser();

    public static boolean isResponseSuccessful(HttpResponse<String> response){
        try {

            JSONObject json = (JSONObject) parser.parse(response.body());

            System.out.println(json.get("message"));

            return ((String) json.get("status")).equals("SUCCESSES");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static StateDescriber getGameStateOutOfRequest(HttpResponse<String> response){
        try {

            JSONObject json = (JSONObject) parser.parse(response.body());

            if(json.get("type").equals("WAITING")){
                return new StateDescriber();
            }

            return new StateDescriber().setAttributes((JSONObject) json.get("massage"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
