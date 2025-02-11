package com.manji.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.manji.app.dto.FidoAuthenticator;

@Repository
public interface Fido2AuthenticatorsRepository extends MongoRepository<FidoAuthenticator, String> {
  public FidoAuthenticator findByPrimaryKey(String primaryKey);
}
