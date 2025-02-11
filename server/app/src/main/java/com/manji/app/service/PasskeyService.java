package com.manji.app.service;

import org.springframework.stereotype.Service;

import com.manji.app.dto.AuthenticatorChallenge;
import com.manji.app.dto.RpPublicKeyCredentialUserImpl;
import com.manji.app.repository.AuthenticatorChallengeRepository;

import lombok.RequiredArgsConstructor;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import org.springframework.security.web.webauthn.api.AttestationConveyancePreference;
import org.springframework.security.web.webauthn.api.Bytes;
import org.springframework.security.web.webauthn.api.PublicKeyCredentialCreationOptions;
import org.springframework.security.web.webauthn.api.PublicKeyCredentialParameters;
import org.springframework.security.web.webauthn.api.PublicKeyCredentialRpEntity;

@Service
@RequiredArgsConstructor
public class PasskeyService {
  // email, passwordで認証する前提で実装

  private static final String RP_NAME = "ManjiRp";

  private final AuthenticatorChallengeRepository authenticatorChallengeRepository;

  /**
   * ブラウザ認証機に渡す各種情報の生成
   */
  public PublicKeyCredentialCreationOptions requestCredentialsChallenge(String userId, String loginId) {

    // 認証情報作成用のオプション作成
    PublicKeyCredentialCreationOptions generatedOption = this.generateRegistrationOptions(userId,
        loginId);

    // mongoDBに保存
    // かぶりが発生した場合等にcontrollerAdviceからエラーを返すように後で修正
    this.storeAuthenticatorChallenge(generatedOption);

    return generatedOption;
  }

  /**
   * FIDO2 Optionを生成
   */
  private PublicKeyCredentialCreationOptions generateRegistrationOptions(String userId,
      String loginId) {

    PublicKeyCredentialRpEntity RpEntity = PublicKeyCredentialRpEntity.builder()
        .id(RP_NAME)
        .name("nekoo.test.rp.server")
        .build();

    // 本来は、既に作成されているユーザー情報をコンストラクタに渡して作成する
    // idはUUIDのような識別子、userNameは基本ログインID、displayNameはログインIDでもいいし、あれば表示名
    RpPublicKeyCredentialUserImpl userEntity = new RpPublicKeyCredentialUserImpl(userId.getBytes(), loginId, loginId);

    PublicKeyCredentialParameters pubKeyCredParams = PublicKeyCredentialParameters.ES256;

    PublicKeyCredentialCreationOptions options = PublicKeyCredentialCreationOptions.builder().rp(RpEntity)
        .challenge(this.generateChallenge())
        .user(userEntity)
        .pubKeyCredParams(pubKeyCredParams)
        .attestation(AttestationConveyancePreference.NONE)
        .timeout(Duration.ofMinutes(6L))// タイムアウト6分
        .build();
    return options;
  }

  /**
   * チャレンジを生成
   */
  private Bytes generateChallenge() {
    // Generate 64 random bytes
    SecureRandom random = new SecureRandom();
    byte[] challengeBytes = new byte[64];
    random.nextBytes(challengeBytes);

    return new Bytes(challengeBytes);
  }

  private void storeAuthenticatorChallenge(PublicKeyCredentialCreationOptions options) {
    // タイムアウトする日時を計算
    Instant now = Instant.now();
    Instant expDate = now.plus(options.getTimeout());

    AuthenticatorChallenge challenge = new AuthenticatorChallenge();
    challenge.setPrimaryKey(options.getUser().getId().getBytes().toString());
    challenge.setChallenge(options.getChallenge().getBytes());
    challenge.setOptions(options);
    challenge.setExp(Date.from(expDate));

    authenticatorChallengeRepository.save(challenge);
  }
}
