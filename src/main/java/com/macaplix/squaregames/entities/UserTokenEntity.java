package com.macaplix.squaregames.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name= "user_token")
public class UserTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //@ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "userid", referencedColumnName = "id")
    //@MapsId
    private int userid;
    private String mainToken;
    private Date mainCreation;
    private Date mainExpiration;
    private String refreshToken;
    private Date refreshCreation;
    private Date refreshExpiration;

    public UserTokenEntity()
    {

    }

    public Date getMainCreation()
    {
        return mainCreation;
    }

    public Date getMainExpiration()
    {
        return mainExpiration;
    }

    public int getUserid()
    {
        return userid;
    }

    public Date getRefreshCreation()
    {
        return refreshCreation;
    }

    public String getMainToken()
    {
        return mainToken;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }

    public Date getRefreshExpiration()
    {
        return refreshExpiration;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public void setMainToken(String mainToken)
    {
        this.mainToken = mainToken;
    }

    public void setMainCreation(Date mainCreation)
    {
        this.mainCreation = mainCreation;
    }

    public void setMainExpiration(Date mainExpiration)
    {
        this.mainExpiration = mainExpiration;
    }

    public void setRefreshCreation(Date refreshCreation)
    {
        this.refreshCreation = refreshCreation;
    }

    public void setRefreshExpiration(Date refreshExpiration)
    {
        this.refreshExpiration = refreshExpiration;
    }

    public void setRefreshToken(String refreshToken)
    {
        this.refreshToken = refreshToken;
    }
}
