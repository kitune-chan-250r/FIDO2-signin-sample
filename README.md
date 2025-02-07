# FIDO2 認証サンプル

## 構成
* Client: クライアントwebアプリ
* Server: FIDO2 RPサーバー兼アプリケーションサーバー
* MongoDB: FIDO2 認証チャレンジ情報の保存先
* PostgreSQL: アプリケーションデータの保存先

## コンテナの設定
### 拡張機能のインストール
devcontainer.jsonとDockerfileで使用する拡張機能をインストールするためのコードを記述

```json
"customizations": {
    "vscode": {
      "extensions": [
        "dbaeumer.vscode-eslint",
        "esbenp.prettier-vscode"
      ]
    },
}
```
```dockerfile
RUN code --install-extension dbaeumer.vscode-eslint
RUN code  --install-extension esbenp.prettier-vscode
```