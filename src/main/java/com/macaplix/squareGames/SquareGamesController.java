package com.macaplix.squareGames;

import fr.le_campus_numerique.square_games.engine.CellPosition;
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
    private GameParamsAnswer gameCreate(@RequestBody( required= false) GameParams params)
    {
        if (  params == null ) params = new GameParams(0,0,0);
        //System.out.println(body);
        return gameService.createGame( params );
    }
    @GetMapping("/games")
    private GameDescription[] getGames()
    {
        HashMap<String,Game> activeGames = gameService.getAllGames();
        //System.out.println(activeGames);
        GameDescription[] games = new GameDescription[activeGames.size()];
        int i=0;
        for (Map.Entry<String,Game> set: activeGames.entrySet())
        {
            games[i++] = gameService.getGameDescription(set.getKey());
        }
        return games;

    }
    @GetMapping("/games/{gameid}")
    private GameDescription getGame(@PathVariable(value = "gameid") String gameid)
    {
        String val ="1";

        //Game game = gameService.getGame( gameid );
        return gameService.getGameDescription(gameid);
    }

    @GetMapping("/games/{gameid}/tokens")
    private TokenInfo getTokenInfo(@PathVariable(value = "gameid") String gameid, @RequestParam int x, @RequestParam int y)
    {
       return gameService.getTokenInfo(gameid, new CellPosition(x, y));
        // return "gameid = " + gameid + " token = x: " + x + " y: " + y;
    }
    @PostMapping("/games/{gameid}/tokens/{tokenid}")
    private MovedTokenResult moveToken(@PathVariable(value = "gameid") String gameid, @PathVariable(value = "tokenid") int tokenid, @RequestBody MoveTokenParam moveTokenParam)
    {
        return gameService.moveToken(gameid, new CellPosition( 10,  2 ), new  CellPosition(moveTokenParam.xdest(), moveTokenParam.ydest()));
    }

}
