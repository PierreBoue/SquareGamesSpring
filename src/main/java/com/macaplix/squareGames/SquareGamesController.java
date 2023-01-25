package com.macaplix.squareGames;

import com.macaplix.squareGames.dto.*;
import com.macaplix.squareGames.service.GameCatalog;
import com.macaplix.squareGames.service.GameCatalogDummyImpl;
import com.macaplix.squareGames.service.GameService;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SquareGamesController {
   // private  final int dummyInt=-1;
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
    @GetMapping("/games/types")
    private GameTypeInfo[] getGameTypes( )
    {
        GameCatalog catalog = new GameCatalogDummyImpl();
        return catalog.getGameTypes( gameService);
    }

    @GetMapping("/games/{gameid}")
    private GameDescription getGame(@PathVariable(value = "gameid") String gameid)
    {
        String val ="1";

        //Game game = gameService.getGame( gameid );
        return gameService.getGameDescription(gameid);
    }

    @GetMapping("/games/{gameid}/tokens")
    private TokenInfo[] getTokenInfo(@PathVariable(value = "gameid") String gameid, @RequestParam( required = false, defaultValue = "-1" ) int x, @RequestParam( required = false, defaultValue = "-1") int y)
    {
      if ((x < 0) && ( y <0 )) return gameService.getTokenList(gameid);
       return new TokenInfo[] {gameService.getTokenInfo(gameid, new CellPosition(x, y))};
    }
    @PostMapping("/games/{gameid}/tokens/{tokenid}")
    private MovedTokenResult moveToken(@PathVariable(value = "gameid") String gameid, @PathVariable(value = "tokenid") int tokenid, @RequestBody MoveTokenParam moveTokenParam)
    {
        return gameService.moveToken(gameid,tokenid, new  CellPosition(moveTokenParam.xdest(), moveTokenParam.ydest()));
    }

}
