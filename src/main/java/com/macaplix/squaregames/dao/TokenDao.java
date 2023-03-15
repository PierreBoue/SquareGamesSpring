package com.macaplix.squaregames.dao;

import com.macaplix.squaregames.entities.TokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface TokenDao extends CrudRepository<TokenEntity, Integer> {

}
