package app.qwertz.quickjoin.gui;

import app.qwertz.quickjoin.config.QuickJoinConfig;
import app.qwertz.quickjoin.data.Config;
import cc.polyfrost.oneconfig.utils.gui.GuiUtils;
import net.minecraft.client.Minecraft;
import app.qwertz.quickjoin.data.Func;
import app.qwertz.quickjoin.data.ButtonConfig;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SearchGui extends GuiScreen {

    public static class SearchItem {
        public String guiName;
        public int buttonId;
        public String parentTitle;
        public String childName;
        public String childColor;

        public SearchItem(String guiName, int buttonId, String parentTitle, String childName, String childColor) {
            this.guiName = guiName;
            this.buttonId = buttonId;
            this.parentTitle = parentTitle;
            this.childName = childName;
            this.childColor = childColor;
        }
    }

    private List<SearchItem> items = new ArrayList<>();
    private List<SearchItem> filtered = new ArrayList<>();
    private int scrollOffset = 0;
    private int maxVisible = 7;
    private GuiTextField searchField;

    @Override
    public void initGui() {
        buttonList.clear();

        if (items.isEmpty()) {
            items = collectAllActions();
        }

        int availableWidth = 260;
        int startX = width / 2 - availableWidth / 2;
        int topY = height / 2 - 90;

        if (searchField == null) {
            searchField = new GuiTextField(8898, fontRendererObj, startX, topY - 22, availableWidth, 18);
            searchField.setMaxStringLength(64);
            searchField.setFocused(true);
        }

        String query = searchField.getText();
        filtered = filter(items, query);

        int visibleSize = Math.min(filtered.size() - scrollOffset, maxVisible);
        int y = topY;
        int id = 8800;
        for (int i = 0; i < visibleSize; i++) {
            SearchItem entry = filtered.get(scrollOffset + i);
            String label = formatLabel(entry);
            buttonList.add(new GuiButton(id++, startX, y, availableWidth, 20, label));
            y += 22;
        }

        buttonList.add(new GuiButton(8700, startX, height / 2 + 90, 128, 20, "Back"));
        buttonList.add(new GuiButton(8701, startX + 132, height / 2 + 90, 128, 20, "Close"));
        buttonList.add(new GuiButton(8702, startX, height / 2 + 66, 62, 20, "▲"));
        buttonList.add(new GuiButton(8703, startX + 66, height / 2 + 66, 62, 20, "▼"));
        buttonList.add(new GuiButton(8704, startX + 132, height / 2 + 66, 128, 20, "Clear Search"));
    }

    private List<SearchItem> collectAllActions() {
        List<SearchItem> result = new ArrayList<>();
        QuickJoinConfig.guis.guis.forEach((guiName, gui) -> {
            String parentTitle = guiName.equals("QuickJoinGui") ? "Quickjoin" : gui.name;
            if (gui.funcs != null) {
                for (Map.Entry<String, Func> entry : gui.funcs.entrySet()) {
                    Func func = entry.getValue();
                    if (!"opengui".equals(func.type)) {
                        int id;
                        try {
                            id = Integer.parseInt(entry.getKey());
                        } catch (NumberFormatException e) {
                            continue;
                        }
                        ButtonConfig button = null;
                        if (gui.buttons != null) {
                            for (ButtonConfig b : gui.buttons) {
                                if (b.id == id) {
                                    button = b;
                                    break;
                                }
                            }
                        }
                        String childName = button != null ? button.label : guiName + "#" + id;
                        String childColor = button != null ? button.colorCode : "§f";
                        result.add(new SearchItem(guiName, id, parentTitle, childName, childColor));
                    }
                }
            }
        });
        result.sort(Comparator.comparing((SearchItem si) -> si.parentTitle).thenComparing(si -> si.childName));
        return result;
    }

    private List<SearchItem> filter(List<SearchItem> list, String query) {
        if (query == null || query.trim().isEmpty()) return new ArrayList<>(list);
        String q = query.trim().toLowerCase();
        List<SearchItem> filtered = new ArrayList<>();
        for (SearchItem item : list) {
            String parent = item.parentTitle.toLowerCase();
            String child = item.childName.toLowerCase();
            String combined = (item.parentTitle + " >> " + item.childName).toLowerCase();
            if (containsOrFuzzy(parent, q) || containsOrFuzzy(child, q) || containsOrFuzzy(combined, q)) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    private boolean containsOrFuzzy(String text, String query) {
        if (text.contains(query)) return true;
        if (Math.abs(text.length() - query.length()) > 1) return false;
        return levenshteinLeq1(text, query);
    }

    private boolean levenshteinLeq1(String a, String b) {
        int la = a.length(), lb = b.length();
        if (a.equals(b)) return true;
        if (Math.abs(la - lb) > 1) return false;
        int i = 0, j = 0, edits = 0;
        while (i < la && j < lb) {
            if (a.charAt(i) == b.charAt(j)) {
                i++; j++;
            } else {
                if (edits == 1) return false;
                edits++;
                if (la > lb) i++;
                else if (lb > la) j++;
                else { i++; j++; }
            }
        }
        if (i < la || j < lb) edits++;
        return edits <= 1;
    }

    private String formatLabel(SearchItem item) {
        return "§b§l" + item.parentTitle + " §r§7>> " + item.childColor + item.childName;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawRect(0, 0, width, height, 0xB0000000);
        drawCenteredString(fontRendererObj, "§aSearch", width / 2, height / 2 - 110, 0xFFFFFF);
        if (searchField != null) searchField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        try {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        } catch (Exception ignored) {
        }
        if (searchField != null) searchField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void handleMouseInput() {
        try {
            super.handleMouseInput();
        } catch (Exception ignored) {
        }
        int dWheel = Mouse.getEventDWheel();
        if (dWheel != 0) {
            if (dWheel < 0) scrollOffset = Math.min(scrollOffset + 1, Math.max(0, filtered.size() - maxVisible));
            else scrollOffset = Math.max(0, scrollOffset - 1);
            initGui();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (searchField != null && searchField.isFocused()) {
            searchField.textboxKeyTyped(typedChar, keyCode);
            scrollOffset = 0;
            initGui();
        } else {
            try {
                super.keyTyped(typedChar, keyCode);
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 8700:
                GuiUtils.displayScreen(new QuickJoinGui());
                break;
            case 8701:
                GuiUtils.closeScreen();
                break;
            case 8702:
                scrollOffset = Math.max(0, scrollOffset - 1);
                initGui();
                break;
            case 8703:
                scrollOffset = Math.min(scrollOffset + 1, Math.max(0, filtered.size() - maxVisible));
                initGui();
                break;
            case 8704:
                if (searchField != null) searchField.setText("");
                scrollOffset = 0;
                initGui();
                break;
            default:
                handleClick(button.id);
                break;
        }
    }

    private void handleClick(int buttonId) {
        int index = buttonId - 8800 + scrollOffset;
        if (index < 0 || index >= filtered.size()) return;

        SearchItem entry = filtered.get(index);
        Map<String, Func> funcs = QuickJoinConfig.guis.guis.get(entry.guiName).funcs;
        if (funcs == null) return;
        Func func = funcs.get(String.valueOf(entry.buttonId));
        if (func == null) return;

        switch (func.type) {
            case "command":
                Minecraft.getMinecraft().thePlayer.sendChatMessage(func.string);
                GuiUtils.closeScreen();
                break;
            case "chat":
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(func.string));
                GuiUtils.closeScreen();
                break;
            case "close":
                GuiUtils.closeScreen();
                break;
            case "repeat":
                for (int i = 0; i < func.times; i++) {
                    GuiUtils.closeScreen();
                    Minecraft.getMinecraft().thePlayer.sendChatMessage(func.string);
                }
                break;
        }
    }
}
