package com.macaplix.squareGames;

import com.macaplix.squareGames.dao.MySQLconnector;
import com.macaplix.squareGames.dto.*;
import com.macaplix.squareGames.service.GameCatalog;
import com.macaplix.squareGames.service.GameCatalogImpl;
import com.macaplix.squareGames.service.GameService;
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
    /*
       @Autowired

        private GameDAO gameD;
        @Autowired
        private TokenDAO tokenDAO;
    */
    SquareGamesController()
    {
        MySQLconnector.getInstance();
    }
    /**
     *
     * @param params GameParams DTO to hold game creation data
     * @return GameParamsAnswer DTO to hold creation result
     */

    @PostMapping( "/games")
    private GameParamsAnswer gameCreate(@RequestBody( required= false) GameParams params)
    {
        if (  params == null ) params = new GameParams(0,0,0);
        //System.out.println(body);
        GameParamsAnswer rez = gameService.createGame( params );
        //int sqlid, String gameKey, int gameType, int currentPlayerID, String gameStatus, int boardSize, Date creationDate, Duration duration, boolean success, String errorMessage
        //gameDAO.saveGame( new GameSaveDTO(0,rez.gameUUID(), rez.gameIndex(), 0, "SETUP", rez.boardSize(), null, null, true, ""));
        gameService.saveGame(new GameSaveDTO(0,rez.gameUUID(), rez.gameIndex(), 0, "SETUP", rez.boardSize(), null, null, true, ""));
        gameService.saveTokens(rez.gameUUID());
        return rez;
    }

    /**
     * get the list of the created games
     * @return
     */
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

    /**
     *
     * @return every available game types
     */
    @GetMapping("/games/types")
    private GameTypeInfo[] getGameTypes( )
    {
        GameCatalog catalog = new GameCatalogImpl();
        return catalog.getGameTypes( gameService);
    }

    /**
     *
     * @param gameid UUID as returned by /games
     * @return the game description and state
     */
    @GetMapping("/games/{gameid}")
    private GameDescription getGame(@PathVariable(value = "gameid") String gameid)
    {
        String val ="1";

        //Game game = gameService.getGame( gameid );
        return gameService.getGameDescription(gameid);
    }

    /**
     *
     * @param gameid game UUID
     * @param x optional
     * @param y optional
     * @return the game token list
     * if x and y are omitted it returns all the tokens of the game, otherwise the token at position x, y on the board
     */
    @GetMapping("/games/{gameid}/tokens")
    private TokenInfo[] getTokenInfo(@PathVariable(value = "gameid") String gameid, @RequestParam( required = false, defaultValue = "-1" ) int x, @RequestParam( required = false, defaultValue = "-1") int y)
    {
      if ((x < 0) && ( y <0 )) return gameService.getTokenList(gameid);
       return new TokenInfo[] {gameService.getTokenInfo(gameid, new CellPosition(x, y))};
    }

    /**
     * moves a token
     * @param gameid game UUID
     * @param tokenid token index as returned in the token list from above endpoint
     * @param moveTokenParam coordinates of the new token position
     * @return MovedTokenResult which encapsulate a boolean "success" and an error message in case of failure
     */
    @PostMapping("/games/{gameid}/tokens/{tokenid}")
    private MovedTokenResult moveToken(@PathVariable(value = "gameid") String gameid, @PathVariable(value = "tokenid") int tokenid, @RequestBody MoveTokenParam moveTokenParam)
    {
        return gameService.moveToken(gameid,tokenid, new  CellPosition(moveTokenParam.xdest(), moveTokenParam.ydest()));
    }

}
