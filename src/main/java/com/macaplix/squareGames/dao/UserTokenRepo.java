package com.macaplix.squareGames.dao;

import com.macaplix.squareGames.entities.UserTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserTokenRepo extends CrudRepository<UserTokenEntity, Integer>
{

}
