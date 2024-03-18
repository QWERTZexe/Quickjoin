package app.qwertz.quickjoin.config

import app.qwertz.quickjoin.QuickJoin
import app.qwertz.quickjoin.gui.QuickJoinGui
import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.KeyBind
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.annotations.Text
import cc.polyfrost.oneconfig.config.core.OneKeyBind
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.config.data.OptionSize
import cc.polyfrost.oneconfig.libs.universal.UKeyboard
import cc.polyfrost.oneconfig.utils.gui.GuiUtils
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText

/**
 * The main Config entrypoint that extends the Config type and inits the config options.
 * See [this link](https://docs.polyfrost.cc/oneconfig/config/adding-options) for more config Options
 */
class QuickJoinConfig : Config(Mod(QuickJoin.NAME, ModType.HYPIXEL, "/QuickJoin.png"), QuickJoin.MODID + ".json") {
    init {
        initialize()
        var config: app.qwertz.quickjoin.config.QuickJoinConfig = this // Assign the current instance of QuickJoinConfig to config
        registerKeyBind(QJKeyBind) {
            if (config.EnableKeyBind) {
                GuiUtils.displayScreen(QuickJoinGui())

            }
            else {
                Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("§4[§6§lQUICKJOIN§4]§a: The mod is disabled in OneConfig. Please enable it."))
            }
        }
    }

    @Switch(name = "BOLD BUTTONS",size = OptionSize.SINGLE)
    var BoldSwitch: Boolean = false

    @Switch(name = "COLORED BUTTONS", size = OptionSize.SINGLE)
    var ColorSwitch: Boolean = true

    @Switch(name = "ENABLE COMMAND ALIAS", size = OptionSize.SINGLE)
    var EnableAlias: Boolean = true

    @Text(name = "COMMAND ALIAS",size = OptionSize.SINGLE)
    var CommandAlias: String = "qj"

    @Switch(name = "ENABLE KEYBIND", size = OptionSize.SINGLE)
    var EnableKeyBind: Boolean = true

    @KeyBind(name = "KEYBIND", size = OptionSize.SINGLE)
    lateinit var QJKeyBind: OneKeyBind

}