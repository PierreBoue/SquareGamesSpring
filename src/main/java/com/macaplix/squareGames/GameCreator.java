package com.macaplix.squareGames;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGame;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import fr.le_campus_numerique.square_games.engine.IntRange;
import org.springframework.stereotype.Service;

@Service
public class GameCreator {
    Game activeGame;
    public String createGame( int gameIndex, int playerCount, int boardSize)
    {
       //Game game;
        GameFactory factory;
        switch (gameIndex)
        {
            case 0:
                factory = new TicTacToeGameFactory( );
                break;
            case 1:
                factory = new TaquinGameFactory();
                break;
            case 2:
                factory = new ConnectFourGameFactory();
                break;
            default:
                return "Game Index out of range";
        }
        String checkAnswer = checkPlayerCount(factory, playerCount);
        if ( ! checkAnswer.isBlank()) return checkAnswer;
        checkAnswer = checkBoardSize(factory, boardSize);
        if ( ! checkAnswer.isBlank()) return checkAnswer;
        activeGame = factory.createGame(playerCount, boardSize);
        return factory.getGameId() + " created";
    }
    private String checkPlayerCount( GameFactory factory, int playerCount )
    {
        IntRange range = factory.getPlayerCountRange();
        if ( !range.contains(playerCount)) return "playerCount should be between "
        return "";
    }
    private String checkBoardSize( GameFactory factory, int boardSize)
    {

        return "";
    }
}
