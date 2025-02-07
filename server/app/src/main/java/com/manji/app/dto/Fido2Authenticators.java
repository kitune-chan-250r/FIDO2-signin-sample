package com.manji.app.dto;

import java.util.Date;
import org.springframework.data.annotation.Id;

import lombok.Data;

/**
 * 認証チャレンジテーブル
 */
@Data
public class Fido2Authenticators {
  @Id
  String primaryKey;
  Date expireDate;
}
