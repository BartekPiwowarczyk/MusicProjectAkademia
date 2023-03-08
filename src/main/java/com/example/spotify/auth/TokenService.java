package com.example.spotify.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Singleton;
import java.io.Serializable;


@ApplicationScoped
public class TokenService implements Serializable {
    private final ThreadLocal<String> SECURITY_CONTEXT = new ThreadLocal<>();


    public void setAuth(String auth) {
        SECURITY_CONTEXT.set(auth);
    }

    public  String getAuth() {
        return SECURITY_CONTEXT.get();
    }

    public  void clear(){
        SECURITY_CONTEXT.remove();
    }

    public  String getToken() {
        return "Bearer " + getAuth();
    }
}

