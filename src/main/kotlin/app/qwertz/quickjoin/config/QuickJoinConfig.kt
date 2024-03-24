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
import com.google.gson.Gson
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import scala.tools.nsc.transform.patmat.Logic.PropositionalLogic.`False$`
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * The main Config entrypoint that extends the Config type and inits the config options.
 * See [this link](https://docs.polyfrost.cc/oneconfig/config/adding-options) for more config Options
 */
class QuickJoinConfig : Config(Mod(QuickJoin.NAME, ModType.HYPIXEL, "/QuickJoin.png"), QuickJoin.MODID + ".json") {

    fun loadConfig(): app.qwertz.quickjoin.gui.Config {
        val url = URL("https://github.com/QWERTZexe/Quickjoin/raw/main/src/main/resources/assets/quickjoin/guis.json")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()

        val inputStream = connection.inputStream
        val reader = BufferedReader(InputStreamReader(inputStream))
        val json = reader.readText()

        return Gson().fromJson(json, app.qwertz.quickjoin.gui.Config::class.java)
    }
    public var  guis = loadConfig()
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
    var QJKeyBind: OneKeyBind = OneKeyBind(UKeyboard.KEY_J)


    init {
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
}