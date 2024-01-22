package com.qwertz.quickjoin

import cc.polyfrost.oneconfig.utils.commands.CommandManager
import cc.polyfrost.oneconfig.utils.gui.GuiUtils
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import com.qwertz.quickjoin.command.QuickJoinCommand
import com.qwertz.quickjoin.gui.QuickJoinGui
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.SidedProxy
import org.lwjgl.input.Keyboard

@Mod(modid = QuickJoin.MODID, name = QuickJoin.NAME, version = QuickJoin.VERSION)
class QuickJoin {
    // Register the config and commands.

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent?) {
        config = com.qwertz.quickjoin.config.QuickJoinConfig()
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


