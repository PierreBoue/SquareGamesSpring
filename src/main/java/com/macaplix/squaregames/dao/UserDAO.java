package com.macaplix.squaregames.dao;

import com.macaplix.squaregames.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserDAO  extends CrudRepository<UserEntity, Integer> {

    UserEntity findByUsername(String username);

}
