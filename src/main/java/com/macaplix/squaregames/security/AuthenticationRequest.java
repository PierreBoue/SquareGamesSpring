package com.macaplix.squaregames.security;

public class AuthenticationRequest {
    private String username;
    private String password;
    private String token;
    private String refreshToken;
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

    public String getRefreshToken()
    {
        return refreshToken;
    }
    public void setRefreshToken( String refreshToken)
    {
        this.refreshToken = refreshToken;
    }
}
