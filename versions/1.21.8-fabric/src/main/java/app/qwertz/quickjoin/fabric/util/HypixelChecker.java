package app.qwertz.quickjoin.fabric.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;

import java.util.Locale;

public final class HypixelChecker {

    private HypixelChecker() {
    }

    public static boolean isOnHypixel(MinecraftClient client) {
        if (client == null) {
            return false;
        }
        if (client.isInSingleplayer()) {
            return false;
        }
        ServerInfo server = client.getCurrentServerEntry();
        if (server == null || server.address == null) {
            return false;
        }
        String address = server.address.toLowerCase(Locale.ROOT);
        return address.contains("hypixel.net");
    }
}
