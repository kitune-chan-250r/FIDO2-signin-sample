package com.manji.app.dto;

import java.util.Date;
import org.springframework.data.annotation.Id;

import lombok.Data;

/**
 * 認証チャレンジテーブル
 */
@Data
public class FidoAuthenticator {
  @Id
  String primaryKey;
  Date expireDate;
}
