package app.qwertz.quickjoin

import app.qwertz.quickjoin.QuickJoin.Companion.config
import app.qwertz.quickjoin.command.IsEnabled
import app.qwertz.quickjoin.command.QuickJoinCommand
import app.qwertz.quickjoin.gui.QuickJoinGui
import cc.polyfrost.oneconfig.utils.gui.GuiUtils
import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod(modid = QuickJoin.MODID, name = QuickJoin.NAME, version = QuickJoin.VERSION)
class QuickJoin {
    // Register the config and commands.

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent?) {
        config = app.qwertz.quickjoin.config.QuickJoinConfig
        MinecraftForge.EVENT_BUS.register(CommandEventHandler())
        ClientCommandHandler.instance.registerCommand(QuickJoinCommand())
    }
    companion object {
        const val MODID: String = "@ID@"
        const val NAME: String = "@NAME@"
        const val VERSION: String = "@VER@"

        @Mod.Instance(MODID)
        lateinit var INSTANCE: QuickJoin
        lateinit var config: app.qwertz.quickjoin.config.QuickJoinConfig
    }
}



class CommandEventHandler {
    @SubscribeEvent
    fun onChatReceived(event: ClientChatReceivedEvent) {
        val message = event.message.unformattedText
        if (message.startsWith("Unknown command.") && config.EnableAlias) {
            val pattern = Regex("""'([^']*)'""")
            val matchResult = pattern.find(message)?.value
            val alias = config.CommandAlias
            if (matchResult == "'$alias'") {
                if (IsEnabled().enabledCheck()) {
                    GuiUtils.displayScreen(QuickJoinGui())
                } else {
                    if (HypixelUtils().isHypixel) {
                        if (config.DebugMode) {
                            Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: Displaying MainGui"))
                        }
                        GuiUtils.displayScreen(QuickJoinGui())
                    }
                    else {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("§4[§6§lQUICKJOIN§4]§a: You are not on Hypixel."))
                    }
                }

            }
        }
    }
}