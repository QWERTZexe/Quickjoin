package app.qwertz.quickjoin.fabric.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

import java.util.Locale;

public final class HypixelChecker {

    private HypixelChecker() {
    }

    public static boolean isOnHypixel(Minecraft client) {
        if (client == null) {
            return false;
        }
        if (client.isLocalServer()) {
            return false;
        }
        ServerData server = client.getCurrentServer();
        if (server == null || server.ip == null) {
            return false;
        }
        String address = server.ip.toLowerCase(Locale.ROOT);
        return address.contains("hypixel.net");
    }
}
