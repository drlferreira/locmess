package ist.meic.cnv.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Diogo on 05/04/2017.
 */

@Service
public class TokenService {

    // key - token
    // value - username
    private ConcurrentHashMap<String,String> tokens;

    @PostConstruct
    public void init(){
        tokens = new ConcurrentHashMap<>();
    }

    public String generateToken(String username){
        String token = null;
        try {
            token = JWT.create()
                    .withSubject(username)
                    .withIssuer("locmess")
                    .sign(Algorithm.HMAC512(UUID.randomUUID().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(token != null)
            tokens.put(token, username);

        return token;
    }

    public String removeToken(String token){
        return tokens.remove(token);
    }

    public String getUsername(String token){
        return tokens.get(token);
    }

}
