export const Login = () => {
  /**
   * 1. RPサーバーからチャレンジを取得
   */
  const getChallengeFromRpServer = async () => {};

  /**
   * 2. 取得したチャレンジ情報を元にブラウザのパスキー認証情報を取得
   */
  const startFidoAuth = async () => {
    navigator.credentials.get();
  };

  return <div>Login</div>;
};
