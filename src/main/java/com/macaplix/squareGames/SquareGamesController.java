package com.macaplix.squareGames;

import com.macaplix.squareGames.dao.SQLconnector;
import com.macaplix.squareGames.dto.*;
import com.macaplix.squareGames.service.GameCatalog;
import com.macaplix.squareGames.service.GameCatalogImpl;
import com.macaplix.squareGames.service.GameService;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import org.apache.logging.log4j.Logger;
import org.apache.logging.slf4j.Log4jLogger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SquareGamesController {
    @Autowired
    private GameService gameService;
    private static final Log4jLogger LOGGER = (Log4jLogger) LoggerFactory.getLogger(SquareGamesController.class);
    /*
       @Autowired

        private GameDAO gameD;
        @Autowired
        private TokenDAO tokenDAO;
    */
    SquareGamesController()
    {
        //SQLconnector.getInstance();
    }
    /**
     *
     * @param params GameParams DTO to hold game creation data
     * @return GameParamsAnswer DTO to hold creation result
     */

    @PostMapping( value = "/games", consumes = {"application/xml","application/json"})
    private GameDescription gameCreate(@RequestBody( required= false) GameParams params)
    {
        if (  params == null ) params = new GameParams(0,0,0);
        LOGGER.warn("No parameters provided for game creation, default will be used");
        //System.out.println(params);
        GameDescription rez = gameService.createGame( params );
        if ( !rez.isOk())
        {
            LOGGER.warn(rez.errorMessage());
            return rez;
        }
        //int sqlid, String gameKey, int gameType, int currentPlayerID, String gameStatus, int boardSize, Date creationDate, Duration duration, boolean success, String errorMessage
        //gameDAO.saveGame( new GameSaveDTO(0,rez.gameUUID(), rez.gameIndex(), 0, "SETUP", rez.boardSize(), null, null, true, ""));
        gameService.saveGame(new GameSaveDTO(0,rez.gameKey(), rez.gameIndex(), 0, "SETUP", rez.boardSize(), null, null, true, ""));
        gameService.saveTokens(rez.gameKey());
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

            //i++;
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
        //String val ="1";
        GameDescription gd = gameService.getGameDescription(gameid);
        if ( !gd.isOk() )
        {
            LOGGER.error(gd.errorMessage());
        }
        return gd;
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
    @PostMapping( value = "/games/{gameid}/tokens/{tokenid}", consumes = {"application/xml","application/json"})
    private MovedTokenResult moveToken(@PathVariable(value = "gameid") String gameid, @PathVariable(value = "tokenid") int tokenid, @RequestBody MoveTokenParam moveTokenParam)
    {
        MovedTokenResult mtr = gameService.moveToken(gameid,tokenid, new  CellPosition(moveTokenParam.xdest(), moveTokenParam.ydest()));
        if ( !mtr.success())
        {
            LOGGER.warn(mtr.errorMessage());
        }
        return mtr;
    }

}
