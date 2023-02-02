package com.macaplix.squareGames.security;

public class AuthenticationRequest {
    private String username;
    private String password;
    private String token;
    public String getUsername()
    {
        return username;
    }
    void setUsername( String usrename)
    {
        this.username = usrename;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword( String password)
    {
        this.password = password;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
}
