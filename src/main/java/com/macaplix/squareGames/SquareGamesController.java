package com.macaplix.squareGames;

import fr.le_campus_numerique.square_games.engine.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SquareGamesController {
    @Autowired
    private GameService gameService;
    @PostMapping( "/games")
    private GameParamsAnswer gameCreate(@RequestBody GameParams params)
    {
        //System.out.println(body);
        return gameService.createGame( params );
    }
    @GetMapping("/games")
    private GameDescription[] getGames()
    {
        HashMap<String,Game> activeGames = gameService.getAllGames();
        System.out.println(activeGames);
        GameDescription[] games = new GameDescription[activeGames.size()];
        int i=0;
        for (Map.Entry<String,Game> set: activeGames.entrySet())
        {
            games[i++] = gameService.getGameDescription(set.getKey());
        }
/*
        activeGames.forEach((key, value)-> {
            games[i++] = gameService.getGameDescription(key);
        });
        // new GameDescription(key, value.getFactoryId(), value.getBoardSize(), value.getPlayerIds().size()) );
        for (String key: activeGames.keySet())
        {
            Game game = activeGames.get(key);
            games[i]= new GameDescription(key, game.getFactoryId(), game.getBoardSize(), game.getPlayerIds().size());
        }
*/
        return games;

    }
    @GetMapping("/games/{gameid}")
    private GameDescription getGame(@PathVariable(value = "gameid") String gameid)
    {
        //Game game = gameService.getGame( gameid );
        return gameService.getGameDescription(gameid);
    }

    @GetMapping("/games/{gameid}/tokens/{tokenid}")
    private String gameUpdate(@PathVariable(value = "gameid") int gameid, @PathVariable(value = "tokenid") int tokenid)
    {
        return "gameid = " + gameid + " token = " + tokenid;
    }
}
