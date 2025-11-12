package app.qwertz.quickjoin.gui;

import app.qwertz.quickjoin.QuickJoin;
import app.qwertz.quickjoin.config.QuickJoinConfig;
import app.qwertz.quickjoin.data.Config;
import app.qwertz.quickjoin.data.Func;
import app.qwertz.quickjoin.data.ButtonConfig;
import app.qwertz.quickjoin.favorites.FavoriteEntry;
import app.qwertz.quickjoin.favorites.FavoritesManager;
import cc.polyfrost.oneconfig.utils.gui.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavoritesGui extends GuiScreen {

    private Config guiConfig = QuickJoinConfig.guis;
    private int scrollOffset = 0;
    private int maxVisible = 7;
    private GuiTextField searchField;

    @Override
    public void initGui() {
        buttonList.clear();

        int availableWidth = 220;
        int startX = this.width / 2 - availableWidth / 2;
        int topY = this.height / 2 - 90;

        if (searchField == null) {
            searchField = new GuiTextField(9998, fontRendererObj, startX, topY - 22, availableWidth, 18);
            searchField.setMaxStringLength(64);
            searchField.setFocused(false);
        }

        List<FavoriteEntry> favorites = filterFavorites(FavoritesManager.getFavorites(), searchField.getText());
        List<FavoriteEntry> visibleFavorites = favorites.subList(Math.min(scrollOffset, favorites.size()),
                Math.min(scrollOffset + maxVisible, favorites.size()));

        int yOffset = topY;
        int id = 9000;
        for (FavoriteEntry entry : visibleFavorites) {
            String label = buildRichLabel(entry.guiName, entry.buttonId);
            buttonList.add(new GuiButton(id++, startX, yOffset, availableWidth, 20, label));
            yOffset += 22;
        }

        // Add standard buttons
        buttonList.add(new GuiButton(8000, startX, this.height / 2 + 90, 108, 20, "Back"));
        buttonList.add(new GuiButton(8001, startX + 112, this.height / 2 + 90, 108, 20, "Close"));
        buttonList.add(new GuiButton(8002, startX, this.height / 2 + 66, 52, 20, "▲"));
        buttonList.add(new GuiButton(8003, startX + 56, this.height / 2 + 66, 52, 20, "▼"));
        buttonList.add(new GuiButton(8004, startX + 112, this.height / 2 + 66, 108, 20, "Clear Search"));
    }

    private List<FavoriteEntry> filterFavorites(List<FavoriteEntry> list, String query) {
        if (query == null || query.isEmpty()) return new ArrayList<>(list);
        List<FavoriteEntry> filtered = new ArrayList<>();
        for (FavoriteEntry entry : list) {
            if (buildPlainLabel(entry.guiName, entry.buttonId).toLowerCase().contains(query.toLowerCase())) {
                filtered.add(entry);
            }
        }
        return filtered;
    }

    private String buildPlainLabel(String guiName, int buttonId) {
        String parentTitle = "QuickJoinGui".equals(guiName) ? "Quickjoin" :
                guiConfig.guis.get(guiName) != null ? guiConfig.guis.get(guiName).name : "Quickjoin";
        ButtonConfig child = guiConfig.guis.get(guiName) != null && guiConfig.guis.get(guiName).buttons != null ?
                guiConfig.guis.get(guiName).buttons.stream().filter(b -> b.id == buttonId).findFirst().orElse(null) : null;
        String childName = child != null ? child.label : guiName + "#" + buttonId;
        return parentTitle + " >> " + childName;
    }

    private String buildRichLabel(String guiName, int buttonId) {
        String parentTitle = "QuickJoinGui".equals(guiName) ? "Quickjoin" :
                guiConfig.guis.get(guiName) != null ? guiConfig.guis.get(guiName).name : "Quickjoin";
        String parentColor = "§b§l";
        ButtonConfig child = guiConfig.guis.get(guiName) != null && guiConfig.guis.get(guiName).buttons != null ?
                guiConfig.guis.get(guiName).buttons.stream().filter(b -> b.id == buttonId).findFirst().orElse(null) : null;
        String childColor = child != null ? child.colorCode : "§f";
        String childName = child != null ? child.label : guiName + "#" + buttonId;
        return "✮ " + parentColor + parentTitle + " §r§7>> " + childColor + childName;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawRect(0, 0, width, height, 0xB0000000);
        drawCenteredString(fontRendererObj, "§eFavorites", width / 2, height / 2 - 110, 0xFFFFFF);
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

        if (mouseButton == 1) { // right-click removes from favorites
            List<FavoriteEntry> favorites = filterFavorites(FavoritesManager.getFavorites(),
                    searchField != null ? searchField.getText() : "");
            for (GuiButton btn : buttonList) {
                int index = btn.id - 9000 + scrollOffset;
                if (index >= 0 && index < favorites.size() && btn.mousePressed(mc, mouseX, mouseY)) {
                    FavoritesManager.toggleFavorite(favorites.get(index).guiName, favorites.get(index).buttonId);
                    initGui();
                    return;
                }
            }
        }
    }

    @Override
    public void handleMouseInput() {
        try {
            super.handleMouseInput();
        } catch (Exception ignored) {
        }
        int dWheel = Mouse.getEventDWheel();
        if (dWheel != 0) {
            List<FavoriteEntry> favorites = filterFavorites(FavoritesManager.getFavorites(),
                    searchField != null ? searchField.getText() : "");
            if (dWheel < 0) scrollOffset = Math.min(scrollOffset + 1, Math.max(0, favorites.size() - maxVisible));
            if (dWheel > 0) scrollOffset = Math.max(0, scrollOffset - 1);
            initGui();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (searchField != null && searchField.isFocused()) {
            searchField.textboxKeyTyped(typedChar, keyCode);
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
            case 8000:
                GuiUtils.displayScreen(new QuickJoinGui());
                break;
            case 8001:
                GuiUtils.closeScreen();
                break;
            case 8002:
                scrollOffset = Math.max(0, scrollOffset - 1);
                initGui();
                break;
            case 8003:
                List<FavoriteEntry> favorites = filterFavorites(FavoritesManager.getFavorites(),
                        searchField != null ? searchField.getText() : "");
                scrollOffset = Math.min(scrollOffset + 1, Math.max(0, favorites.size() - maxVisible));
                initGui();
                break;
            case 8004:
                if (searchField != null) searchField.setText("");
                initGui();
                break;
            default:
                handleFavoriteClick(button.id);
                break;
        }
    }

    private void handleFavoriteClick(int buttonId) {
        List<FavoriteEntry> favorites = filterFavorites(FavoritesManager.getFavorites(),
                searchField != null ? searchField.getText() : "");
        int index = buttonId - 9000 + scrollOffset;
        if (index < 0 || index >= favorites.size()) return;

        FavoriteEntry entry = favorites.get(index);
        QuickJoinGui gui = new QuickJoinGui(entry.guiName);
        Map<String, Func> funcs = guiConfig.guis.get(entry.guiName) != null ? guiConfig.guis.get(entry.guiName).funcs : null;
        Func buttonConfig = funcs != null ? funcs.get(String.valueOf(entry.buttonId)) : null;

        if (buttonConfig == null) {
            if (QuickJoinConfig.DebugMode) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: Favorite not found in config")
                );
            }
            return;
        }

        GuiUtils.displayScreen(gui);
        switch (buttonConfig.type) {
            case "opengui":
                GuiUtils.displayScreen(new QuickJoinGui(buttonConfig.string));
                break;
            case "command":
                Minecraft.getMinecraft().thePlayer.sendChatMessage(buttonConfig.string);
                GuiUtils.closeScreen();
                break;
            case "chat":
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(buttonConfig.string));
                GuiUtils.closeScreen();
                break;
            case "close":
                GuiUtils.closeScreen();
                break;
            case "repeat":
                for (int i = 0; i < buttonConfig.times; i++) {
                    GuiUtils.closeScreen();
                    Minecraft.getMinecraft().thePlayer.sendChatMessage(buttonConfig.string);
                }
                break;
        }
    }
}
