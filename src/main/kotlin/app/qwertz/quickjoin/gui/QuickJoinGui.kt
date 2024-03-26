package app.qwertz.quickjoin.gui
import app.qwertz.quickjoin.QuickJoin.Companion.config
import app.qwertz.quickjoin.config.guis
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
            val label = "${buttonConfig.colorCode}$color$bold${buttonConfig.label}"
            val resultx = evaluateExpression(buttonConfig.x)
            val resulty = evaluateExpression(buttonConfig.y)
            this.buttonList.add(GuiButton(buttonConfig.id, resultx.toInt(), resulty.toInt(), buttonConfig.width, buttonConfig.height, label))


        }


    }
    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(ResourceLocation("quickjoin", "Icon${bconfig.guis[guiname]?.icon?.sheet.toString()}.png"))
        GlStateManager.color(1f, 1f, 1f, 1f)
        drawTexturedModalRect(this.width/2-38, this.height/2-130, bconfig.guis[guiname]!!.icon.textureX, bconfig.guis[guiname]!!.icon.textureY, 76, 76)
        this.drawCenteredString(this.fontRendererObj, bconfig.guis[guiname]?.name, this.width / 2, this.height / 2 - 50, 0xFFFFFF)


    }
    override fun actionPerformed(button: GuiButton) {
        val buttonpressedid = button.id.toString()
        bconfig.guis[guiname]?.funcs?.forEach { funcConfig ->
            if (funcConfig.key == buttonpressedid) {
                if (funcConfig.value.type == "opengui") {
                    GuiUtils.displayScreen(QuickJoinGui(funcConfig.value.string))
                }
                if (funcConfig.value.type == "command") {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage(funcConfig.value.string)
                    GuiUtils.closeScreen()
                }
                if (funcConfig.value.type == "chat") {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(funcConfig.value.string))
                    GuiUtils.closeScreen()
                }
                if (funcConfig.value.type == "close") {
                    GuiUtils.closeScreen()
                }
                if (funcConfig.value.type == "repeat") {
                    repeat(funcConfig.value.times) {
                        GuiUtils.closeScreen()
                        Minecraft.getMinecraft().thePlayer.sendChatMessage(funcConfig.value.string)
                    }
                }

            }
        }
    }
}