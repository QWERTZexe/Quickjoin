package app.qwertz.quickjoin.config
import app.qwertz.quickjoin.QuickJoin
import app.qwertz.quickjoin.fallback.getJsonFallback
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
import cc.polyfrost.oneconfig.utils.NetworkUtils
import cc.polyfrost.oneconfig.utils.gui.GuiUtils
import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils
import com.google.gson.Gson
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText

/**
 * The main Config entrypoint that extends the Config type and inits the config options.
 * See [this link](https://docs.polyfrost.cc/oneconfig/config/adding-options) for more config Options
 */
var guis: app.qwertz.quickjoin.gui.Config = Gson().fromJson(getJsonFallback(), app.qwertz.quickjoin.gui.Config::class.java)

object QuickJoinConfig : Config(Mod(QuickJoin.NAME, ModType.HYPIXEL, "/QuickJoin.png"), QuickJoin.MODID + ".json") {

    @Switch(name = "Bold Buttons",size = OptionSize.SINGLE)
    var BoldSwitch: Boolean = true
    @Switch(name = "Colored Buttons", size = OptionSize.SINGLE)
    var ColorSwitch: Boolean = true
    @Switch(name = "Enable Command Alias", size = OptionSize.SINGLE)
    var EnableAlias: Boolean = true
    @Text(name = "Command Alias",size = OptionSize.SINGLE)
    var CommandAlias: String = "qj"
    @Switch(name = "Enable Keybind", size = OptionSize.SINGLE)
    var EnableKeyBind: Boolean = true
    @KeyBind(name = "Keybind", size = OptionSize.SINGLE)
    var QJKeyBind: OneKeyBind = OneKeyBind(UKeyboard.KEY_X)
    @Switch(name = "Debug Mode", size = OptionSize.SINGLE)
    var DebugMode: Boolean = false
    fun loadConfig(): app.qwertz.quickjoin.gui.Config {
        try {
            val json = NetworkUtils.getJsonElement("https://raw.githubusercontent.com/QWERTZexe/Quickjoin/main/src/main/resources/assets/quickjoin/guis.json").asJsonObject
            return Gson().fromJson(json, app.qwertz.quickjoin.gui.Config::class.java)
        }
        catch (e:Exception) {
            val json = getJsonFallback()
            return Gson().fromJson(json, app.qwertz.quickjoin.gui.Config::class.java)
        }

    }
    init {

        guis = loadConfig()
        var config: QuickJoinConfig = this // Assign the current instance of QuickJoinConfig to config
        initialize()
        registerKeyBind(QJKeyBind) {
            if (config.EnableKeyBind) {
                if (!config.enabled) {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("§4[§6§lQUICKJOIN§4]§a: The mod is disabled in OneConfig. Please enable it."))
                    }
                else {
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