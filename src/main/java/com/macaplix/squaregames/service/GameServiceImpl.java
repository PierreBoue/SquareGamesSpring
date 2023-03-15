package com.macaplix.squaregames.service;

import com.macaplix.squaregames.dao.GameDaoSQL;
import com.macaplix.squaregames.dao.TokenDao;
import com.macaplix.squaregames.dto.*;
import com.macaplix.squaregames.entities.TokenEntity;
import com.macaplix.squaregames.plugin.*;
import fr.le_campus_numerique.square_games.engine.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
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
    private TokenDao tokenDAO;
    @Autowired
    private GameDaoSQL gameDAOSQL;
    @Value("${stats.local.url}")
    private String rootURL;

    @Autowired
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    private static Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);
    private HashMap<String, Game> activeGames;
    private HashMap<String, Date> gameCreations;
    private HashMap<String, TokenInfo[]> gameTokens;

    public GameServiceImpl() {
        activeGames = new HashMap<String, Game>();
        gameTokens = new HashMap<String, TokenInfo[]>();
        this.gameCreations = new HashMap<String, Date>();
        //GameServiceImpl.logger.warn("Instantiated GameService");
        //Logger logger = (Logger) LoggerFactory.getLogger("com.macaplix.squareGames");
        //readPersistentGames();
        //LoggerUtility.getInstance( this.getClass()).logWarning("this a warning");

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
    public GameDescription createGame(GameParams params) {
        GameDescription aparam;
        int idx = params.gameIndex();
        //int gameIndex, String gameKey, int sqlid, String typeLocale, String typeName, int playerCount, int boardSize, Map<CellPosition, Token> board, Date creation, int duration, String errorMessage, boolean isOk
        if ((idx < 0) || (idx >= getPlugins().length))
            return new GameDescription(params.gameIndex(), "", 0, "", "", params.playerCount(), params.boardSize(), new HashMap<CellPosition, Token>(), new ArrayList<>(), new ArrayList<>(), (new Date()).toInstant().getEpochSecond(), 0, "gameIndex out of range", false);
        GamePlugin plugin = getPlugins()[idx];
        GameDescription answer = plugin.checkParams(params);
        if (!answer.isOk()) {
            return answer;
        }
        Game activeGame = plugin.createGame(answer);
        String uuid = UUID.randomUUID().toString();
        activeGames.put(uuid, activeGame);
        gameCreations.put(uuid, new Date());
        Locale curLocale = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);
        //int gameIndex, String gameKey, int sqlid, String typeLocale, String typeName, int playerCount, int boardSize, Map<CellPosition, Token> board, Date creation, int duration, String errorMessage, boolean isOk        GameDescription rep = new GameDescription(answer.gameIndex(), answer.playerCount(), answer.boardSize(), uuid, "ok", true, plugin.getName(getEndUserLocale()));
        GameDescription rep = new GameDescription(answer.gameIndex(), uuid, 0, plugin.getName(getEndUserLocale(), ticTacToePlugin), plugin.getType(), answer.playerCount(), answer.boardSize(), new HashMap<CellPosition, Token>(), new ArrayList<>(), new ArrayList<>(), (new Date()).toInstant().getEpochSecond(), 0, "ok", true);

        Locale.setDefault(curLocale);
        return rep;
    }

    public Locale getEndUserLocale() {
        //Locale newloc = Locale.of( );
        //System.out.println(newloc);
        return LocaleContextHolder.getLocale();
/*
        String lngheader = null;
        try {
            lngheader = request.getHeader("Accept-Language");
        } catch (Exception e) {
            lngheader = "fr";
            System.err.println("No language header: " + e );
        }
        Locale newloc = Locale.of( lngheader);
        return newloc;
*/
    }

    private Game getGame(String gameid) {
        return activeGames.get(gameid);
    }

    @Override
    public GameDescription getGameDescription(String gameid) {
        Game game = getGame(gameid);
        if (game == null)
            return new GameDescription(0, gameid, 0, "", "", 0, 0, null, null, null, 0L, 0, "game not found", false);
        GamePluginAuto gpa = new GamePluginAuto(game.getFactoryId());
        //int gameIndex, String gameKey, int sqlid, String typeLocale, String typeName, int playerCount, int boardSize, Map<CellPosition, Token> board, Date creation, int duration, String errorMessage, boolean isOk
        return new GameDescription(gpa.getTypeIndex(), gameid, 0, gpa.getName(getEndUserLocale(), ticTacToePlugin), game.getFactoryId(), game.getPlayerIds().size(), game.getBoardSize(), game.getBoard(), game.getRemainingTokens(), game.getRemovedTokens(), this.gameCreations.get(gameid).toInstant().getEpochSecond(), 0, "ok", true);
    }

    /*
    public GameDescription getGameDescription(Game game, String gameid)
    {
        Game game = getGame(gameid);
        if ( game == null) return new GameDescription(0, gameid, 0, "","", 0, 0, null, null,0,"game not found", false);
        //int gameIndex, String gameKey, int sqlid, String typeLocale, String typeName, int playerCount, int boardSize, Map<CellPosition, Token> board, Date creation, int duration, String errorMessage, boolean isOk
        return new GameDescription( 0, gameid, 0, game.getFactoryId(), game.getFactoryId(),  game.getPlayerIds().size(), game.getBoardSize(), game.getBoard(), new Date(),0, "ok",true);
    }
    */
    public void saveGame(GameSaveDTO gameInfo) {
        gameDAOSQL.saveGame(gameInfo);
    }

    @Override
    public TokenInfo[] getTokenList(String gameid) {
        Game game = activeGames.get(gameid);

        // Token[] onboardTokens = (Token[]) game.getBoard().values().toArray();
        //Token[] remainingTokens = (Token[]) game.getRemainingTokens().toArray();
        // Token[] allTokens = Stream.concat(Arrays.stream(onboardTokens), Arrays.stream(remainingTokens)).toArray(size->(Token[]) Array.newInstance(Token.class, size));
        TokenInfo[] tokenInfos = new TokenInfo[game.getBoard().size() + game.getRemainingTokens().size()];
        int idx = 0;
        for (Map.Entry<CellPosition, Token> set : game.getBoard().entrySet()) {
            Token t = set.getValue();
            tokenInfos[idx] = new TokenInfo(gameid, t.getPosition(), idx, t.getName(), t.canMove(), true);
            idx++;
        }
        for (Token t : game.getRemainingTokens()) {
            tokenInfos[idx] = new TokenInfo(gameid, t.getPosition(), idx, t.getName(), t.canMove(), false);
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
    public TokenInfo getTokenInfo(String gameid, CellPosition position) {
        Game game = activeGames.get(gameid);
        if (game == null) return new TokenInfo(gameid, position, -1, "no game", false, false);
        Token t = game.getBoard().get(position);
        boolean onBoard = false;
        int idx = -1;
        if (t == null) {
            onBoard = false;
            t = game.getRemainingTokens().iterator().next();
            idx = 0;
        } else {
            // Collection<Token> obtokens = game.getBoard().values();
            // idx = obtokens.stream().map(tp->( tp.isEqual(t))).indexOf( t);
            onBoard = true;
        }
        boolean canMove = (t == null) ? false : t.canMove();
        String name = (t == null) ? "null" : t.getName();
        return new TokenInfo(gameid, position, idx, name, canMove, onBoard);
    }

    @Override
    public MovedTokenResult moveToken(String gameid, int tokenIndex, CellPosition newpos) {
        Game game = activeGames.get(gameid);
        System.out.println("movetoken " + gameid + " " + tokenIndex);
        TokenInfo tokenInfo = gameTokens.get(gameid)[tokenIndex];
        Token token = (tokenInfo.onBoard()) ? game.getBoard().get(tokenInfo.position()) : (Token) game.getRemainingTokens().toArray()[tokenInfo.tokenIndex()];//-game.getBoard().size()
        MovedTokenResult result = null;
        if (token.canMove()) {
            try {
                token.moveTo(newpos);
                result = new MovedTokenResult(gameid, tokenIndex, newpos.x(), newpos.y(), true, "ok");
            } catch (InvalidPositionException e) {
                result = new MovedTokenResult(gameid, tokenIndex, newpos.x(), newpos.y(), false, "Impossible to move token: " + e.toString());
                //throw new RuntimeException(e);
            }
        } else {
            result = new MovedTokenResult(gameid, tokenIndex, newpos.x(), newpos.y(), false, "token cannot move");
        }
        //game.getBoard().computeIfAbsent()
        return result;//new MovedTokenResult( gameid, 0, );
    }

    @Override
    public void saveTokens(String gameid) {
        Game game = activeGames.get(gameid);
        for (Token t : game.getBoard().values()) {
            TokenEntity te = new TokenEntity();
            te.setGameId(gameid);
            te.setName(t.getName());
            te.setPositionX(Integer.valueOf(t.getPosition().x()));
            te.setPositionY(Integer.valueOf(t.getPosition().y()));
            te.setOnBoard(Boolean.TRUE);
            te.setCanMove(Boolean.valueOf(t.canMove()));
            tokenDAO.save(te);

        }
        for (Token t : game.getRemainingTokens()) {
            TokenEntity te = new TokenEntity();
            te.setGameId(gameid);
            te.setName(t.getName());
            te.setPositionX(Integer.valueOf(0));
            te.setPositionY(Integer.valueOf(0));
            te.setOnBoard(Boolean.TRUE);
            te.setCanMove(Boolean.valueOf(t.canMove()));
            tokenDAO.save(te);

        }
    }

    @Override
    public List<Token> readTokens(int gamid) {
        return null;
    }


    @Override
    public HashMap<String, Game> getAllGames() {
        return activeGames;
    }

    @PostConstruct
    private void readPersistentGames() {
        ArrayList<GameSaveDTO> gameDTOs = gameDAOSQL.readGames();
        //System.out.println("read persistent");
        for (GameSaveDTO gdto : gameDTOs) {
            GamePlugin plugin = getPlugins()[gdto.gameType()];
            //int gameIndex, String gameKey, int sqlid, String typeLocale, String typeName, int playerCount, int boardSize, Map<CellPosition, Token> board, Date creation, int duration, String errorMessage, boolean isOk
            Game game = plugin.createGame(new GameDescription(gdto.gameType(), gdto.gameKey(), gdto.sqlid(), plugin.getName(Locale.getDefault(), ticTacToePlugin), plugin.getName(getEndUserLocale(), ticTacToePlugin), plugin.getDefaultPlayerCount(), gdto.boardSize(), new HashMap<CellPosition, Token>(), new ArrayList<>(), new ArrayList<>(), gdto.creationDate().toInstant().getEpochSecond(), 0, "ok", true));
            activeGames.put(gdto.gameKey(), game);
            gameCreations.put(gdto.gameKey(), gdto.creationDate());
        }
    }

    @Override
    public String mockWriteStats() {
        String rep = "";
        for (int u = 1; u < 3; u++) {
            for (int i = 0; i < 10; i++) {
                int score = (int) (Math.round(Math.random() * 3) - 1);
                rep += restTemplate(new RestTemplateBuilder()).getForObject("http://127.0.0.1:8082/stats/" + u + "?score=" + score, String.class) + "\n";
            }
        }
        return rep;
    }

    private String postScore2statsApi(StatsDTO statsDTO) {
        URI uri = null;
        RestTemplate restTemplate = new RestTemplate();
        try {
            uri = new URI(rootURL + "/stats/" + statsDTO.playerID());
        } catch (URISyntaxException e) {
            System.err.println("uri error:" + e.toString());
            return e.toString();
        }
        return restTemplate.postForObject(uri, statsDTO, String.class);
    }

    @Override
    public StatsSummaryDTO getStatSummaryForPlayer(int playerid) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = null;
        try {
            uri = new URI(rootURL + "/stats/summary/" + playerid);
        } catch (URISyntaxException e) {
            System.err.println(e);
            return null;
        }
        return restTemplate.getForObject(uri, StatsSummaryDTO.class);
    }

    private String checkPlayerCount(GameFactory factory, int playerCount) {
        IntRange range = factory.getPlayerCountRange();
        if (!range.contains(playerCount)) return "playerCount should be between " + range.min() + " and " + range.max();
        return "";
    }

    private String checkBoardSize(GameFactory factory, int boardSize, int playerCount) {
        IntRange range = factory.getBoardSizeRange(playerCount);
        if (!range.contains(boardSize)) return "boardSize should be between " + range.min() + " and " + range.max();
        return "";
    }

}
