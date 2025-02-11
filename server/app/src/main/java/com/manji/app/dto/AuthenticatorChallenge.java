package com.manji.app.dto;

import org.springframework.data.annotation.Id;
import org.springframework.security.web.webauthn.api.PublicKeyCredentialCreationOptions;
import java.util.Date;

import lombok.Data;

@Data
public class AuthenticatorChallenge {
  @Id
  String primaryKey;
  byte[] challenge;
  PublicKeyCredentialCreationOptions options;
  Date exp;
}
