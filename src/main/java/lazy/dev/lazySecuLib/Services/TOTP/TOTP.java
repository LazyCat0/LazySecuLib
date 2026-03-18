package lazy.dev.lazySecuLib.Services.TOTP;

public interface TOTP {
    String generateSecret();
    String getQrCodeUrl(String playerName, String secret);
    boolean verify(String secret, int code);
}
