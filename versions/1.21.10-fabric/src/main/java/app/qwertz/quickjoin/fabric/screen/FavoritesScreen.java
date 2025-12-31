package app.qwertz.quickjoin.fabric.screen;

import app.qwertz.quickjoin.data.ButtonConfig;
import app.qwertz.quickjoin.data.Config;
import app.qwertz.quickjoin.data.Func;
import app.qwertz.quickjoin.data.GuiData;
import app.qwertz.quickjoin.fabric.config.QuickJoinConfig;
import app.qwertz.quickjoin.fabric.util.LegacyText;
import app.qwertz.quickjoin.favorites.FavoriteEntry;
import app.qwertz.quickjoin.favorites.FavoritesManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.ClickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoritesScreen extends Screen {

    private static final int MAX_VISIBLE = 7;

    private EditBox searchField;
    private int scrollOffset = 0;

    private final List<Button> entryButtons = new ArrayList<>();
    private final Map<Button, FavoriteEntry> entryMap = new HashMap<>();

    private final Minecraft clientInstance = Minecraft.getInstance();

    public FavoritesScreen() {
        super(Component.literal("Favorites"));
    }

    @Override
    protected void init() {
        super.init();
        clearWidgets();
        entryButtons.clear();
        entryMap.clear();

        int availableWidth = 220;
        int startX = this.width / 2 - availableWidth / 2;
        int topY = this.height / 2 - 90;

        if (searchField == null) {
            searchField = new EditBox(font, startX, topY - 22, availableWidth, 18, Component.empty());
            searchField.setMaxLength(64);
            searchField.setResponder(s -> {
                scrollOffset = 0;
                rebuildEntries();
            });
        }
        addRenderableWidget(searchField);

        rebuildEntries();

        addRenderableWidget(Button.builder(Component.literal("Back"), btn -> Minecraft.getInstance().setScreen(new QuickJoinScreen("QuickJoinGui")))
                .bounds(startX, this.height / 2 + 90, 108, 20)
                .build());
        addRenderableWidget(Button.builder(Component.literal("Close"), btn -> Minecraft.getInstance().setScreen(null))
                .bounds(startX + 112, this.height / 2 + 90, 108, 20)
                .build());
        addRenderableWidget(Button.builder(Component.literal("▲"), btn -> {
                    if (scrollOffset > 0) {
                        scrollOffset--;
                        rebuildEntries();
                    }
                })
                .bounds(startX, this.height / 2 + 66, 52, 20)
                .build());
        addRenderableWidget(Button.builder(Component.literal("▼"), btn -> {
                    int max = Math.max(0, filteredFavorites().size() - MAX_VISIBLE);
                    if (scrollOffset < max) {
                        scrollOffset++;
                        rebuildEntries();
                    }
                })
                .bounds(startX + 56, this.height / 2 + 66, 52, 20)
                .build());
        addRenderableWidget(Button.builder(Component.literal("Clear Search"), btn -> {
                    searchField.setValue("");
                    scrollOffset = 0;
                    rebuildEntries();
                })
                .bounds(startX + 112, this.height / 2 + 66, 108, 20)
                .build());
    }

    private void rebuildEntries() {
        for (Button widget : entryButtons) {
            removeWidget(widget);
        }
        entryButtons.clear();
        entryMap.clear();

        int availableWidth = 220;
        int startX = this.width / 2 - availableWidth / 2;
        int topY = this.height / 2 - 90;

        List<FavoriteEntry> favorites = filteredFavorites();
        int visible = Math.min(MAX_VISIBLE, Math.max(0, favorites.size() - scrollOffset));
        int y = topY;
        for (int i = 0; i < visible; i++) {
            FavoriteEntry entry = favorites.get(scrollOffset + i);
            MutableComponent label = LegacyText.parse(buildLabel(entry));
            Button widget = Button.builder(label, btn -> onFavoriteClicked(entry))
                    .bounds(startX, y, availableWidth, 20)
                    .build();
            entryButtons.add(widget);
            entryMap.put(widget, entry);
            addRenderableWidget(widget);
            y += 22;
        }
    }

    private List<FavoriteEntry> filteredFavorites() {
        String query = searchField != null ? searchField.getValue() : "";
        if (query == null || query.isBlank()) {
            return new ArrayList<>(FavoritesManager.getFavorites());
        }
        String lower = query.toLowerCase();
        List<FavoriteEntry> filtered = new ArrayList<>();
        for (FavoriteEntry entry : FavoritesManager.getFavorites()) {
            if (buildPlainLabel(entry).toLowerCase().contains(lower)) {
                filtered.add(entry);
            }
        }
        return filtered;
    }

    private String buildPlainLabel(FavoriteEntry entry) {
        Config config = QuickJoinConfig.getGuiLayout();
        if (config == null || config.guis == null) {
            return entry.guiName + " >> " + entry.buttonId;
        }
        GuiData gui = config.guis.get(entry.guiName);
        String parent = gui != null ? gui.name : "Quickjoin";
        ButtonConfig button = gui != null ? gui.buttons.stream().filter(b -> b.id == entry.buttonId).findFirst().orElse(null) : null;
        String child = button != null ? button.label : entry.guiName + "#" + entry.buttonId;
        return parent + " >> " + child;
    }

    private String buildLabel(FavoriteEntry entry) {
        Config config = QuickJoinConfig.getGuiLayout();
        if (config == null || config.guis == null) {
            return "✮ §b§lQuickjoin §r§7>> §f" + entry.buttonId;
        }
        GuiData gui = config.guis.get(entry.guiName);
        String parent = gui != null ? gui.name : "Quickjoin";
        ButtonConfig button = gui != null ? gui.buttons.stream().filter(b -> b.id == entry.buttonId).findFirst().orElse(null) : null;
        String childColor = button != null ? button.colorCode : "§f";
        String child = button != null ? button.label : entry.guiName + "#" + entry.buttonId;
        return "✮ §b§l" + parent + " §r§7>> " + childColor + child;
    }

    private void onFavoriteClicked(FavoriteEntry entry) {
        Config config = QuickJoinConfig.getGuiLayout();
        if (config == null || config.guis == null) {
            return;
        }
        GuiData gui = config.guis.get(entry.guiName);
        if (gui == null || gui.funcs == null) {
            return;
        }
        Func func = gui.funcs.get(String.valueOf(entry.buttonId));
        if (func == null) {
            return;
        }
        QuickJoinActions.execute(clientInstance, entry.guiName, func);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent click, boolean doubled) {
        int button = click.button();
        double mouseX = click.x();
        double mouseY = click.y();
        if (button == 1) {
            for (Button widget : entryButtons) {
                if (widget.isMouseOver(mouseX, mouseY)) {
                    FavoriteEntry entry = entryMap.get(widget);
                    if (entry != null) {
                        FavoritesManager.toggleFavorite(entry.guiName, entry.buttonId);
                        rebuildEntries();
                        return true;
                    }
                }
            }
        }
        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (verticalAmount > 0) {
            if (scrollOffset > 0) {
                scrollOffset--;
                rebuildEntries();
                return true;
            }
        } else if (verticalAmount < 0) {
            int max = Math.max(0, filteredFavorites().size() - MAX_VISIBLE);
            if (scrollOffset < max) {
                scrollOffset++;
                rebuildEntries();
                return true;
            }
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, 0xB0000000);
        context.drawCenteredString(font, Component.literal("§eFavorites"), width / 2, height / 2 - 110, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }
}
