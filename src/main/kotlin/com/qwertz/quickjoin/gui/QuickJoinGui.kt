package com.qwertz.quickjoin.gui
import cc.polyfrost.oneconfig.utils.gui.GuiUtils
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import net.minecraft.util.ResourceLocation
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import com.qwertz.quickjoin.QuickJoin.Companion.config
import com.qwertz.quickjoin.config.QuickJoinConfig
import net.minecraft.util.ChatComponentText

class CheckConfig {
    fun BoldCheck(): String {


            if (config.BoldSwitch) {
                return "§l"
            } else {
                return ""
            }
    }
    fun ColorCheck(): String {

        if (config.ColorSwitch) {
            return ""
        } else {
            return "§f"
        }
    }
}

val bold = CheckConfig().BoldCheck()
val color = CheckConfig().ColorCheck()
val Icon = ResourceLocation("quickjoin", "Icon.png")
val Icon2 = ResourceLocation("quickjoin", "Icon2.png")

class QuickJoinGui : GuiScreen() {
    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 201, this.height / 2 - 24, 76,20, "§2$color$bold" + "BEDWARS"))
        this.buttonList.add(GuiButton(1, this.width / 2 + 125, this.height / 2 - 24, 76,20,"§2$color$bold" + "SKYWARS"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 26, this.height / 2 - 24, 76,20,"§2$color$bold" + "DUELS"))
        this.buttonList.add(GuiButton(15, this.width / 2 - 201, this.height / 2 + 32, 76,20,"§2$color$bold" + "UHC"))
        this.buttonList.add(GuiButton(16, this.width / 2 -102, this.height / 2 + 32, 76,20,"§2$color$bold" + "BLITZ SG"))
        this.buttonList.add(GuiButton(18, this.width / 2 -102, this.height / 2 + 60, 76,20,"§2$color$bold" + "WARLORDS"))
        this.buttonList.add(GuiButton(17, this.width / 2 +26, this.height / 2 + 32, 76,20,"§2$color$bold" + "CVC"))
        this.buttonList.add(GuiButton(19, this.width / 2 +26, this.height / 2 + 60, 76,20,"§2$color$bold" + "SMASH"))
        this.buttonList.add(GuiButton(4, this.width / 2 + 88, this.height / 2 - 52, 76,20,"§6$color$bold" + "SKYBLOCK"))
        this.buttonList.add(GuiButton(8, this.width / 2 - 201, this.height / 2 + 4, 76,20,"§2$color$bold" + "MURDER"))
        this.buttonList.add(GuiButton(9, this.width / 2 + 125, this.height / 2 + 4, 76,20,"§2$color$bold" + "CLASSIC"))
        this.buttonList.add(GuiButton(10, this.width / 2 -102, this.height / 2 + 4, 76,20,"§2$color$bold" + "TNT"))
        this.buttonList.add(GuiButton(13, this.width / 2 + 125, this.height / 2 + 32, 76,20,"§2$color$bold" + "MEGA WALLS"))
        this.buttonList.add(GuiButton(14, this.width / 2 +26, this.height / 2 + 4, 76,20,"§2$color$bold" + "BUILDBATTLE"))
        this.buttonList.add(GuiButton(3, this.width / 2 - 102, this.height / 2 - 24, 76,20, "§2$color$bold" + "ARCADE"))

        this.buttonList.add(GuiButton(5, this.width / 2 - 162, this.height / 2 - 52, 76,20,"§b$color$bold" + "PIT"))
        this.buttonList.add(GuiButton(6, this.width / 2 - 162, this.height / 2 - 80, 76,20,"§b$color$bold" + "LIMBO"))
        this.buttonList.add(GuiButton(20, this.width / 2 - 162, this.height / 2 - 108, 76,20,"§b$color$bold" + "MAIN LOBBY"))
        this.buttonList.add(GuiButton(7, this.width / 2 + 88, this.height / 2 - 80, 76,20,"§b$color$bold" + "HOUSING"))
        this.buttonList.add(GuiButton(11, this.width / 2 + 88, this.height / 2 - 108, 76,20,"§b$color$bold" + "WOOL WARS"))

        this.buttonList.add(GuiButton(12, this.width / 2 - 25, this.height / 2 + 100, 50,20,"§c$color$bold" + "EXIT"))
    }
    // I HATE YOU RESOURCELOCATION!!! >:( //
    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {

        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-38, this.height/2-125, 0, 0, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "Quickjoin", this.width / 2, this.height / 2 - 50, 0xFFFFFF)


    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                GuiUtils.displayScreen(BedwarsGui())
            }
            1 -> {
                GuiUtils.displayScreen(SkywarsGui())
            }
            2 -> {
                GuiUtils.displayScreen(DuelsGui())
            }
            3 -> {
            GuiUtils.displayScreen(ArcadeGui())
        }
            8 -> {
                GuiUtils.displayScreen(MurderGui())
            }
            4 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play skyblock")
                GuiUtils.closeScreen()
            }
            5 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play pit")
                GuiUtils.closeScreen()
            }
            6 -> {
            GuiUtils.closeScreen()
                repeat(20) {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/")
                }

        }
            7 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/home")
                GuiUtils.closeScreen()
            }
            11 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play wool_wool_wars_two_four")
                GuiUtils.closeScreen()
            }

            12 -> {
                GuiUtils.closeScreen()
            }

            9 -> {
                GuiUtils.displayScreen(ClassicGui())
            }
            10 -> {
                GuiUtils.displayScreen(TNTGui())
            }
            13 -> {
                GuiUtils.displayScreen(MegaWallsGui())
            }
            14 -> {
                GuiUtils.displayScreen(BBGui())
            }
            15 -> {
                GuiUtils.displayScreen(UHCGui())
            }
            16 -> {
                GuiUtils.displayScreen(BSGGui())
            }
            17 -> {
                GuiUtils.displayScreen(CVCGui())
            }
            18 -> {
                GuiUtils.displayScreen(WarlordsGui())
            }
            19 -> {
                GuiUtils.displayScreen(SmashGui())
            }
            20 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/lobby main")
                GuiUtils.closeScreen()
            }

        }
    }
}
class BedwarsGui : GuiScreen() {
    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 162, this.height / 2 - 24, 76,20, "§b$color$bold" + "SOLO"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 37, this.height / 2 - 24, 76,20,"§b$color$bold" + "DOUBLES"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 88, this.height / 2 - 24, 76,20,"§b$color$bold" + "3v3v3v3"))
        this.buttonList.add(GuiButton(3, this.width / 2 - 162, this.height / 2 + 4, 76,20,"§b$color$bold" + "4v4v4v4"))
        this.buttonList.add(GuiButton(4, this.width / 2 - 37, this.height / 2 + 4, 76,20,"§b$color$bold" + "4v4"))
        this.buttonList.add(GuiButton(5, this.width / 2 + 88, this.height / 2 + 4, 76,20,"§2$color$bold" + "DREAMS"))
        this.buttonList.add(GuiButton(6, this.width / 2 - 25, this.height / 2 + 100, 50,20,"§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-38, this.height/2-130, 76, 0, 76, 76)

        this.drawCenteredString(this.fontRendererObj, "BEDWARS", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_eight_one")
                GuiUtils.closeScreen()
            }
            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_eight_two")
                GuiUtils.closeScreen()
            }
            2 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_four_three")
                GuiUtils.closeScreen()
            }
            3 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_four_four")
                GuiUtils.closeScreen()
            }
            4 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_two_four")
                GuiUtils.closeScreen()
            }
            5 -> {
                GuiUtils.displayScreen(BedwarsDreamsGui())
            }
            6 -> {
                GuiUtils.displayScreen(QuickJoinGui())
            }
        }
    }
}
class BedwarsDreamsGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 213, this.height / 2 - 24, 76,20, "§2$color$bold" + "RUSH 2s"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 114, this.height / 2 - 24, 76,20,"§2$color$bold" + "RUSH 4s"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 38, this.height / 2 - 24, 76,20,"§4$color$bold" + "ULTIMATE 2s"))
        this.buttonList.add(GuiButton(3, this.width / 2 + 137, this.height / 2 - 24, 76,20,"§4$color$bold" + "ULTIMATE 4s"))
        this.buttonList.add(GuiButton(4, this.width / 2 - 213, this.height / 2 + 4, 76,20,"§d$color$bold" + "LUCKY 2s"))
        this.buttonList.add(GuiButton(5, this.width / 2 - 114, this.height / 2 + 4, 76,20,"§d$color$bold" + "LUCKY 4s"))
        this.buttonList.add(GuiButton(6, this.width / 2 + 38, this.height / 2 + 4, 76,20,"§a$color$bold" + "VOIDLESS 2s"))
        this.buttonList.add(GuiButton(7, this.width / 2 + 137, this.height / 2 + 4, 76,20,"§a$color$bold" + "VOIDLESS 4s"))
        this.buttonList.add(GuiButton(8, this.width / 2 - 213, this.height / 2 + 32, 76,20,"§e$color$bold" + "ARMED 2s"))
        this.buttonList.add(GuiButton(9, this.width / 2 - 114, this.height / 2 + 32, 76,20,"§e$color$bold" + "ARMED 4s"))
        this.buttonList.add(GuiButton(10, this.width / 2 + 87, this.height / 2 + 32, 76,20,"§b$color$bold" + "CASTLE"))
        this.buttonList.add(GuiButton(11, this.width / 2 - 25, this.height / 2 + 100, 50,20,"§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-38, this.height/2-130, 76, 0, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "BEDWARS - DREAMS", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_eight_two_rush")
                GuiUtils.closeScreen()
            }
            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_four_four_rush")
                GuiUtils.closeScreen()
            }
            2 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_eight_two_ultimate")
                GuiUtils.closeScreen()
            }
            3 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_four_four_ultimate")
                GuiUtils.closeScreen()
            }
            4 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_eight_two_lucky")
                GuiUtils.closeScreen()
            }
            5 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_four_four_lucky")
                GuiUtils.closeScreen()
            }
            6 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_eight_two_voidless")
                GuiUtils.closeScreen()
            }
            7 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_four_four_voidless")
                GuiUtils.closeScreen()
            }
            8 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_eight_two_armed")
                GuiUtils.closeScreen()
            }
            9 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_four_four_armed")
                GuiUtils.closeScreen()
            }
            10 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_castle")
                GuiUtils.closeScreen()
            }
            11 -> {
                GuiUtils.displayScreen(BedwarsGui())
            }
        }
    }
}
class SkywarsGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 213, this.height / 2 - 24, 76, 20, "§a$color$bold" + "NORMAL SOLO"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§a$color$bold" + "NORMAL 2s"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§4$color$bold" + "INSANE SOLO"))
        this.buttonList.add(GuiButton(3, this.width / 2 + 137, this.height / 2 - 24, 76, 20, "§4$color$bold" + "INSANE 2s"))
        this.buttonList.add(GuiButton(4, this.width / 2 - 213, this.height / 2 + 4, 76, 20, "§6$color$bold" + "MEGA SOLO"))
        this.buttonList.add(GuiButton(5, this.width / 2 - 114, this.height / 2 + 4, 76, 20, "§6$color$bold" + "MEGA 2s"))
        this.buttonList.add(GuiButton(6, this.width / 2 + 87, this.height / 2 + 4, 76, 20, "§2$color$bold" + "LABORATORY"))
        this.buttonList.add(GuiButton(7, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-38, this.height/2-130, 152, 0, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "SKYWARS", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }
    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play solo_normal")
                GuiUtils.closeScreen()
            }
            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play teams_normal")
                GuiUtils.closeScreen()
            }
            2 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play solo_insane")
                GuiUtils.closeScreen()
            }
            3 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play teams_insane")
                GuiUtils.closeScreen()
            }
            4 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play mega_normal")
                GuiUtils.closeScreen()
            }
            5 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play mega_doubles")
                GuiUtils.closeScreen()
            }
            6 -> {
                GuiUtils.displayScreen(SkywarsLabGui())
            }
            7 -> {
                GuiUtils.displayScreen(QuickJoinGui())
            }
        }
    }
}
class SkywarsLabGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 213, this.height / 2 - 24, 76, 20, "§d$color$bold" + "LUCKY SOLO"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§d$color$bold" + "LUCKY 2s"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§4$color$bold" + "TNT SOLO"))
        this.buttonList.add(GuiButton(3, this.width / 2 + 137, this.height / 2 - 24, 76, 20, "§4$color$bold" + "TNT 2s"))
        this.buttonList.add(GuiButton(4, this.width / 2 - 213, this.height / 2 + 4, 76, 20, "§e$color$bold" + "RUSH SOLO"))
        this.buttonList.add(GuiButton(5, this.width / 2 - 114, this.height / 2 + 4, 76, 20, "§e$color$bold" + "RUSH 2s"))
        this.buttonList.add(GuiButton(6, this.width / 2 + 38, this.height / 2 + 4, 76, 20, "§a$color$bold" + "SLIME SOLO"))
        this.buttonList.add(GuiButton(7, this.width / 2 + 137, this.height / 2 + 4, 76, 20, "§a$color$bold" + "SLIME 2s"))
        this.buttonList.add(GuiButton(8, this.width / 2 - 50, this.height / 2 + 32, 100, 20, "§b$color$bold" + "HUNTER VS BEASTS"))
        this.buttonList.add(GuiButton(9, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-38, this.height/2-130, 152, 0, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "SKYWARS - LABORATORY", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play solo_insane_lucky")
                GuiUtils.closeScreen()
            }

            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play teams_insane_lucky")
                GuiUtils.closeScreen()
            }

            2 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play solo_insane_tnt_madness")
                GuiUtils.closeScreen()
            }

            3 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play teams_insane_tnt_madness")
                GuiUtils.closeScreen()
            }
            4 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play solo_insane_rush")
                GuiUtils.closeScreen()
            }

            5 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play teams_insane_rush")
                GuiUtils.closeScreen()
            }
            6 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play solo_insane_slime")
                GuiUtils.closeScreen()
            }

            7 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play teams_insane_slime")
                GuiUtils.closeScreen()
            }

            8 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play solo_insane_hunters_vs_beasts")
                GuiUtils.closeScreen()
            }

            9 -> {
                GuiUtils.displayScreen(SkywarsGui())
            }
        }
    }
}
class DuelsGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 213, this.height / 2 - 24, 76, 20, "§a$color$bold" + "CLASSIC"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§a$color$bold" + "SUMO"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§4$color$bold" + "MEGA WALLS"))
        this.buttonList.add(GuiButton(3, this.width / 2 + 137, this.height / 2 - 24, 76, 20, "§4$color$bold" + "BRIDGE"))

        this.buttonList.add(GuiButton(4, this.width / 2 - 213, this.height / 2 + 4, 76, 20, "§a$color$bold" + "PARKOUR"))
        this.buttonList.add(GuiButton(5, this.width / 2 - 114, this.height / 2 + 4, 76, 20, "§a$color$bold" + "NO DEBUFF"))
        this.buttonList.add(GuiButton(6, this.width / 2 + 38, this.height / 2 + 4, 76, 20, "§4$color$bold" + "SKYWARS"))
        this.buttonList.add(GuiButton(7, this.width / 2 + 137, this.height / 2 + 4, 76, 20, "§4$color$bold" + "UHC"))

        this.buttonList.add(GuiButton(8, this.width / 2 - 213, this.height / 2 + 32, 76, 20, "§a$color$bold" + "BLITZ"))
        this.buttonList.add(GuiButton(9, this.width / 2 - 114, this.height / 2 + 32, 76, 20, "§a$color$bold" + "COMBO"))
        this.buttonList.add(GuiButton(10, this.width / 2 + 88, this.height / 2 + 32, 76, 20, "§4$color$bold" + "OP"))

        this.buttonList.add(GuiButton(11, this.width / 2 - 213, this.height / 2 + 60, 51, 20, "§a$color$bold" + "BOW"))
        this.buttonList.add(GuiButton(12, this.width / 2 - 150, this.height / 2 + 60, 50, 20, "§a$color$bold" + "BOXING"))
        this.buttonList.add(GuiButton(13, this.width / 2 - 88, this.height / 2 + 60, 51, 20, "§a$color$bold" + "TNT"))

        this.buttonList.add(GuiButton(14, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-38, this.height/2-130, 0, 76, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "DUELS", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }
    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_classic_duel")
                GuiUtils.closeScreen()
            }
            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_sumo_duel")
                GuiUtils.closeScreen()
            }
            4 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_parkour_eight")
                GuiUtils.closeScreen()
            }
            5 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_potion_duel")
                GuiUtils.closeScreen()
            }
            8 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_blitz_duel")
                GuiUtils.closeScreen()
            }
            9 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_combo_duel")
                GuiUtils.closeScreen()
            }
            11 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_bow_duel")
                GuiUtils.closeScreen()
            }
            12 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_blitz_duel")
                GuiUtils.closeScreen()
            }
            13 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_boxing_duel")
                GuiUtils.closeScreen()
            }
            14 -> {
                GuiUtils.displayScreen(QuickJoinGui())
            }
            2 -> {
                GuiUtils.displayScreen(DuelsMWGui())
            }
            3 -> {
                GuiUtils.displayScreen(DuelsBridgeGui())
            }
            6 -> {
                GuiUtils.displayScreen(DuelsSkyWarsGui())
            }
            7 -> {
            GuiUtils.displayScreen(DuelsUHCGui())
            }
            10 -> {
                GuiUtils.displayScreen(DuelsOPGui())
            }
        }
    }
}
class DuelsMWGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§b$color$bold" + "MEGAWALLS 1s"))
        this.buttonList.add(GuiButton(1, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§b$color$bold" + "MEGAWALLS 2s"))
        this.buttonList.add(GuiButton(2, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-38, this.height/2-130, 0, 76, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "DUELS - MEGA WALLS", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_mw_duel")
                GuiUtils.closeScreen()
            }

            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_mw_doubles")
                GuiUtils.closeScreen()
            }

            2 -> {
                GuiUtils.displayScreen(DuelsGui())
            }
        }
    }
}
class DuelsBridgeGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 213, this.height / 2 - 24, 76, 20, "§b$color$bold" + "BRIDGE 1v1"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§b$color$bold" + "BRIDGE 2v2"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§b$color$bold" + "BRIDGE 3v3"))
        this.buttonList.add(GuiButton(3, this.width / 2 + 137, this.height / 2 - 24, 76, 20, "§b$color$bold" + "BRIDGE 4v4"))
        this.buttonList.add(GuiButton(4, this.width / 2 - 213, this.height / 2 + 24, 85, 20, "§4$color$bold" + "BRIDGE 2v2v2v2"))
        this.buttonList.add(GuiButton(5, this.width / 2 + 129, this.height / 2 + 24, 85, 20, "§4$color$bold" + "BRIDGE 3v3v3v3"))
        this.buttonList.add(GuiButton(6, this.width / 2 - 43, this.height / 2 + 24, 85, 20, "§1$color$bold" + "BRIDGE CTF 3v3"))
        this.buttonList.add(GuiButton(7, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-38, this.height/2-130, 0, 76, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "DUELS - BRIDGE", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_bridge_duel")
                GuiUtils.closeScreen()
            }

            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_bridge_doubles")
                GuiUtils.closeScreen()
            }
            2 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_bridge_threes")
                GuiUtils.closeScreen()
            }

            3 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_bridge_four")
                GuiUtils.closeScreen()
            }
            4 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_bridge_2v2v2v2")
                GuiUtils.closeScreen()
            }
            5 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_bridge_3v3v3v3")
                GuiUtils.closeScreen()
            }
            6 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_capture_threes")
                GuiUtils.closeScreen()
            }
            7 -> {
                GuiUtils.displayScreen(DuelsGui())
            }
        }
    }
}
class DuelsSkyWarsGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§b$color$bold" + "SKYWARS 1s"))
        this.buttonList.add(GuiButton(1, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§b$color$bold" + "SKYWARS 2s"))
        this.buttonList.add(GuiButton(2, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-38, this.height/2-130, 0, 76, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "DUELS - SKYWARS", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_sw_duel")
                GuiUtils.closeScreen()
            }

            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_sw_doubles")
                GuiUtils.closeScreen()
            }

            2 -> {
                GuiUtils.displayScreen(DuelsGui())
            }
        }
    }
}
class DuelsUHCGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 213, this.height / 2 - 24, 76, 20, "§b$color$bold" + "UHC 1v1"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§b$color$bold" + "UHC 2v2"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§b$color$bold" + "UHC 4v4"))
        this.buttonList.add(GuiButton(3, this.width / 2 + 137, this.height / 2 - 24, 76, 20, "§4$color$bold" + "UHC FFA"))
        this.buttonList.add(GuiButton(4, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-38, this.height/2-130, 0, 76, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "DUELS - UHC", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_uhc_duel")
                GuiUtils.closeScreen()
            }

            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_uhc_doubles")
                GuiUtils.closeScreen()
            }
            2 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_uhc_four")
                GuiUtils.closeScreen()
            }

            3 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_uhc_meetup")
                GuiUtils.closeScreen()
            }
            4 -> {
                GuiUtils.displayScreen(DuelsGui())
            }
        }
    }
}
class DuelsOPGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§b$color$bold" + "OP SOLO"))
        this.buttonList.add(GuiButton(1, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§b$color$bold" + "OP DOUBLES"))
        this.buttonList.add(GuiButton(2, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-38, this.height/2-130, 0, 76, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "DUELS - OP", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_op_duel")
                GuiUtils.closeScreen()
            }

            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_op_doubles")
                GuiUtils.closeScreen()
            }

            2 -> {
                GuiUtils.displayScreen(DuelsGui())
            }
        }
    }
}
class ArcadeGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 201, this.height / 2 - 24, 76, 20, "§b$color$bold" + "DROPPER"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 102, this.height / 2 - 24, 76, 20, "§b$color$bold" + "ZOMBIES"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 26, this.height / 2 - 24, 76, 20, "§b$color$bold" + "PARTY GAMES"))
        this.buttonList.add(GuiButton(3, this.width / 2 + 125   , this.height / 2 - 24, 76, 20, "§b$color$bold" + "PIXEL PARTY"))

        this.buttonList.add(GuiButton(4, this.width / 2 - 201, this.height / 2 + 4, 76, 20, "§a$color$bold" + "BLOCKING DEAD"))
        this.buttonList.add(GuiButton(5, this.width / 2 - 102, this.height / 2 + 4, 76, 20, "§a$color$bold" + "CTW"))
        this.buttonList.add(GuiButton(6, this.width / 2 + 26, this.height / 2 + 4, 76, 20, "§a$color$bold" + "CREEPER"))
        this.buttonList.add(GuiButton(7, this.width / 2 + 125, this.height / 2 + 4, 76, 20, "§a$color$bold" + "DRAGON WARS"))

        this.buttonList.add(GuiButton(8, this.width / 2 - 201, this.height / 2 + 32, 76, 20, "§b$color$bold" + "ENDER SPLEEF"))
        this.buttonList.add(GuiButton(9, this.width / 2 - 102, this.height / 2 + 32, 76, 20, "§b$color$bold" + "FARM HUNT"))
        this.buttonList.add(GuiButton(10, this.width / 2 + 26, this.height / 2 + 32, 76, 20, "§b$color$bold" + "FOOTBALL"))
        this.buttonList.add(GuiButton(11, this.width / 2 + 125, this.height / 2 + 32, 76, 20, "§b$color$bold" + "GALAXY WARS"))

        this.buttonList.add(GuiButton(12, this.width / 2 - 201, this.height / 2 + 60, 76, 20, "§a$color$bold" + "HIDE AND SEEK"))
        this.buttonList.add(GuiButton(13, this.width / 2 - 102, this.height / 2 + 60, 76, 20, "§a$color$bold" + "HITW"))
        this.buttonList.add(GuiButton(14, this.width / 2 + 26, this.height / 2 + 60, 76, 20, "§a$color$bold" + "THROW OUT"))
        this.buttonList.add(GuiButton(15, this.width / 2 + 125, this.height / 2 + 60, 76, 20, "§a$color$bold" + "PAINTERS"))

        this.buttonList.add(GuiButton(16, this.width / 2 - 201, this.height / 2 - 52, 76, 20, "§a$color$bold" + "HYPIXEL SAYS"))
        this.buttonList.add(GuiButton(17, this.width / 2 + 125, this.height / 2 - 52, 76, 20, "§a$color$bold" + "MINI WALLS"))

        this.buttonList.add(GuiButton(18, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-38, this.height/2-130, 76, 76, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "ARCADE", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }
    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_dropper")
                GuiUtils.closeScreen()
            }
            2 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_party_games_1")
                GuiUtils.closeScreen()
            }
            3 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_pixel_party")
                GuiUtils.closeScreen()
            }
            4 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_day_one")
                GuiUtils.closeScreen()
            }
            5 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_pvp_ctw")
                GuiUtils.closeScreen()
            }
            6 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_creeper_defense")
                GuiUtils.closeScreen()
            }
            7 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_dragon_wars")
                GuiUtils.closeScreen()
            }
            8 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_ender_spleef")
                GuiUtils.closeScreen()
            }
            9 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_farm_hunt")
                GuiUtils.closeScreen()
            }
            10 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_soccer")
                GuiUtils.closeScreen()
            }
            11 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_starwars")
                GuiUtils.closeScreen()
            }
            13 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_hole_in_the_wall")
                GuiUtils.closeScreen()
            }
            14 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_throw_out")
                GuiUtils.closeScreen()
            }
            15 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_pixel_painters")
                GuiUtils.closeScreen()
            }
            16 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_simon_says")
                GuiUtils.closeScreen()
            }
            17 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_mini_walls")
                GuiUtils.closeScreen()
            }
            18 -> {
                GuiUtils.displayScreen(QuickJoinGui())
            }
            12 -> {
                GuiUtils.displayScreen(ArcadeHideAndSeekGui())
            }
            1 -> {
                GuiUtils.displayScreen(ArcadeZombiesGui())
            }
        }
    }
}
class ArcadeHideAndSeekGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§b$color$bold" + "PROP HUNT"))
        this.buttonList.add(GuiButton(1, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§b$color$bold" + "PARTY POOPER"))
        this.buttonList.add(GuiButton(2, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-38, this.height/2-130, 76, 76, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "ARCADE - HIDE AND SEEK", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_hide_and_seek_prop_hunt")
                GuiUtils.closeScreen()
            }

            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_hide_and_seek_party_pooper")
                GuiUtils.closeScreen()
            }

            2 -> {
                GuiUtils.displayScreen(ArcadeGui())
            }
        }
    }
}
class ArcadeZombiesGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(
            GuiButton(
                0,
                this.width / 2 - 213,
                this.height / 2 - 24,
                85,
                20,
                "§b$color$bold" + "DEAD END"
            )
        )
        this.buttonList.add(
            GuiButton(
                1,
                this.width / 2 - 43,
                this.height / 2 - 24,
                85,
                20,
                "§b$color$bold" + "BAD BLOOD"
            )
        )
        this.buttonList.add(
            GuiButton(
                2,
                this.width / 2 + 129,
                this.height / 2 - 24,
                85,
                20,
                "§b$color$bold" + "ALIEN ARCADIUM"
            )
        )
        this.buttonList.add(GuiButton(3, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width / 2 - 38, this.height / 2 - 130, 76, 76, 76, 76)
        this.drawCenteredString(
            this.fontRendererObj,
            "ARCADE - ZOMBIES",
            this.width / 2,
            this.height / 2 - 50,
            0xFFFFFF
        )

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_zombies_dead_end")
                GuiUtils.closeScreen()
            }

            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_zombies_bad_blood")
                GuiUtils.closeScreen()
            }

            2 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_zombies_alien_arcadium")
                GuiUtils.closeScreen()
            }

            3 -> {
                GuiUtils.displayScreen(ArcadeGui())
            }
        }
    }

    class DuelsBridgeGui : GuiScreen() {

        private val bold = CheckConfig().BoldCheck()
        private val color = CheckConfig().ColorCheck()
        val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
        private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
        override fun initGui() {
            this.buttonList.clear()
            this.buttonList.add(
                GuiButton(
                    0,
                    this.width / 2 - 213,
                    this.height / 2 - 24,
                    76,
                    20,
                    "§b$color$bold" + "BRIDGE 1v1"
                )
            )
            this.buttonList.add(
                GuiButton(
                    1,
                    this.width / 2 - 114,
                    this.height / 2 - 24,
                    76,
                    20,
                    "§b$color$bold" + "BRIDGE 2v2"
                )
            )
            this.buttonList.add(
                GuiButton(
                    2,
                    this.width / 2 + 38,
                    this.height / 2 - 24,
                    76,
                    20,
                    "§b$color$bold" + "BRIDGE 3v3"
                )
            )
            this.buttonList.add(
                GuiButton(
                    3,
                    this.width / 2 + 137,
                    this.height / 2 - 24,
                    76,
                    20,
                    "§b$color$bold" + "BRIDGE 4v4"
                )
            )
            this.buttonList.add(
                GuiButton(
                    4,
                    this.width / 2 - 213,
                    this.height / 2 + 24,
                    85,
                    20,
                    "§4$color$bold" + "BRIDGE 2v2v2v2"
                )
            )
            this.buttonList.add(
                GuiButton(
                    5,
                    this.width / 2 + 129,
                    this.height / 2 + 24,
                    85,
                    20,
                    "§4$color$bold" + "BRIDGE 3v3v3v3"
                )
            )
            this.buttonList.add(
                GuiButton(
                    6,
                    this.width / 2 - 43,
                    this.height / 2 + 24,
                    85,
                    20,
                    "§1$color$bold" + "BRIDGE CTF 3v3"
                )
            )
            this.buttonList.add(
                GuiButton(
                    7,
                    this.width / 2 - 25,
                    this.height / 2 + 100,
                    50,
                    20,
                    "§c$color$bold" + "BACK"
                )
            )
        }

        override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
            drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
            super.drawScreen(mouseX, mouseY, partialTicks)
            mc.textureManager.bindTexture(Icon)
            drawTexturedModalRect(this.width / 2 - 38, this.height / 2 - 130, 0, 76, 76, 76)
            this.drawCenteredString(
                this.fontRendererObj,
                "DUELS - BRIDGE",
                this.width / 2,
                this.height / 2 - 50,
                0xFFFFFF
            )

        }

        override fun actionPerformed(button: GuiButton) {
            when (button.id) {
                0 -> {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_bridge_duel")
                    GuiUtils.closeScreen()
                }

                1 -> {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_bridge_doubles")
                    GuiUtils.closeScreen()
                }

                2 -> {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_bridge_threes")
                    GuiUtils.closeScreen()
                }

                3 -> {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_bridge_four")
                    GuiUtils.closeScreen()
                }

                4 -> {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_bridge_2v2v2v2")
                    GuiUtils.closeScreen()
                }

                5 -> {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_bridge_3v3v3v3")
                    GuiUtils.closeScreen()
                }

                6 -> {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/play duels_capture_threes")
                    GuiUtils.closeScreen()
                }

                7 -> {
                    GuiUtils.displayScreen(DuelsGui())
                }
            }
        }
    }
}
class MurderGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(
            GuiButton(
                0,
                this.width / 2 - 213,
                this.height / 2 - 24,
                76,
                20,
                "§b$color$bold" + "CLASSIC"
            )
        )
        this.buttonList.add(
            GuiButton(
                1,
                this.width / 2 - 114,
                this.height / 2 - 24,
                76,
                20,
                "§b$color$bold" + "DOUBLE UP"
            )
        )
        this.buttonList.add(
            GuiButton(
                2,
                this.width / 2 + 38,
                this.height / 2 - 24,
                76,
                20,
                "§b$color$bold" + "ASSASSINS"
            )
        )
        this.buttonList.add(
            GuiButton(
                3,
                this.width / 2 + 137,
                this.height / 2 - 24,
                76,
                20,
                "§b$color$bold" + "INFECTION"
            )
        )
        this.buttonList.add(GuiButton(4, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width / 2 - 38, this.height / 2 - 130, 152, 76, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "MURDER MYSTERY", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play murder_classic")
                GuiUtils.closeScreen()
            }

            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play murder_double_up")
                GuiUtils.closeScreen()
            }

            2 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play murder_assassins")
                GuiUtils.closeScreen()
            }

            3 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play murder_infection")
                GuiUtils.closeScreen()
            }
            4 -> {
                GuiUtils.displayScreen(QuickJoinGui())
            }
        }
    }
}
class ClassicGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 213, this.height / 2 - 24, 76, 20, "§b$color$bold" + "VAMPIREZ"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§b$color$bold" + "TKR"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§b$color$bold" + "WALLS"))
        this.buttonList.add(GuiButton(3, this.width / 2 + 137, this.height / 2 - 24, 76, 20, "§b$color$bold" + "PAINTBALL"))
        this.buttonList.add(GuiButton(4, this.width / 2 - 162, this.height / 2 + 4, 76, 20, "§3$color$bold" + "ARENA 1v1"))
        this.buttonList.add(GuiButton(5, this.width / 2 - 38, this.height / 2 + 4, 76, 20, "§3$color$bold" + "ARENA 2v2"))
        this.buttonList.add(GuiButton(6, this.width / 2 + 88, this.height / 2 + 4, 76, 20, "§3$color$bold" + "ARENA 4v4"))
        this.buttonList.add(GuiButton(7, this.width / 2 - 114, this.height / 2 + 32, 76, 20, "§6$color$bold" + "QUAKE SOLO"))
        this.buttonList.add(GuiButton(8, this.width / 2 + 38, this.height / 2 + 32, 76, 20, "§6$color$bold" + "QUAKE TEAMS"))

        this.buttonList.add(GuiButton(9, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-38, this.height/2-130, 0, 152, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "CLASSIC GAMES", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play vampirez")
                GuiUtils.closeScreen()
            }

            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play tkr")
                GuiUtils.closeScreen()
            }
            2 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play walls")
                GuiUtils.closeScreen()
            }

            3 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play paintball")
                GuiUtils.closeScreen()
            }
            4 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arena_1v1")
                GuiUtils.closeScreen()
            }

            5 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arena_2v2")
                GuiUtils.closeScreen()
            }
            6 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arena_4v4")
                GuiUtils.closeScreen()
            }

            7 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play quake_solo")
                GuiUtils.closeScreen()
            }
            8 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play quake_teams")
                GuiUtils.closeScreen()
            }
            9 -> {
                GuiUtils.displayScreen(QuickJoinGui())
            }
        }
    }
}
class TNTGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 213, this.height / 2 - 24, 76, 20, "§b$color$bold" + "TNT RUN"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§b$color$bold" + "TNT TAG"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§b$color$bold" + "TNT WIZARDS"))
        this.buttonList.add(GuiButton(3, this.width / 2 + 137, this.height / 2 - 24, 76, 20, "§b$color$bold" + "PVP RUN"))
        this.buttonList.add(GuiButton(4, this.width / 2 - 38, this.height / 2 + 4, 76, 20, "§b$color$bold" + "BOW SPLEEF"))

        this.buttonList.add(GuiButton(5, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-38, this.height/2-130, 76, 152, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "TNT", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play tnt_tntrun")
                GuiUtils.closeScreen()
            }

            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play tnt_tntag")
                GuiUtils.closeScreen()
            }
            2 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play tnt_capture")
                GuiUtils.closeScreen()
            }

            3 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play tnt_pvprun")
                GuiUtils.closeScreen()
            }
            4 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play tnt_bowspleef")
                GuiUtils.closeScreen()
            }
            5 -> {
                GuiUtils.displayScreen(QuickJoinGui())
            }
        }
    }
}
class MegaWallsGui : GuiScreen() {
    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 162, this.height / 2 - 24, 76,20, "§b$color$bold" + "FACEOFF"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 37, this.height / 2 - 24, 76,20,"§b$color$bold" + "STANDARD"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 88, this.height / 2 - 24, 76,20,"§b$color$bold" + "CHALLENGE"))
        this.buttonList.add(GuiButton(6, this.width / 2 - 25, this.height / 2 + 100, 50,20,"§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-38, this.height/2-130, 152, 152, 76, 76)

        this.drawCenteredString(this.fontRendererObj, "MEGA WALLS", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play mw_face_off")
                GuiUtils.closeScreen()
            }
            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play mw_standard")
                GuiUtils.closeScreen()
            }
            2 -> {
                Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("§4[§6§lQUICKJOIN§4]§a: This command has not been found - yet. If you found it make sure to open an issue on our github page!"))
                GuiUtils.closeScreen()
            }
            6 -> {
                GuiUtils.displayScreen(QuickJoinGui())
            }
        }
    }
}
class BBGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(
            GuiButton(
                0,
                this.width / 2 - 213,
                this.height / 2 - 24,
                76,
                20,
                "§b$color$bold" + "SOLO"
            )
        )
        this.buttonList.add(
            GuiButton(
                1,
                this.width / 2 - 114,
                this.height / 2 - 24,
                76,
                20,
                "§b$color$bold" + "TEAMS"
            )
        )
        this.buttonList.add(
            GuiButton(
                2,
                this.width / 2 + 38,
                this.height / 2 - 24,
                76,
                20,
                "§b$color$bold" + "SOLO PRO"
            )
        )
        this.buttonList.add(
            GuiButton(
                3,
                this.width / 2 + 137,
                this.height / 2 - 24,
                76,
                20,
                "§b$color$bold" + "GTB"
            )
        )
        this.buttonList.add(GuiButton(4, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon2)
        drawTexturedModalRect(this.width / 2 - 38, this.height / 2 - 130, 0, 0, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "BUILD BATTLE", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play build_battle_solo_normal")
                GuiUtils.closeScreen()
            }

            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play build_battle_teams_normal")
                GuiUtils.closeScreen()
            }

            2 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play build_battle_solo_pro")
                GuiUtils.closeScreen()
            }

            3 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play build_battle_guess_the_build")
                GuiUtils.closeScreen()
            }
            4 -> {
                GuiUtils.displayScreen(QuickJoinGui())
            }
        }
    }
}
class UHCGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(
            GuiButton(
                0,
                this.width / 2 - 213,
                this.height / 2 - 24,
                76,
                20,
                "§b$color$bold" + "SPEED SOLO"
            )
        )
        this.buttonList.add(
            GuiButton(
                1,
                this.width / 2 - 114,
                this.height / 2 - 24,
                76,
                20,
                "§b$color$bold" + "SPEED TEAMS"
            )
        )
        this.buttonList.add(
            GuiButton(
                2,
                this.width / 2 + 38,
                this.height / 2 - 24,
                76,
                20,
                "§b$color$bold" + "UHC SOLO"
            )
        )
        this.buttonList.add(
            GuiButton(
                3,
                this.width / 2 + 137,
                this.height / 2 - 24,
                76,
                20,
                "§b$color$bold" + "UHC TEAMS"
            )
        )
        this.buttonList.add(GuiButton(4, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon2)
        drawTexturedModalRect(this.width / 2 - 38, this.height / 2 - 130, 76, 0, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "UHC", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play speed_solo_normal")
                GuiUtils.closeScreen()
            }

            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play speed_team_normal")
                GuiUtils.closeScreen()
            }

            2 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play uhc_solo")
                GuiUtils.closeScreen()
            }

            3 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play uhc_teams")
                GuiUtils.closeScreen()
            }
            4 -> {
                GuiUtils.displayScreen(QuickJoinGui())
            }
        }
    }
}
class BSGGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§b$color$bold" + "SOLO"))
        this.buttonList.add(GuiButton(1, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§b$color$bold" + "TEAMS"))
        this.buttonList.add(GuiButton(2, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon2)
        drawTexturedModalRect(this.width/2-38, this.height/2-130, 152, 0, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "BLITZ SURVIVAL GAMES", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play blitz_solo_normal")
                GuiUtils.closeScreen()
            }

            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play blitz_teams_normal")
                GuiUtils.closeScreen()
            }

            2 -> {
                GuiUtils.displayScreen(QuickJoinGui())
            }
        }
    }
}
class CVCGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(
            GuiButton(
                0,
                this.width / 2 - 213,
                this.height / 2 - 24,
                76,
                20,
                "§b$color$bold" + "DEFUSAL"
            )
        )
        this.buttonList.add(
            GuiButton(
                1,
                this.width / 2 - 114,
                this.height / 2 - 24,
                76,
                20,
                "§b$color$bold" + "CHALLENGE"
            )
        )
        this.buttonList.add(
            GuiButton(
                2,
                this.width / 2 + 38,
                this.height / 2 - 24,
                76,
                20,
                "§b$color$bold" + "GUN GAME"
            )
        )
        this.buttonList.add(
            GuiButton(
                3,
                this.width / 2 + 137,
                this.height / 2 - 24,
                76,
                20,
                "§b$color$bold" + "TEAM DM"
            )
        )
        this.buttonList.add(GuiButton(4, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon2)
        drawTexturedModalRect(this.width / 2 - 38, this.height / 2 - 130, 0, 76, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "COPS AND CRIMS", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play mcgo_normal")
                GuiUtils.closeScreen()
            }

            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play mcgo_normal_party")
                GuiUtils.closeScreen()
            }

            2 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play mcgo_gungame")
                GuiUtils.closeScreen()
            }

            3 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play mcgo_deathmatch")
                GuiUtils.closeScreen()
            }
            4 -> {
                GuiUtils.displayScreen(QuickJoinGui())
            }
        }
    }
}
class WarlordsGui : GuiScreen() {
    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 162, this.height / 2 - 24, 76,20, "§b$color$bold" + "CTF"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 37, this.height / 2 - 24, 76,20,"§b$color$bold" + "DOMINATION"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 88, this.height / 2 - 24, 76,20,"§b$color$bold" + "TEAM DM"))
        this.buttonList.add(GuiButton(6, this.width / 2 - 25, this.height / 2 + 100, 50,20,"§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon2)
        drawTexturedModalRect(this.width/2-38, this.height/2-130, 76, 76, 76, 76)

        this.drawCenteredString(this.fontRendererObj, "WARLORDS", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play warlords_ctf_mini")
                GuiUtils.closeScreen()
            }
            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play warlords_domination")
                GuiUtils.closeScreen()
            }
            2 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play warlords_team_deathmatch")
                GuiUtils.closeScreen()
            }
            6 -> {
                GuiUtils.displayScreen(QuickJoinGui())
            }
        }
    }
}
class SmashGui : GuiScreen() {

    private val bold = CheckConfig().BoldCheck()
    private val color = CheckConfig().ColorCheck()
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 162, this.height / 2 - 24, 76, 20, "§b$color$bold" + "SOLO"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 38, this.height / 2 - 24, 76, 20, "§b$color$bold" + "FRIENDS"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 88, this.height / 2 - 24, 76, 20, "§b$color$bold" + "TEAMS"))
        this.buttonList.add(GuiButton(3, this.width / 2 - 114, this.height / 2 + 4, 76, 20, "§d$color$bold" + "1v1"))
        this.buttonList.add(GuiButton(4, this.width / 2 + 38, this.height / 2 + 4, 76, 20, "§d$color$bold" + "2v2"))

        this.buttonList.add(GuiButton(5, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§c$color$bold" + "BACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon2)
        drawTexturedModalRect(this.width/2-38, this.height/2-130, 152, 76, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "SMASH HEROES", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play super_smash_solo_normal")
                GuiUtils.closeScreen()
            }

            1 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play super_smash_friends_normal")
                GuiUtils.closeScreen()
            }
            2 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play super_smash_teams_normal")
                GuiUtils.closeScreen()
            }

            3 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play super_smash_1v1_normal")
                GuiUtils.closeScreen()
            }
            4 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play super_smash_2v2_normal")
                GuiUtils.closeScreen()
            }
            5 -> {
                GuiUtils.displayScreen(QuickJoinGui())
            }
        }
    }
}