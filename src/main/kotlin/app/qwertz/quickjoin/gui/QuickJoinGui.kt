package app.qwertz.quickjoin.gui
import app.qwertz.quickjoin.QuickJoin.Companion.config
import app.qwertz.quickjoin.config.guis
import app.qwertz.quickjoin.favorites.FavoritesManager
import app.qwertz.quickjoin.config.QuickJoinConfig
import cc.polyfrost.oneconfig.utils.gui.GuiUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ResourceLocation

class CheckConfig {
    fun boldCheck(): String {

        return if (config.BoldSwitch) {
            "§l"
        } else {
            ""
        }
    }
    fun colorCheck(): String {

        return if (config.ColorSwitch) {
            ""
        } else {
            "§f"
        }
    }
}

data class ButtonConfig(
    val id: Int,
    val x: String,
    val y: String,
    val width: Int,
    val height: Int,
    val label: String,
    val colorCode: String
)

data class Config(
    val maingui: Maingui,
    val guis: Map<String,Guii>
)

data class Maingui(
    val buttons: List<ButtonConfig>,
    val funcs: Map<String, Func>,
    val name: String
)
data class Guii(
    val buttons: List<ButtonConfig>,
    val funcs: Map<String, Func>,
    val name: String,
    val icon: Icon
)
data class Icon(
    val sheet: Int,
    val textureX: Int,
    val textureY: Int
)
data class Func(
    val type: String,
    val string: String,
    val times: Int
)

class QuickJoinGui(val guiname: String="QuickJoinGui") : GuiScreen() {
    val bconfig = guis
    val bold = CheckConfig().boldCheck()
    val color = CheckConfig().colorCheck()
    fun evaluateExpression(expression: String): Double {
        val modifiedexpression =
            expression.replace("centerx", (this.width / 2).toString()).replace("centery", (this.height / 2).toString())
        val result = modifiedexpression.split(" ")
        if (result[1] == "-") {
            return (result[0].toInt() - result[2].toInt()).toDouble()
        }
        if (result[1] == "+") {
            return (result[0].toInt() + result[2].toInt()).toDouble()
        }
        return 0.toDouble()
    }
    override fun initGui() {
        this.buttonList.clear()
        bconfig.guis[guiname]?.buttons?.forEach { buttonConfig ->
            val isFav = QuickJoinConfig.EnableFavorites && FavoritesManager.isFavorite(guiname, buttonConfig.id)
            val star = if (isFav) "✮ " else ""
            val label = "$star${buttonConfig.colorCode}$color$bold${buttonConfig.label}"
            val resultx = evaluateExpression(buttonConfig.x)
            val resulty = evaluateExpression(buttonConfig.y)
            if (config.DebugMode) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: Adding button $label"))
            }
            this.buttonList.add(GuiButton(buttonConfig.id, resultx.toInt(), resulty.toInt(), buttonConfig.width, buttonConfig.height, label))


        }

        // Add a quick access Favorites button on the main GUI
        if (guiname == "QuickJoinGui") {
            val topY = 8
            val rightX = this.width - 90
            if (QuickJoinConfig.EnableFavorites) {
                // Favorites on the far right, Search next to it on the left
                this.buttonList.add(GuiButton(7000, rightX, topY, 80, 20, "§6§lFavorites"))
                this.buttonList.add(GuiButton(7001, this.width - 180, topY, 80, 20, "§a§lSearch"))
            } else {
                // If favorites are disabled, put Search in the far right spot
                this.buttonList.add(GuiButton(7001, rightX, topY, 80, 20, "§a§lSearch"))
            }
        }


    }
    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        // NO DEBUG MESSAGES HERE - INTENTIONALLY!
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(ResourceLocation("quickjoin", "Icon${bconfig.guis[guiname]?.icon?.sheet.toString()}.png"))
        GlStateManager.color(1f, 1f, 1f, 1f)
        drawTexturedModalRect(this.width/2-38, this.height/2-125, bconfig.guis[guiname]!!.icon.textureX, bconfig.guis[guiname]!!.icon.textureY, 76, 76)
        this.drawCenteredString(this.fontRendererObj, bconfig.guis[guiname]?.name, this.width / 2, this.height / 2 - 45, 0xFFFFFF)


    }
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        if (mouseButton == 1 && QuickJoinConfig.EnableFavorites) { // Right click toggles favorite on the button under cursor
            for (btn in buttonList) {
                if (btn.id >= 0 && btn.mousePressed(mc, mouseX, mouseY)) {
                    val func = bconfig.guis[guiname]?.funcs?.get(btn.id.toString())
                    if (func == null || func.type == "opengui") {
                        if (config.DebugMode) {
                            Minecraft.getMinecraft().thePlayer.addChatMessage(
                                ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: Favorites are only for playable entries, not categories.")
                            )
                        }
                        return
                    }
                    val added = FavoritesManager.toggleFavorite(guiname, btn.id)
                    if (config.DebugMode) {
                        val state = if (added) "added to" else "removed from"
                        Minecraft.getMinecraft().thePlayer.addChatMessage(
                            ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: $state favorites: $guiname#${btn.id}")
                        )
                    }
                    initGui() // refresh stars
                    return
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton)
    }
    override fun actionPerformed(button: GuiButton) {
        if (button.id == 7000) {
            GuiUtils.displayScreen(FavoritesGui())
            return
        }
        if (button.id == 7001) {
            GuiUtils.displayScreen(SearchGui())
            return
        }
        val buttonpressedid = button.id.toString()
        bconfig.guis[guiname]?.funcs?.forEach { funcConfig ->
            if (funcConfig.key == buttonpressedid) {
                if (funcConfig.value.type == "opengui") {
                    if (config.DebugMode) {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: Displaying ${funcConfig.value.string}"))
                    }
                    GuiUtils.displayScreen(QuickJoinGui(funcConfig.value.string))
                }
                if (funcConfig.value.type == "command") {
                    if (config.DebugMode) {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: Sending command ${funcConfig.value.string}"))
                    }
                    Minecraft.getMinecraft().thePlayer.sendChatMessage(funcConfig.value.string)
                    GuiUtils.closeScreen()
                }
                if (funcConfig.value.type == "chat") {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(funcConfig.value.string))
                    GuiUtils.closeScreen()
                }
                if (funcConfig.value.type == "close") {
                    if (config.DebugMode) {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: Closing all GUIs"))
                    }
                    GuiUtils.closeScreen()
                }
                if (funcConfig.value.type == "repeat") {
                    if (config.DebugMode) {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: Spamming ${funcConfig.value.string} ${funcConfig.value.times} times"))
                    }
                    repeat(funcConfig.value.times) {
                        GuiUtils.closeScreen()
                        Minecraft.getMinecraft().thePlayer.sendChatMessage(funcConfig.value.string)
                    }
                }

            }
        }
    }
}