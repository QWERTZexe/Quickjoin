package app.qwertz.quickjoin.command;

import app.qwertz.quickjoin.QuickJoin;
import app.qwertz.quickjoin.config.QuickJoinConfig;
import app.qwertz.quickjoin.gui.QuickJoinGui;
import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.gui.GuiUtils;
import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

@Command(value = QuickJoin.MODID, description = "Access the " + QuickJoin.NAME + " GUI")
public class QuickJoinCommand extends CommandBase {
    private QuickJoin mod;
    public QuickJoinCommand(QuickJoin mod) {
        this.mod = mod;
    }
    private final QuickJoinConfig QJConfig = QuickJoin.config;

    @Override
    public String getCommandName() {
        return "quickjoin";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/quickjoin";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (mod.isEnabled()) {
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
        } else {
            Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText("§4[§6§lQUICKJOIN§4]§a: The mod is disabled in OneConfig. Please enable it.")
            );
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
