package ist.meic.cmu.controller;

import java.util.List;

/**
 * Created by Diogo on 05/04/2017.
 */
public abstract class LocmessController {

    protected final String TOKEN_HEADER = "X-Token";

    protected String buildResponseBody(String message){
        return "{\"message\":" + " \"" + message + "\"}";
    }

    protected String buildResponseBodyJson(List<String> keys, List<String> values){
        String output = "{";

        if(keys == null || values == null)
            return output += "}";

        if(keys.size() == values.size()){
            for (int i = 0; i < keys.size(); i++){
                output += "\"" + keys.get(i) + "\"" + " : " + "\"" + values.get(i) + "\"";
                output += i == keys.size() - 1 ? "}" : " ,";
            }
        }
        return output;
    }

}
