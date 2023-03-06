package com.example.spotify;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TokenService {
    private static final ThreadLocal<String> SECURITY_CONTEXT = new ThreadLocal<>();


    public static void setAuth(String auth) {
        SECURITY_CONTEXT.set(auth);
    }

    public static String getAuth() {
        return SECURITY_CONTEXT.get();
    }

    public static void clear(){
        SECURITY_CONTEXT.remove();
    }

    public static String getToken() {
        return "Bearer " + getAuth();
    }
}

