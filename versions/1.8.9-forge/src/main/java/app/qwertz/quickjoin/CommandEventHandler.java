package app.qwertz.quickjoin;

import akka.io.Tcp;
import app.qwertz.quickjoin.config.QuickJoinConfig;
import app.qwertz.quickjoin.gui.QuickJoinGui;
import cc.polyfrost.oneconfig.utils.gui.GuiUtils;
import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandEventHandler {
    private QuickJoin mod;
    public CommandEventHandler(QuickJoin mod) {
        this.mod = mod;
    }
    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();

        if (message.startsWith("Unknown command.") && QuickJoin.config.EnableAlias) {
            Pattern pattern = Pattern.compile("'([^']*)'");
            Matcher matcher = pattern.matcher(message);
            String matchResult = matcher.find() ? matcher.group() : null;
            String alias = QuickJoin.config.CommandAlias;

            if (("'" + alias + "'").equals(matchResult)) {
                if (mod.isEnabled()) {
                    GuiUtils.displayScreen(new QuickJoinGui());
                } else {
                    if (new HypixelUtils().isHypixel()) {
                        if (QuickJoin.config.DebugMode) {
                            Minecraft.getMinecraft().thePlayer.addChatMessage(
                                    new ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: Displaying MainGui")
                            );
                        }
                        GuiUtils.displayScreen(new QuickJoinGui());
                    } else {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(
                                new ChatComponentText("§4[§6§lQUICKJOIN§4]§a: You are not on Hypixel.")
                        );
                    }
                }
            }
        }
    }
}
