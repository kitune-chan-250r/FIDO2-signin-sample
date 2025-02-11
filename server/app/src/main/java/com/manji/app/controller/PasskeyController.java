package com.manji.app.controller;

import java.security.SecureRandom;
import java.util.Date;
import java.util.UUID;
import java.util.Base64;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.webauthn.api.PublicKeyCredentialCreationOptions;
import org.springframework.stereotype.Controller;

import com.manji.app.dto.FidoAuthenticator;
import com.manji.app.repository.Fido2AuthenticatorsRepository;
import com.manji.app.service.PasskeyService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
// @RequestMapping("/api/v1/passkey")
public class PasskeyController {

  private final PasskeyService passkeyService;
  private final Fido2AuthenticatorsRepository fido2AuthenticatorsRepository;

  @RequestMapping("/hello")
  public ResponseEntity<String> hello() {
    return new ResponseEntity<>("Hello World", HttpStatus.OK);
  }

  @GetMapping("/rp/challenge")
  public ResponseEntity<PublicKeyCredentialCreationOptions> getAuthChallenge() {

    // Generate 64 random bytes
    // SecureRandom random = new SecureRandom();
    // byte[] challengeBytes = new byte[64];
    // random.nextBytes(challengeBytes);

    // // Convert to Base64 string
    // String base64String = Base64.getEncoder().encodeToString(challengeBytes);

    // // Encode to base64url by replacing characters and removing padding
    // String base64UrlString = base64String.replace("+", "-")
    // .replace("/", "_")
    // .replaceAll("=", "");

    // FidoAuthenticator fido2Authenticators = new FidoAuthenticator();
    // fido2Authenticators.setPrimaryKey(base64UrlString);
    // fido2Authenticators.setExpireDate(new Date(System.currentTimeMillis()));
    // fido2AuthenticatorsRepository.save(fido2Authenticators);
    String userId = UUID.randomUUID().toString();
    String loginId = "nekoo@example.com";
    PublicKeyCredentialCreationOptions options = passkeyService.requestCredentialsChallenge(userId, loginId);

    return new ResponseEntity<>(options, null, HttpStatus.OK);
  }

  @GetMapping("/rp/check")
  public ResponseEntity<String> getMethodName(@Param("key") String key) {
    FidoAuthenticator fido2Authenticators = fido2AuthenticatorsRepository.findByPrimaryKey(key);
    return new ResponseEntity<>(fido2Authenticators.getPrimaryKey(), null, HttpStatus.OK);
  }

}
