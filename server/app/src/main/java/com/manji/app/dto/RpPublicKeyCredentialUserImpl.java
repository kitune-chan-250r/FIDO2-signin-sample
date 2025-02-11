package com.manji.app.dto;

import org.springframework.security.web.webauthn.api.Bytes;
import org.springframework.security.web.webauthn.api.PublicKeyCredentialUserEntity;

public class RpPublicKeyCredentialUserImpl implements PublicKeyCredentialUserEntity {
  private Bytes id;
  private String name;
  private String displayName;

  public RpPublicKeyCredentialUserImpl(byte[] id, String name, String displayName) {
    this.id = new Bytes(id);
    this.name = name;
    this.displayName = displayName;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Bytes getId() {
    return this.id;
  }

  @Override
  public String getDisplayName() {
    return this.displayName;
  }
}
