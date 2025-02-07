package com.manji.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.manji.app.dto.Fido2Authenticators;

@Repository
public interface Fido2AuthenticatorsRepository extends MongoRepository<Fido2Authenticators, String> {
  public Fido2Authenticators findByPrimaryKey(String primaryKey);
}
