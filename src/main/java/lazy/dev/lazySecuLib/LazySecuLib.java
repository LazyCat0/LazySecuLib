package lazy.dev.lazySecuLib;

import lazy.dev.lazySecuLib.Services.EncryptorService;
import lazy.dev.lazySecuLib.Services.TOTP.TOTPService;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

public final class LazySecuLib extends JavaPlugin {
    private EncryptorService encryptorService;

    @Override
    public void onEnable() {
        // EncryptorService initialize
        byte[] key = loadOrGenerateKey();
        this.encryptorService = new EncryptorService(key);
        // TotpService initialize
        getServer().getServicesManager().register(
                TOTPService.class,
                new TOTPService(),
                this,
                ServicePriority.Normal
        );
        getLogger().info("Library enabled!");
        saveDefaultConfig();
        if (Objects.equals(getConfig().getString("server_name"), "My cool server")) {
            getLogger().severe("Found default value in plugin config. Please, change it for better experience.");
        }
    }

    @Override
    public void onDisable() {
    }

    private byte[] loadOrGenerateKey() {
        String keyStr = getConfig().getString("secret-key");
        if (keyStr == null || keyStr.isEmpty()) {
            byte[] newKey = new byte[32];
            new SecureRandom().nextBytes(newKey);
            String encoded = Base64.getEncoder().encodeToString(newKey);

            getConfig().set("secret-key", encoded);
            saveConfig();
            return newKey;
        }
        return Base64.getDecoder().decode(keyStr);
    }
    public EncryptorService getEncryptorService() { return encryptorService; }
}
