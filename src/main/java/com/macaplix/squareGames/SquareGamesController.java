package com.macaplix.squareGames;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SquareGamesController {
    @Autowired
    private GameService gameService;
    @GetMapping("/games/{gameid}/tokens/{tokenid}")
    private String gameUpdate(@PathVariable(value = "gameid") int gameid, @PathVariable(value = "tokenid") int tokenid)
    {
        return "gameid = " + gameid + " token = " + tokenid;
    }
    @GetMapping( "/games")
    private GameParamsAnswer gameCreate(@RequestBody GameParams params)
    {
        //System.out.println(body);
        return gameService.createGame( params );
    }
}
