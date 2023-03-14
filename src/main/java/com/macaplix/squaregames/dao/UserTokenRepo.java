package com.macaplix.squaregames.dao;

import com.macaplix.squaregames.entities.UserTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserTokenRepo extends CrudRepository<UserTokenEntity, Integer>
{

}
