package com.qwertz.quickjoin

import cc.polyfrost.oneconfig.utils.commands.CommandManager
import cc.polyfrost.oneconfig.utils.gui.GuiUtils
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import com.qwertz.quickjoin.command.QuickJoinCommand
import net.minecraftforge.common.MinecraftForge
import com.qwertz.quickjoin.gui.QuickJoinGui
import net.minecraft.client.Minecraft
import com.qwertz.quickjoin.QuickJoin.Companion.config
import com.qwertz.quickjoin.command.IsEnabled
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.SidedProxy
import org.lwjgl.input.Keyboard
import net.minecraft.util.ChatComponentText
@Mod(modid = QuickJoin.MODID, name = QuickJoin.NAME, version = QuickJoin.VERSION)
class QuickJoin {
    // Register the config and commands.

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent?) {
        config = com.qwertz.quickjoin.config.QuickJoinConfig()
        MinecraftForge.EVENT_BUS.register(CommandEventHandler())
        ClientCommandHandler.instance.registerCommand(QuickJoinCommand())

    }
    companion object {
        const val MODID: String = "@ID@"
        const val NAME: String = "@NAME@"
        const val VERSION: String = "@VER@"

        @Mod.Instance(MODID)
        lateinit var INSTANCE: QuickJoin
        lateinit var config: com.qwertz.quickjoin.config.QuickJoinConfig
    }
}



class CommandEventHandler {
    @SubscribeEvent
    fun onChatReceived(event: ClientChatReceivedEvent) {
        val message = event.message.unformattedText
        if (message.startsWith("Unknown command.")) {
            val pattern = Regex("""'([^']*)'""")
            val matchResult = pattern.find(message)?.value
            val alias = config.CommandAlias
            if (matchResult == "'$alias'") {
                if (IsEnabled().EnabledCheck()) {
                    GuiUtils.displayScreen(QuickJoinGui())
                } else {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("§4[§6§lQUICKJOIN§4]§a: The mod is disabled in OneConfig. Please enable it."))
                }

            }
        }
    }
}