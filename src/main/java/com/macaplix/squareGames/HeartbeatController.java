package com.macaplix.squareGames;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class HeartbeatController {
    @Autowired
    private HeartbeatSensor heartbeatSensor;
    @Autowired
    private GameCatalog gamecatalog;
    @Autowired
    private GameCreator gameCreator;
    @GetMapping("/gamelist")
    Collection<String> getGameList()
    {
        return gamecatalog.getGameIdentifiers();
    }
    @GetMapping("/creategame")
    String creategame( int gameIndex, int playerCount, int boardSize)
    {
        return gameCreator.createGame(gameIndex, playerCount, boardSize );
        //return playerCount + " joueur" + ((playerCount>1)?"s":"") + " size " + boardSize;
    }
    @GetMapping("/heartbeat")
    int getHeartbeat()
    {
        return heartbeatSensor.get();
    }
    @GetMapping("/heartbeatview")
    String getHeartbeatString()
    {
        return "Your heart beat is: " + heartbeatSensor.get() + "<br>congratulation!!!";
    }
/*
    @GetMapping("/PierreBoue")
    String salutation()
    {
        return "Hello Pierre";
    }
    @GetMapping("/error")
    String errorMessage()
    {
        return "Error!!!";
    }
*/
}
