package lazy.dev.lazySecuLib.Services.Crypto;

import lazy.dev.lazySecuLib.LazySecuLib;
import org.bukkit.entity.Player;

public class DeviceFingerPrintService {
    private final LazySecuLib lib;

    public DeviceFingerPrintService(LazySecuLib lib) {
        this.lib = lib;
    }

    public String generateFingerprint(Player player) {
        String rawData = player.getAddress().getHostString() +
                player.getLocale() +
                player.getClientBrandName();

        try {
            return lib.getCryptoServiceAES().encrypt(rawData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
