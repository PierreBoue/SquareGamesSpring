package com.macaplix.squareGames.service;

import com.macaplix.squareGames.dao.GameDAOMySQL;
import com.macaplix.squareGames.dao.TokenDAO;
import com.macaplix.squareGames.dto.*;
import com.macaplix.squareGames.entities.TokenEntity;
import com.macaplix.squareGames.plugin.ConnectfourPlugin;
import com.macaplix.squareGames.plugin.GamePlugin;
import com.macaplix.squareGames.plugin.TaquinPlugin;
import com.macaplix.squareGames.plugin.TicTacToePlugin;
import fr.le_campus_numerique.square_games.engine.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {
    //private Game activeGame;
    @Autowired
    private TicTacToePlugin ticTacToePlugin;
    @Autowired
    private TaquinPlugin taquinPlugin;
    @Autowired
    private ConnectfourPlugin connectfourPlugin;
    @Autowired
    private TokenDAO tokenDAO;
    @Autowired
    private GameDAOMySQL gameDAOMySQL;
    @Autowired
    private HttpServletRequest request;

    private HashMap<String, Game> activeGames;
    private HashMap<String, TokenInfo[]> gameTokens;

    public GameServiceImpl() {
        activeGames = new HashMap<String, Game>();
        gameTokens = new HashMap<String, TokenInfo[]>();
        //readPersistentGames();
    }

    private GamePlugin[] getPlugins() {
        return new GamePlugin[]{ticTacToePlugin, taquinPlugin, connectfourPlugin};

    }

    public GamePlugin pluginForGame(String gameName) {
        GamePlugin gamePlugin = null;
        switch (gameName) {
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
    public GameParamsAnswer createGame(GameParams params) {
        GameParamsAnswer aparam;
        int idx = params.gameIndex();
        if ((idx < 0) || (idx >= getPlugins().length))
            return new GameParamsAnswer(params.gameIndex(), params.playerCount(), params.boardSize(), null, "gameIndex out of range", false, "");
        GamePlugin plugin = getPlugins()[idx];
        GameParamsAnswer answer = plugin.checkParams(params);
        if (!answer.isOk()) {
            return answer;
        }
        Game activeGame = plugin.createGame(answer);
        String uuid = UUID.randomUUID().toString();
        activeGames.put(uuid, activeGame);
        Locale curLocale = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);
        //Locale newloc = Locale.of( response.getHeader("Accept-Language"));
        //System.out.println(newloc);
        GameParamsAnswer rep = new GameParamsAnswer(answer.gameIndex(), answer.playerCount(), answer.boardSize(), uuid, "ok", true, plugin.getName(getEndUserLocale()));
        Locale.setDefault(curLocale);
        return rep;
    }
    public Locale getEndUserLocale()
    {
        Locale newloc = Locale.of( request.getHeader("Accept-Language"));
        System.out.println(newloc);
        return newloc;
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
    public void saveGame( GameSaveDTO gameInfo )
    {
        gameDAOMySQL.saveGame(gameInfo);
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
    public void saveTokens(String gameid)
    {
        Game game = activeGames.get(gameid);
        for (Token t: game.getBoard().values())
        {
            TokenEntity te = new TokenEntity();
            te.setGameId( gameid);
            te.setName( t.getName());
            te.setPositionX( Integer.valueOf(t.getPosition().x()));
            te.setPositionY(Integer.valueOf(t.getPosition().y()));
            te.setOnBoard(Boolean.TRUE);
            te.setCanMove( Boolean.valueOf( t.canMove()));
            tokenDAO.save(te);
        }
        for (Token t: game.getRemainingTokens())
        {
            TokenEntity te = new TokenEntity();
            te.setGameId( gameid);
            te.setName( t.getName());
            te.setPositionX( Integer.valueOf(0));
            te.setPositionY(Integer.valueOf(0));
            te.setOnBoard(Boolean.TRUE);
            te.setCanMove( Boolean.valueOf( t.canMove()));
            tokenDAO.save(te);

        }
    }

    @Override
    public List<Token> readTokens(int gamid) {
        return null;
    }


    @Override
    public HashMap<String, Game> getAllGames()
    {
        return activeGames;
    }
    @PostConstruct
    private void readPersistentGames()
    {
        ArrayList<GameSaveDTO> gameDTOs = gameDAOMySQL.readGames( );
        //System.out.println("read persistent");
        for (GameSaveDTO gdto: gameDTOs)
        {
            GamePlugin plugin = getPlugins()[gdto.gameType()];
            Game game = plugin.createGame( new GameParamsAnswer(gdto.gameType(), plugin.getDefaultPlayerCount(), gdto.boardSize(), gdto.gameKey(), "ok",true, plugin.getName(Locale.getDefault())));
            activeGames.put(gdto.gameKey(), game);
        }
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
