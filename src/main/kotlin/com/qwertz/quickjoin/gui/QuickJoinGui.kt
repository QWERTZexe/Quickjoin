package com.qwertz.quickjoin.gui
import cc.polyfrost.oneconfig.utils.gui.GuiUtils
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import net.minecraft.util.ResourceLocation
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
val Icon = ResourceLocation("quickjoin", "Icon.png")
class QuickJoinGui : GuiScreen() {
    val resourceLocation = ResourceLocation("quickjoin", "Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 162, this.height / 2 - 24, 76,20, "§aBEDWARS"))
        this.buttonList.add(GuiButton(1, this.width / 2 + 88, this.height / 2 - 24, 76,20,"§bSKYWARS"))
        this.buttonList.add(GuiButton(2, this.width / 2 - 37, this.height / 2 - 24, 76,20,"§2DUELS"))
        this.buttonList.add(GuiButton(3, this.width / 2 - 37, this.height / 2 + 4, 76,20,"§eARCADE"))
        this.buttonList.add(GuiButton(4, this.width / 2 + 88, this.height / 2 - 52, 76,20,"§6SKYBLOCK"))
        this.buttonList.add(GuiButton(5, this.width / 2 - 162, this.height / 2 - 52, 76,20,"§dPIT"))
        this.buttonList.add(GuiButton(12, this.width / 2 - 25, this.height / 2 + 100, 50,20,"§cEXIT"))
    }
    // I HATE YOU RESOURCELOCATION!!! >:( //
    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {

        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-37, this.height/2-125, 0, 0, 76, 76)
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
            4 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play skyblock")
                GuiUtils.closeScreen()
            }
            5 -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play pit")
                GuiUtils.closeScreen()
            }
            12 -> {
                GuiUtils.closeScreen()
            }
        }
    }
}
class BedwarsGui : GuiScreen() {
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 162, this.height / 2 - 24, 76,20, "§bSOLO"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 37, this.height / 2 - 24, 76,20,"§bDOUBLES"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 88, this.height / 2 - 24, 76,20,"§b3v3v3v3"))
        this.buttonList.add(GuiButton(3, this.width / 2 - 162, this.height / 2 + 4, 76,20,"§b4v4v4v4"))
        this.buttonList.add(GuiButton(4, this.width / 2 - 37, this.height / 2 + 4, 76,20,"§b4v4"))
        this.buttonList.add(GuiButton(5, this.width / 2 + 88, this.height / 2 + 4, 76,20,"§2DREAMS"))
        this.buttonList.add(GuiButton(6, this.width / 2 - 25, this.height / 2 + 100, 50,20,"§cBACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-37, this.height/2-130, 76, 0, 76, 76)

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
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 213, this.height / 2 - 24, 76,20, "§2RUSH 2s"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 114, this.height / 2 - 24, 76,20,"§2RUSH 4s"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 38, this.height / 2 - 24, 76,20,"§4ULTIMATE 2s"))
        this.buttonList.add(GuiButton(3, this.width / 2 + 137, this.height / 2 - 24, 76,20,"§4ULTIMATE 4s"))
        this.buttonList.add(GuiButton(4, this.width / 2 - 213, this.height / 2 + 4, 76,20,"§dLUCKY 2s"))
        this.buttonList.add(GuiButton(5, this.width / 2 - 114, this.height / 2 + 4, 76,20,"§dLUCKY 4s"))
        this.buttonList.add(GuiButton(6, this.width / 2 + 38, this.height / 2 + 4, 76,20,"§aVOIDLESS 2s"))
        this.buttonList.add(GuiButton(7, this.width / 2 + 137, this.height / 2 + 4, 76,20,"§aVOIDLESS 4s"))
        this.buttonList.add(GuiButton(8, this.width / 2 - 213, this.height / 2 + 32, 76,20,"§eARMED 2s"))
        this.buttonList.add(GuiButton(9, this.width / 2 - 114, this.height / 2 + 32, 76,20,"§eARMED 4s"))
        this.buttonList.add(GuiButton(10, this.width / 2 + 87, this.height / 2 + 32, 76,20,"§bCASTLE"))
        this.buttonList.add(GuiButton(11, this.width / 2 - 25, this.height / 2 + 100, 50,20,"§cBACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-37, this.height/2-130, 76, 0, 76, 76)
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
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 213, this.height / 2 - 24, 76, 20, "§aNORMAL SOLO"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§aNORMAL 2s"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§4INSANE SOLO"))
        this.buttonList.add(GuiButton(3, this.width / 2 + 137, this.height / 2 - 24, 76, 20, "§4INSANE 2s"))
        this.buttonList.add(GuiButton(4, this.width / 2 - 213, this.height / 2 + 4, 76, 20, "§6MEGA SOLO"))
        this.buttonList.add(GuiButton(5, this.width / 2 - 114, this.height / 2 + 4, 76, 20, "§6MEGA 2s"))
        this.buttonList.add(GuiButton(6, this.width / 2 + 87, this.height / 2 + 4, 76, 20, "§2LABORATORY"))
        this.buttonList.add(GuiButton(7, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§cBACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-37, this.height/2-130, 152, 0, 76, 76)
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
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 213, this.height / 2 - 24, 76, 20, "§dLUCKY SOLO"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§dLUCKY 2s"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§4TNT SOLO"))
        this.buttonList.add(GuiButton(3, this.width / 2 + 137, this.height / 2 - 24, 76, 20, "§4TNT 2s"))
        this.buttonList.add(GuiButton(4, this.width / 2 - 213, this.height / 2 + 4, 76, 20, "§eRUSH SOLO"))
        this.buttonList.add(GuiButton(5, this.width / 2 - 114, this.height / 2 + 4, 76, 20, "§eRUSH 2s"))
        this.buttonList.add(GuiButton(6, this.width / 2 + 38, this.height / 2 + 4, 76, 20, "§aSLIME SOLO"))
        this.buttonList.add(GuiButton(7, this.width / 2 + 137, this.height / 2 + 4, 76, 20, "§aSLIME 2s"))
        this.buttonList.add(GuiButton(8, this.width / 2 - 50, this.height / 2 + 32, 100, 20, "§bHUNTER VS BEASTS"))
        this.buttonList.add(GuiButton(9, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§cBACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-37, this.height/2-130, 152, 0, 76, 76)
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
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 213, this.height / 2 - 24, 76, 20, "§aCLASSIC"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§aSUMO"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§4MEGA WALLS"))
        this.buttonList.add(GuiButton(3, this.width / 2 + 137, this.height / 2 - 24, 76, 20, "§4BRIDGE"))

        this.buttonList.add(GuiButton(4, this.width / 2 - 213, this.height / 2 + 4, 76, 20, "§aPARKOUR"))
        this.buttonList.add(GuiButton(5, this.width / 2 - 114, this.height / 2 + 4, 76, 20, "§aNO DEBUFF"))
        this.buttonList.add(GuiButton(6, this.width / 2 + 38, this.height / 2 + 4, 76, 20, "§4SKYWARS"))
        this.buttonList.add(GuiButton(7, this.width / 2 + 137, this.height / 2 + 4, 76, 20, "§4UHC"))

        this.buttonList.add(GuiButton(8, this.width / 2 - 213, this.height / 2 + 32, 76, 20, "§aBLITZ"))
        this.buttonList.add(GuiButton(9, this.width / 2 - 114, this.height / 2 + 32, 76, 20, "§aCOMBO"))
        this.buttonList.add(GuiButton(10, this.width / 2 + 88, this.height / 2 + 32, 76, 20, "§4OP"))

        this.buttonList.add(GuiButton(11, this.width / 2 - 213, this.height / 2 + 60, 51, 20, "§aBOW"))
        this.buttonList.add(GuiButton(12, this.width / 2 - 150, this.height / 2 + 60, 50, 20, "§aBOXING"))
        this.buttonList.add(GuiButton(13, this.width / 2 - 88, this.height / 2 + 60, 51, 20, "§aTNT"))

        this.buttonList.add(GuiButton(14, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§cBACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-37, this.height/2-130, 0, 76, 76, 76)
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
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§bMEGAWALLS 1s"))
        this.buttonList.add(GuiButton(1, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§bMEGAWALLS 2s"))
        this.buttonList.add(GuiButton(2, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§cBACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-37, this.height/2-130, 0, 76, 76, 76)
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
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 213, this.height / 2 - 24, 76, 20, "§bBRIDGE 1v1"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§bBRIDGE 2v2"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§bBRIDGE 3v3"))
        this.buttonList.add(GuiButton(3, this.width / 2 + 137, this.height / 2 - 24, 76, 20, "§bBRIDGE 4v4"))
        this.buttonList.add(GuiButton(4, this.width / 2 - 213, this.height / 2 + 24, 85, 20, "§4BRIDGE 2v2v2v2"))
        this.buttonList.add(GuiButton(5, this.width / 2 + 129, this.height / 2 + 24, 85, 20, "§4BRIDGE 3v3v3v3"))
        this.buttonList.add(GuiButton(6, this.width / 2 - 43, this.height / 2 + 24, 85, 20, "§1BRIDGE CTF 3v3"))
        this.buttonList.add(GuiButton(7, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§cBACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-37, this.height/2-130, 0, 76, 76, 76)
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
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§bSKYWARS 1s"))
        this.buttonList.add(GuiButton(1, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§bSKYWARS 2s"))
        this.buttonList.add(GuiButton(2, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§cBACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-37, this.height/2-130, 0, 76, 76, 76)
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
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 213, this.height / 2 - 24, 76, 20, "§bUHC 1v1"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§bUHC 2v2"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§bUHC 4v4"))
        this.buttonList.add(GuiButton(3, this.width / 2 + 137, this.height / 2 - 24, 76, 20, "§4UHC FFA"))
        this.buttonList.add(GuiButton(4, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§cBACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-37, this.height/2-130, 0, 76, 76, 76)
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
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 114, this.height / 2 - 24, 76, 20, "§bOP SOLO"))
        this.buttonList.add(GuiButton(1, this.width / 2 + 38, this.height / 2 - 24, 76, 20, "§bOP DOUBLES"))
        this.buttonList.add(GuiButton(2, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§cBACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-37, this.height/2-130, 0, 76, 76, 76)
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
    val resourceLocation = ResourceLocation("quickjoin", "gui/Background.png")
    private val backgroundTexture = ResourceLocation("quickjoin", "gui/Background.png")
    override fun initGui() {
        this.buttonList.clear()
        this.buttonList.add(GuiButton(0, this.width / 2 - 201, this.height / 2 - 24, 76, 20, "§bDROPPER"))
        this.buttonList.add(GuiButton(1, this.width / 2 - 102, this.height / 2 - 24, 76, 20, "§bZOMBIES"))
        this.buttonList.add(GuiButton(2, this.width / 2 + 26, this.height / 2 - 24, 76, 20, "§bPARTY GAMES"))
        this.buttonList.add(GuiButton(3, this.width / 2 + 125   , this.height / 2 - 24, 76, 20, "§bPIXEL PARTY"))

        this.buttonList.add(GuiButton(4, this.width / 2 - 201, this.height / 2 + 4, 76, 20, "§aBLOCKING DEAD"))
        this.buttonList.add(GuiButton(5, this.width / 2 - 102, this.height / 2 + 4, 76, 20, "§aCTW"))
        this.buttonList.add(GuiButton(6, this.width / 2 + 26, this.height / 2 + 4, 76, 20, "§aCREEPER"))
        this.buttonList.add(GuiButton(7, this.width / 2 + 125, this.height / 2 + 4, 76, 20, "§aDRAGON WARS"))

        this.buttonList.add(GuiButton(8, this.width / 2 - 201, this.height / 2 + 32, 76, 20, "§bENDER SPLEEF"))
        this.buttonList.add(GuiButton(9, this.width / 2 - 102, this.height / 2 + 32, 76, 20, "§bFARM HUNT"))
        this.buttonList.add(GuiButton(10, this.width / 2 + 26, this.height / 2 + 32, 76, 20, "§bFOOTBALL"))
        this.buttonList.add(GuiButton(11, this.width / 2 + 125, this.height / 2 + 32, 76, 20, "§bGALAXY WARS"))

        this.buttonList.add(GuiButton(12, this.width / 2 - 201, this.height / 2 + 60, 76, 20, "§aHIDE AND SEEK"))
        this.buttonList.add(GuiButton(13, this.width / 2 - 102, this.height / 2 + 60, 76, 20, "§aHITW"))
        this.buttonList.add(GuiButton(14, this.width / 2 + 26, this.height / 2 + 60, 76, 20, "§aTHROW OUT"))
        this.buttonList.add(GuiButton(15, this.width / 2 + 125, this.height / 2 + 60, 76, 20, "§aPAINTERS"))

        this.buttonList.add(GuiButton(16, this.width / 2 - 201, this.height / 2 - 52, 76, 20, "§aHYPIXEL SAYS"))
        this.buttonList.add(GuiButton(17, this.width / 2 + 125, this.height / 2 - 52, 76, 20, "§aMINI WALLS"))

        this.buttonList.add(GuiButton(18, this.width / 2 - 25, this.height / 2 + 100, 50, 20, "§cBACK"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, this.width, this.height, 0x80000000.toInt())
        super.drawScreen(mouseX, mouseY, partialTicks)
        mc.textureManager.bindTexture(Icon)
        drawTexturedModalRect(this.width/2-37, this.height/2-130, 76, 76, 76, 76)
        this.drawCenteredString(this.fontRendererObj, "ARCADE", this.width / 2, this.height / 2 - 50, 0xFFFFFF)

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
            18 -> {
                GuiUtils.displayScreen(QuickJoinGui())
            }

        }
    }
}