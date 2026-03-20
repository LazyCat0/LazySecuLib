package lazy.dev.lazySecuLib;

import lazy.dev.lazySecuLib.Services.Crypto.*;
import lazy.dev.lazySecuLib.Services.TOTP.TOTPService;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

public final class LazySecuLib extends JavaPlugin {
    // Services
    private CryptoServiceAES cryptoServiceAES;
    private CryptoService3DES cryptoService3DES;
    private CryptoServiceDES cryptoServiceDES;
    private CryptoServiceBlowfish cryptoServiceBlowfish;
    public static byte[] key;
    private DeviceFingerPrintService deviceFingerPrintService;

    @Override
    public void onEnable() {
        // CryptoService initialize
        key = loadOrGenerateKey();
        this.cryptoServiceAES = new CryptoServiceAES(key);
        this.cryptoService3DES = new CryptoService3DES(key);
        this.cryptoServiceDES = new CryptoServiceDES(key);
        this.cryptoServiceBlowfish = new CryptoServiceBlowfish(key);
        // Services initialize
        this.deviceFingerPrintService = new DeviceFingerPrintService(this);
        getServer().getServicesManager().register(
                TOTPService.class,
                new TOTPService(),
                this,
                ServicePriority.Normal
        );

        getLogger().info("Library enabled!");
        saveConfig();
        if (Objects.equals(getConfig().getString("server_name"), "My cool server")) {
            getLogger().severe("Found default value in plugin config. Please, change it for better experience.");
        }
        if (!Objects.equals(getConfig().getInt("config_version", 1), 1)) {
            getLogger().severe("Found unknown config version. All reset to default");
            saveDefaultConfig();
        }
    }

    @Override
    public void onDisable() {
    }

    private byte[] loadOrGenerateKey() {
        String keyStr = getConfig().getString("k");
        if (keyStr == null || keyStr.isEmpty()) {
            byte[] newKey = new byte[32];
            new SecureRandom().nextBytes(newKey);
            String encoded = Base64.getEncoder().encodeToString(newKey);

            getConfig().set("k", encoded);
            saveConfig();
            return newKey;
        }
        return Base64.getDecoder().decode(keyStr);
    }

    public CryptoServiceAES getCryptoServiceAES() { return cryptoServiceAES; }

    public CryptoServiceBlowfish getCryptoServiceBlowfish() {
        return cryptoServiceBlowfish;
    }

    public  CryptoServiceDES getCryptoServiceDES() {
        return cryptoServiceDES;
    }

    public CryptoService3DES getCryptoService3DES() {
        return cryptoService3DES;
    }

    public DeviceFingerPrintService getDeviceFingerPrintService() {
        return deviceFingerPrintService;
    }
}
