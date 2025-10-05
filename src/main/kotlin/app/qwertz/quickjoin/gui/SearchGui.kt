package app.qwertz.quickjoin.gui

import app.qwertz.quickjoin.QuickJoin.Companion.config
import app.qwertz.quickjoin.config.guis
import cc.polyfrost.oneconfig.utils.gui.GuiUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.GuiTextField
import net.minecraft.util.ChatComponentText
import org.lwjgl.input.Mouse

data class SearchItem(
    val guiName: String,
    val buttonId: Int,
    val parentTitle: String,
    val childName: String,
    val childColor: String
)

class SearchGui : GuiScreen() {
    private var items: List<SearchItem> = emptyList()
    private var filtered: List<SearchItem> = emptyList()
    private var scrollOffset = 0
    private var maxVisible = 7
    private var searchField: GuiTextField? = null

    override fun initGui() {
        buttonList.clear()
        if (items.isEmpty()) {
            items = collectAllActions()
        }
        val availableWidth = 260
        val startX = this.width / 2 - availableWidth / 2
        val topY = this.height / 2 - 90

        if (searchField == null) {
            searchField = GuiTextField(8898, fontRendererObj, startX, topY - 22, availableWidth, 18)
            searchField!!.maxStringLength = 64
            searchField!!.setFocused(true)
        }

        val query = searchField?.text ?: ""
        filtered = filter(items, query)
        val visible = filtered.drop(scrollOffset).take(maxVisible)

        var y = topY
        var id = 8800
        for (entry in visible) {
            val label = formatLabel(entry)
            buttonList.add(GuiButton(id, startX, y, availableWidth, 20, label))
            id++
            y += 22
        }
        buttonList.add(GuiButton(8700, startX, this.height / 2 + 90, 128, 20, "Back"))
        buttonList.add(GuiButton(8701, startX + 132, this.height / 2 + 90, 128, 20, "Close"))
        buttonList.add(GuiButton(8702, startX, this.height / 2 + 66, 62, 20, "▲"))
        buttonList.add(GuiButton(8703, startX + 66, this.height / 2 + 66, 62, 20, "▼"))
        buttonList.add(GuiButton(8704, startX + 132, this.height / 2 + 66, 128, 20, "Clear Search"))
    }

    private fun collectAllActions(): List<SearchItem> {
        val result = mutableListOf<SearchItem>()
        guis.guis.forEach { (guiName, gui) ->
            val parentTitle = if (guiName == "QuickJoinGui") "Quickjoin" else gui.name
            gui.funcs.forEach { (idStr, func) ->
                if (func.type != "opengui") {
                    val id = idStr.toIntOrNull() ?: return@forEach
                    val button = gui.buttons.firstOrNull { it.id == id }
                    val childName = button?.label ?: "$guiName#$id"
                    val childColor = button?.colorCode ?: "§f"
                    result.add(SearchItem(guiName, id, parentTitle, childName, childColor))
                }
            }
        }
        return result.sortedWith(compareBy({ it.parentTitle }, { it.childName }))
    }

    private fun strip(text: String): String = text.replace("§".toRegex(), "")

    private fun filter(list: List<SearchItem>, query: String): List<SearchItem> {
        if (query.isBlank()) return list
        val q = query.trim().lowercase()
        return list.filter { item ->
            val parent = item.parentTitle.lowercase()
            val child = item.childName.lowercase()
            val combined = (item.parentTitle + " >> " + item.childName).lowercase()
            containsOrFuzzy(parent, q) || containsOrFuzzy(child, q) || containsOrFuzzy(combined, q)
        }
    }

    private fun containsOrFuzzy(text: String, query: String): Boolean {
        if (text.contains(query)) return true
        // Early-out: if length difference is > 1 and not substring, impossible for distance <= 1
        if (kotlin.math.abs(text.length - query.length) > 1) return false
        return levenshteinLeq1(text, query)
    }

    // Returns true if Levenshtein distance(text, query) <= 1 with early exit
    private fun levenshteinLeq1(a: String, b: String): Boolean {
        val la = a.length
        val lb = b.length
        if (a == b) return true
        if (kotlin.math.abs(la - lb) > 1) return false
        var i = 0
        var j = 0
        var edits = 0
        while (i < la && j < lb) {
            if (a[i] == b[j]) {
                i++; j++
            } else {
                if (edits == 1) return false
                edits++
                if (la > lb) {
                    i++ // deletion in b (skip a)
                } else if (lb > la) {
                    j++ // insertion in b (skip b)
                } else {
                    i++; j++ // substitution
                }
            }
        }
        // Account for trailing char
        if (i < la || j < lb) edits++
        return edits <= 1
    }

    private fun formatLabel(item: SearchItem): String {
        return "§b§l${item.parentTitle} §r§7>> ${item.childColor}${item.childName}"
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawRect(0, 0, width, height, 0xB0000000.toInt())
        drawCenteredString(fontRendererObj, "§aSearch", width / 2, height / 2 - 110, 0xFFFFFF)
        searchField?.drawTextBox()
        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)
        searchField?.mouseClicked(mouseX, mouseY, mouseButton)
    }

    override fun handleMouseInput() {
        super.handleMouseInput()
        val dWheel = Mouse.getEventDWheel()
        if (dWheel != 0) {
            if (dWheel < 0) scrollOffset = Math.min(scrollOffset + 1, Math.max(0, filtered.size - maxVisible))
            if (dWheel > 0) scrollOffset = Math.max(0, scrollOffset - 1)
            initGui()
        }
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (searchField?.isFocused == true) {
            searchField?.textboxKeyTyped(typedChar, keyCode)
            scrollOffset = 0
            initGui()
            return
        }
        super.keyTyped(typedChar, keyCode)
    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            8700 -> GuiUtils.displayScreen(QuickJoinGui())
            8701 -> GuiUtils.closeScreen()
            8702 -> { scrollOffset = Math.max(0, scrollOffset - 1); initGui() }
            8703 -> { scrollOffset = Math.min(scrollOffset + 1, Math.max(0, filtered.size - maxVisible)); initGui() }
            8704 -> { searchField?.text = ""; scrollOffset = 0; initGui() }
            else -> handleClick(button.id)
        }
    }

    private fun handleClick(buttonId: Int) {
        val index = buttonId - 8800 + scrollOffset
        if (index !in filtered.indices) return
        val entry = filtered[index]
        val func = guis.guis[entry.guiName]?.funcs?.get(entry.buttonId.toString()) ?: return
        when (func.type) {
            "command" -> {
                Minecraft.getMinecraft().thePlayer.sendChatMessage(func.string)
                GuiUtils.closeScreen()
            }
            "chat" -> {
                Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(func.string))
                GuiUtils.closeScreen()
            }
            "close" -> GuiUtils.closeScreen()
            "repeat" -> {
                repeat(func.times) {
                    GuiUtils.closeScreen()
                    Minecraft.getMinecraft().thePlayer.sendChatMessage(func.string)
                }
            }
            else -> {
                // should not happen since we filtered out opengui
            }
        }
    }
}


