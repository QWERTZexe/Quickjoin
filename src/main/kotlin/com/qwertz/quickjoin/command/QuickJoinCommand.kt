package com.qwertz.quickjoin.command

import cc.polyfrost.oneconfig.libs.universal.utils.MCITextComponent
import cc.polyfrost.oneconfig.utils.commands.annotations.Command
import cc.polyfrost.oneconfig.utils.gui.GuiUtils
import com.qwertz.quickjoin.QuickJoin
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import com.qwertz.quickjoin.gui.QuickJoinGui
import com.qwertz.quickjoin.config.QuickJoinConfig
import com.qwertz.quickjoin.QuickJoin.Companion.config
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
val QJConfig = config

// Check the value of the enable/disable option for the current mod
class IsEnabled {
    fun EnabledCheck(): Boolean {
        if (QJConfig.enabled) {
            return true
        } else {
            return false
        }
    }
}
@Command(value = QuickJoin.MODID, description = "Access the " + QuickJoin.NAME + " GUI")
class QuickJoinCommand : CommandBase() {
    override fun getCommandName() = "quickjoin"

    override fun getCommandUsage(sender: ICommandSender) = "/quickjoin"

    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        // Ensure that this command is only executed on the client side
        if (IsEnabled().EnabledCheck()) {
            GuiUtils.displayScreen(QuickJoinGui())
        } else {
            Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("§4[§6§lQUICKJOIN§4]§a: The mod is disabled in OneConfig. Please enable it."))
    }}

    // Make sure the command can be used by any player
    override fun canCommandSenderUseCommand(sender: ICommandSender) = true

}