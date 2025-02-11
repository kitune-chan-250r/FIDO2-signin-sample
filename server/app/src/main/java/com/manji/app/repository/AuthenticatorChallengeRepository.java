package com.manji.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.manji.app.dto.AuthenticatorChallenge;

@Repository
public interface AuthenticatorChallengeRepository extends MongoRepository<AuthenticatorChallenge, String> {

}
