package com.yiran.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yiran.model.entity.User;
import java.util.Date;

/**
 * @author Yang Song
 * @date 2022/11/1 9:12
 */
public class JwtUtil {
    private static final long EXPIRE_TIME= 60*30*1000;
    private static final String TOKEN_SECRET="SONGYANG277";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(TOKEN_SECRET);
    public static String sign(User user){
        return JWT.create()
                .withIssuer("yiran")
                .withClaim("userId",user.getUserId())
                .withClaim("userName",user.getUsername())
                .withClaim("userAvatar",user.getImage())
                .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRE_TIME))
                .sign(ALGORITHM);
    }
    public static boolean verify(String token){
        try {
            JWT.require(ALGORITHM)
                    .withIssuer("yiran")
                    .build()
                    .verify(token);
        }
        catch (JWTVerificationException exception){
            return false;
        }
        return true;
    }
    public static String getUserId(String token){
        try{
            DecodedJWT jwt= JWT.decode(token);
            return  jwt.getClaim("userId").asString();
        }catch (JWTDecodeException e)
        {
            e.printStackTrace();
            return null;
        }
    }

//    public static void main(String[] args) {
//        User user = new User();
//        user.setUserId("song");
//        user.setUsername("sf");
//        user.setImage("123213");
//        String sign = sign(user);
//        System.out.println(sign);
//    }
}
