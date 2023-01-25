package com.macaplix.squareGames.service;

import com.macaplix.squareGames.dto.*;
import com.macaplix.squareGames.plugin.ConnectfourPlugin;
import com.macaplix.squareGames.plugin.GamePlugin;
import com.macaplix.squareGames.plugin.TaquinPlugin;
import com.macaplix.squareGames.plugin.TicTacToePlugin;
import fr.le_campus_numerique.square_games.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;

@Service
public class GameServiceImpl implements GameService
{
    //private Game activeGame;
    @Autowired
    private TicTacToePlugin ticTacToePlugin;
    @Autowired
    private TaquinPlugin taquinPlugin;
    @Autowired
    private ConnectfourPlugin connectfourPlugin;
    private HashMap<String, Game> activeGames;
    private HashMap<String, TokenInfo[]> gameTokens;

    public GameServiceImpl()
    {
        activeGames = new HashMap<String, Game>();
        gameTokens = new HashMap<String, TokenInfo[]>();
    }
    public GamePlugin pluginForGame( String gameName )
    {
        GamePlugin gamePlugin=null;
        switch (gameName)
        {
            case "tictactoe":
                gamePlugin = ticTacToePlugin;
                break;
            case "connect4":
                gamePlugin = connectfourPlugin;
                break;
            case "15 puzzle":
                gamePlugin = taquinPlugin;
                break;
            default:
                System.out.println("Unknown game name");
                break;

        }

        return gamePlugin;
    }

    @Override
    public GameParamsAnswer createGame(GameParams params)
    {
        GameParamsAnswer aparam;
       GamePlugin plugin=null;
        switch (params.gameIndex())
        {
            case 0:
                plugin = ticTacToePlugin;
                break;
            case 1:
                plugin = taquinPlugin;
                break;
            case 2:
                plugin = connectfourPlugin;
                break;
            default:
                return new GameParamsAnswer(params.gameIndex(), params.playerCount(), params.boardSize(), null,"gameIndex out of range", false,"");
        }
        if ( plugin == null)
            return null;
        GameParamsAnswer answer = plugin.checkParams( params);
        if ( ! answer.isOk())
        {
            return answer;
        }
        Game activeGame = plugin.createGame( answer );
        String uuid = UUID.randomUUID().toString();
        activeGames.put(uuid, activeGame);
        Locale curLocale = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);
        return new GameParamsAnswer(answer.gameIndex(), answer.playerCount(), answer.boardSize(), uuid, "ok", true, plugin.getName(curLocale));
    }

    private Game getGame( String gameid)
    {
        return activeGames.get(gameid);
    }
    @Override
    public GameDescription getGameDescription(String gameid)
    {
        Game game = getGame(gameid);
        return new GameDescription(gameid, game.getFactoryId(), game.getBoardSize(), game.getPlayerIds().size(), game.getBoard());
    }

    @Override
    public TokenInfo[] getTokenList(String gameid)
    {
        Game game = activeGames.get( gameid);

       // Token[] onboardTokens = (Token[]) game.getBoard().values().toArray();
        //Token[] remainingTokens = (Token[]) game.getRemainingTokens().toArray();
       // Token[] allTokens = Stream.concat(Arrays.stream(onboardTokens), Arrays.stream(remainingTokens)).toArray(size->(Token[]) Array.newInstance(Token.class, size));
        TokenInfo[] tokenInfos = new TokenInfo[game.getBoard().size() + game.getRemainingTokens().size()];
        int idx=0;
        for (Map.Entry<CellPosition, Token> set : game.getBoard().entrySet())
        {
           Token t = set.getValue();
            tokenInfos[idx]= new TokenInfo( gameid, t.getPosition(), idx, t.getName(), t.canMove(), true);
            idx++;
        }
        for (Token t:game.getRemainingTokens())
        {
            tokenInfos[idx]= new TokenInfo( gameid, t.getPosition(), idx, t.getName(), t.canMove(), false);
            idx++;
        }
/*
        for (Token t:allTokens)
        {
            tokenInfos[idx]= new TokenInfo( gameid, t.getPosition(), idx, t.getName(), t.canMove(), true);
            idx++;
        }
*/
        gameTokens.put(gameid, tokenInfos);
        return tokenInfos;
    }

    @Override
    public TokenInfo getTokenInfo(String gameid, CellPosition position)
    {
        Game game = activeGames.get( gameid);
        if ( game == null ) return new TokenInfo(gameid, position, -1,"no game", false, false);
        Token t = game.getBoard().get(position);
        boolean onBoard = false;
        int idx = -1;
        if ( t== null)
        {
            onBoard = false;
            t = game.getRemainingTokens().iterator().next();
            idx = 0;
        } else {
           // Collection<Token> obtokens = game.getBoard().values();
           // idx = obtokens.stream().map(tp->( tp.isEqual(t))).indexOf( t);
            onBoard = true;
        }
        boolean canMove = ( t== null)?false:t.canMove();
        String name = ( t== null)?"null":t.getName();
         return new TokenInfo( gameid, position, idx, name, canMove, onBoard );
    }
    @Override
    public MovedTokenResult moveToken(String gameid, int tokenIndex, CellPosition newpos)
    {
        Game game = activeGames.get(gameid);
        TokenInfo tokenInfo = gameTokens.get(gameid)[tokenIndex];
        Token token = (tokenInfo.onBoard())?game.getBoard().get(tokenInfo.position()):(Token) game.getRemainingTokens().toArray()[tokenInfo.tokenIndex()-game.getBoard().size()];
        MovedTokenResult result=null;
        if (token.canMove())
        {
            try {
                token.moveTo(newpos);
                result = new MovedTokenResult(gameid, tokenIndex, newpos.x(), newpos.y(), true,"ok");
            } catch (InvalidPositionException e) {
                result = new MovedTokenResult(gameid, tokenIndex, newpos.x(), newpos.y(), false,"Impossible to move token: " + e.toString());
                //throw new RuntimeException(e);
            }
        } else {
            result = new MovedTokenResult(gameid, tokenIndex, newpos.x(), newpos.y(), false,"token cannot move");
        }
        //game.getBoard().computeIfAbsent()
        return result;//new MovedTokenResult( gameid, 0, );
    }


    @Override
    public HashMap<String, Game> getAllGames()
    {
        return activeGames;
    }

    private String checkPlayerCount(GameFactory factory, int playerCount )
    {
        IntRange range = factory.getPlayerCountRange();
        if ( !range.contains(playerCount)) return "playerCount should be between " + range.min() + " and " + range.max();
        return "";
    }
    private String checkBoardSize( GameFactory factory, int boardSize, int playerCount)
    {
        IntRange range =factory.getBoardSizeRange( playerCount);
        if ( !range.contains(boardSize)) return "boardSize should be between " + range.min() + " and " + range.max();
        return "";
    }

}
