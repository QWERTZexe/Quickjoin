package app.qwertz.quickjoin.gui

import app.qwertz.quickjoin.QuickJoin.Companion.config
import app.qwertz.quickjoin.config.guis
import app.qwertz.quickjoin.favorites.FavoritesManager
import cc.polyfrost.oneconfig.utils.gui.GuiUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.GuiTextField
import net.minecraft.util.ChatComponentText
import org.lwjgl.input.Mouse

class FavoritesGui : GuiScreen() {
    private val guiConfig = guis
    private var scrollOffset = 0
    private var maxVisible = 7
    private var searchField: GuiTextField? = null

    override fun initGui() {
        buttonList.clear()
        val availableWidth = 220
        val startX = this.width / 2 - availableWidth / 2
        val topY = this.height / 2 - 90

        if (searchField == null) {
            searchField = GuiTextField(9998, fontRendererObj, startX, topY - 22, availableWidth, 18)
            searchField!!.maxStringLength = 64
            searchField!!.setFocused(false)
        }

        val favorites = filterFavorites(FavoritesManager.getFavorites(), searchField?.text ?: "")
        val visibleFavorites = favorites.drop(scrollOffset).take(maxVisible)
        var yOffset = topY
        var id = 9000
        for (entry in visibleFavorites) {
            val label = buildRichLabel(entry.guiName, entry.buttonId)
            buttonList.add(GuiButton(id, startX, yOffset, availableWidth, 20, label))
            id++
            yOffset += 22
        }
        buttonList.add(GuiButton(8000, startX, this.height / 2 + 90, 108, 20, "Back"))
        buttonList.add(GuiButton(8001, startX + 112, this.height / 2 + 90, 108, 20, "Close"))
        buttonList.add(GuiButton(8002, startX, this.height / 2 + 66, 52, 20, "▲"))
        buttonList.add(GuiButton(8003, startX + 56, this.height / 2 + 66, 52, 20, "▼"))
        buttonList.add(GuiButton(8004, startX + 112, this.height / 2 + 66, 108, 20, "Clear Search"))
    }

    private fun filterFavorites(list: List<app.qwertz.quickjoin.favorites.FavoriteEntry>, query: String): List<app.qwertz.quickjoin.favorites.FavoriteEntry> {
        if (query.isBlank()) return list
        return list.filter { buildPlainLabel(it.guiName, it.buttonId).contains(query, ignoreCase = true) }
    }

    private fun buildPlainLabel(guiName: String, buttonId: Int): String {
        val parentTitle = if (guiName == "QuickJoinGui") "Quickjoin" else guiConfig.guis[guiName]?.name ?: "Quickjoin"
        val child = guiConfig.guis[guiName]?.buttons?.firstOrNull { it.id == buttonId }
        val childName = child?.label ?: "$guiName#$buttonId"
        return "$parentTitle >> $childName"
    }

    private fun buildRichLabel(guiName: String, buttonId: Int): String {
        val parentTitle = if (guiName == "QuickJoinGui") "Quickjoin" else guiConfig.guis[guiName]?.name ?: "Quickjoin"
        val parentColor = "§b§l"
        val child = guiConfig.guis[guiName]?.buttons?.firstOrNull { it.id == buttonId }
        val childColor = child?.colorCode ?: "§f"
        val childName = child?.label ?: "$guiName#$buttonId"
        return "✮ ${parentColor}$parentTitle §r§7>> ${childColor}$childName"
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, width, height, 0xB0000000.toInt())
        drawCenteredString(fontRendererObj, "§eFavorites", width / 2, height / 2 - 110, 0xFFFFFF)
        searchField?.drawTextBox()
        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)
        searchField?.mouseClicked(mouseX, mouseY, mouseButton)
        if (mouseButton == 1) { // right click removes from favorites
            for (btn in buttonList) {
                if (btn.id >= 9000 && btn.mousePressed(mc, mouseX, mouseY)) {
                    val index = btn.id - 9000 + scrollOffset
                    val favorites = filterFavorites(FavoritesManager.getFavorites(), searchField?.text ?: "")
                    if (index in favorites.indices) {
                        val entry = favorites[index]
                        FavoritesManager.toggleFavorite(entry.guiName, entry.buttonId)
                        initGui()
                    }
                    return
                }
            }
        }
    }

    override fun handleMouseInput() {
        super.handleMouseInput()
        val dWheel = Mouse.getEventDWheel()
        if (dWheel != 0) {
            val favorites = filterFavorites(FavoritesManager.getFavorites(), searchField?.text ?: "")
            if (dWheel < 0) scrollOffset = Math.min(scrollOffset + 1, Math.max(0, favorites.size - maxVisible))
            if (dWheel > 0) scrollOffset = Math.max(0, scrollOffset - 1)
            initGui()
        }
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (searchField?.isFocused == true) {
            searchField?.textboxKeyTyped(typedChar, keyCode)
            initGui()
            return
        }
        super.keyTyped(typedChar, keyCode)
    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            8000 -> GuiUtils.displayScreen(QuickJoinGui())
            8001 -> GuiUtils.closeScreen()
            8002 -> { scrollOffset = Math.max(0, scrollOffset - 1); initGui() }
            8003 -> { val favorites = filterFavorites(FavoritesManager.getFavorites(), searchField?.text ?: ""); scrollOffset = Math.min(scrollOffset + 1, Math.max(0, favorites.size - maxVisible)); initGui() }
            8004 -> { searchField?.text = ""; initGui() }
            else -> handleFavoriteClick(button.id)
        }
    }

    private fun handleFavoriteClick(buttonId: Int) {
        val favorites = filterFavorites(FavoritesManager.getFavorites(), searchField?.text ?: "")
        val index = buttonId - 9000 + scrollOffset
        if (index < 0 || index >= favorites.size) return
        val entry = favorites[index]
        val gui = QuickJoinGui(entry.guiName)
        val buttonConfig = guis.guis[entry.guiName]?.funcs?.get(entry.buttonId.toString())
        if (buttonConfig == null) {
            if (config.DebugMode) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: Favorite not found in config"))
            }
            return
        }
        GuiUtils.displayScreen(gui)
        when (buttonConfig.type) {
            "opengui" -> GuiUtils.displayScreen(QuickJoinGui(buttonConfig.string))
            "command" -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage(buttonConfig.string)
                GuiUtils.closeScreen()
            }
            "chat" -> {
                Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(buttonConfig.string))
                GuiUtils.closeScreen()
            }
            "close" -> GuiUtils.closeScreen()
            "repeat" -> {
                repeat(buttonConfig.times) {
                    GuiUtils.closeScreen()
                    Minecraft.getMinecraft().thePlayer.sendChatMessage(buttonConfig.string)
                }
            }
        }
    }
}


