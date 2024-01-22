package com.qwertz.quickjoin.command

import cc.polyfrost.oneconfig.utils.commands.annotations.Command
import cc.polyfrost.oneconfig.utils.gui.GuiUtils
import com.qwertz.quickjoin.QuickJoin
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.server.MinecraftServer
import net.minecraft.client.Minecraft
import com.qwertz.quickjoin.gui.QuickJoinGui
@Command(value = QuickJoin.MODID, description = "Access the " + QuickJoin.NAME + " GUI.")
class QuickJoinCommand : CommandBase() {
    override fun getCommandName() = "quickjoin"

    override fun getCommandUsage(sender: ICommandSender) = "/quickjoin"

    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        // Ensure that this command is only executed on the client side

        GuiUtils.displayScreen(QuickJoinGui())
    }

    // Make sure the command can be used by any player
    override fun canCommandSenderUseCommand(sender: ICommandSender) = true
}