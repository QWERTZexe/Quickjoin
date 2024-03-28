package app.qwertz.quickjoin.command

import app.qwertz.quickjoin.QuickJoin
import app.qwertz.quickjoin.QuickJoin.Companion.config
import app.qwertz.quickjoin.gui.QuickJoinGui
import cc.polyfrost.oneconfig.utils.commands.annotations.Command
import cc.polyfrost.oneconfig.utils.gui.GuiUtils
import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils
import net.minecraft.client.Minecraft
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText

val QJConfig = config

// Check the value of the enable/disable option for the current mod
class IsEnabled {
    fun enabledCheck(): Boolean {
        return QJConfig.enabled
    }
}
@Command(value = QuickJoin.MODID, description = "Access the " + QuickJoin.NAME + " GUI")
class QuickJoinCommand : CommandBase() {
    override fun getCommandName() = "quickjoin"

    override fun getCommandUsage(sender: ICommandSender) = "/quickjoin"

    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        // Ensure that this command is only executed on the client side
        if (IsEnabled().enabledCheck()) {
            if (HypixelUtils().isHypixel) {
                GuiUtils.displayScreen(QuickJoinGui())
            }
            else {
                Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("§4[§6§lQUICKJOIN§4]§a: You are not on Hypixel."))
            }
        }
        else {
            Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("§4[§6§lQUICKJOIN§4]§a: The mod is disabled in OneConfig. Please enable it."))
    }}

    // Make sure the command can be used by any player
    override fun canCommandSenderUseCommand(sender: ICommandSender) = true

}