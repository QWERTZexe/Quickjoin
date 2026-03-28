package app.qwertz.quickjoin.fabric.screen;

import app.qwertz.quickjoin.data.Func;
import app.qwertz.quickjoin.fabric.QuickJoinFabric;
import app.qwertz.quickjoin.fabric.config.QuickJoinConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public final class QuickJoinActions {

    private QuickJoinActions() {
    }

    public static void execute(Minecraft client, String currentGui, Func func) {
        if (client == null || client.player == null || func == null) {
            return;
        }
        switch (func.type) {
            case "opengui" -> client.setScreen(new QuickJoinScreen(func.string));
            case "command" -> {
                String command = func.string;
                if (command.startsWith("/")) {
                    command = command.substring(1);
                }
                client.player.connection.sendUnattendedCommand(command, client.screen);
                client.setScreen(null);
            }
            case "chat" -> {
                if (client.gui != null && client.gui.getChat() != null) {
                    client.gui.getChat().addClientSystemMessage(Component.literal(func.string));
                }
                client.setScreen(null);
            }
            case "close" -> client.setScreen(null);
            case "repeat" -> {
                String cmd = func.string;
                if (cmd.startsWith("/")) {
                    cmd = cmd.substring(1);
                }
                for (int i = 0; i < Math.max(func.times, 1); i++) {
                    client.player.connection.sendUnattendedCommand(cmd, client.screen);
                }
                client.setScreen(null);
            }
            default -> {
                if (QuickJoinConfig.debugMode()) {
                    if (client.gui != null && client.gui.getChat() != null) {
                        client.gui.getChat().addClientSystemMessage(Component.literal("Unknown function type: " + func.type));
                    }
                }
            }
        }
    }
}
