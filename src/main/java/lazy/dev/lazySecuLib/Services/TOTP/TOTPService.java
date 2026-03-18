package lazy.dev.lazySecuLib.Services.TOTP;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lazy.dev.lazySecuLib.LazySecuLib;
import org.bukkit.plugin.java.JavaPlugin;

public class TOTPService implements TOTP {
    private final JavaPlugin plugin = LazySecuLib.getPlugin(LazySecuLib.class);

    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();
    private final String serverName = plugin.getConfig().getString("server_name");
    @Override
    public String getQrCodeUrl(String playerName, String secret) {
        GoogleAuthenticatorKey key = new GoogleAuthenticatorKey.Builder(secret).build();
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL(serverName, playerName, key);
    }

    @Override
    public boolean verify(String secret, int code) {
        return gAuth.authorize(secret, code);
    }
    @Override
    public String generateSecret() {
        final GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key.getKey();
    }
}
