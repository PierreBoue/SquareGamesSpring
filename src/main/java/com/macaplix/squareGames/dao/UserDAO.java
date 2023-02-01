package com.macaplix.squareGames.dao;

import com.macaplix.squareGames.entities.TokenEntity;
import com.macaplix.squareGames.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserDAO  extends CrudRepository<UserEntity, Integer> {

    UserEntity findByUsername(String username);

}
