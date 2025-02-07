package com.manji.app.controller;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Base64;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.manji.app.dto.Fido2Authenticators;
import com.manji.app.repository.Fido2AuthenticatorsRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class FidoController {
  private final Fido2AuthenticatorsRepository fido2AuthenticatorsRepository;

  @GetMapping("/rp/challenge")
  public ResponseEntity<String> getAuthChallenge() {

    // Generate 64 random bytes
    SecureRandom random = new SecureRandom();
    byte[] challengeBytes = new byte[64];
    random.nextBytes(challengeBytes);

    // Convert to Base64 string
    String base64String = Base64.getEncoder().encodeToString(challengeBytes);

    // Encode to base64url by replacing characters and removing padding
    String base64UrlString = base64String.replace("+", "-")
        .replace("/", "_")
        .replaceAll("=", "");

    Fido2Authenticators fido2Authenticators = new Fido2Authenticators();
    fido2Authenticators.setPrimaryKey(base64UrlString);
    fido2Authenticators.setExpireDate(new Date(System.currentTimeMillis()));
    fido2AuthenticatorsRepository.save(fido2Authenticators);

    return new ResponseEntity<>(base64UrlString, null, HttpStatus.OK);
  }

  @GetMapping("/rp/check")
  public ResponseEntity<String> getMethodName(@Param("key") String key) {
    Fido2Authenticators fido2Authenticators = fido2AuthenticatorsRepository.findByPrimaryKey(key);
    return new ResponseEntity<>(fido2Authenticators.getPrimaryKey(), null, HttpStatus.OK);
  }

}
