package com.macaplix.squareGames.dao;

import com.macaplix.squareGames.entities.TokenEntity;
import fr.le_campus_numerique.square_games.engine.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface TokenDAO extends CrudRepository<TokenEntity, Integer>
{

}
