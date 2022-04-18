package framework;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.UserData;

public class Utils {

    public static String UserDataToJson(UserData userData)
    {
        String json = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(userData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
