package app.qwertz.quickjoin.fabric.screen;

import app.qwertz.quickjoin.data.ButtonConfig;
import app.qwertz.quickjoin.data.Config;
import app.qwertz.quickjoin.data.Func;
import app.qwertz.quickjoin.data.GuiData;
import app.qwertz.quickjoin.fabric.QuickJoinFabric;
import app.qwertz.quickjoin.fabric.config.QuickJoinConfig;
import app.qwertz.quickjoin.fabric.util.LegacyText;
import app.qwertz.quickjoin.favorites.FavoritesManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuickJoinScreen extends Screen {

    private static final int ICON_SIZE = 76;
    private static final int ICON_SHEET_SIZE = 256;

    private final String guiName;
    private final Config guiLayout;

    private final List<ButtonWidget> entryButtons = new ArrayList<>();
    private final Map<ButtonWidget, Integer> buttonIds = new HashMap<>();

    public QuickJoinScreen(String guiName) {
        super(Text.literal("QuickJoin"));
        this.guiName = guiName;
        this.guiLayout = QuickJoinConfig.getGuiLayout();
    }

    @Override
    protected void init() {
        super.init();
        clearChildren();
        entryButtons.clear();
        buttonIds.clear();

        GuiData gui = guiLayout != null ? guiLayout.guis.get(guiName) : null;
        if (gui == null) {
            return;
        }

        String boldPrefix = QuickJoinConfig.boldButtons() ? "§l" : "";
        String colorPrefix = QuickJoinConfig.coloredButtons() ? "" : "§f";

        if (gui.buttons != null) {
            for (ButtonConfig button : gui.buttons) {
                int x = evaluateExpression(button.x, width, height);
                int y = evaluateExpression(button.y, width, height);
                boolean isFav = QuickJoinConfig.enableFavorites() && FavoritesManager.isFavorite(guiName, button.id);
                String star = isFav ? "✮ " : "";
                String label = star + button.colorCode + colorPrefix + boldPrefix + button.label;
                MutableText text = LegacyText.parse(label);
                ButtonWidget widget = ButtonWidget.builder(text, btn -> handleButtonPress(button.id))
                        .dimensions(x, y, button.width, button.height)
                        .build();
                addDrawableChild(widget);
                entryButtons.add(widget);
                buttonIds.put(widget, button.id);
            }
        }

        if ("QuickJoinGui".equals(guiName)) {
            int topY = 8;
            int spacing = 90;
            int nextX = this.width - spacing;

            if (QuickJoinConfig.enableFavorites()) {
                addDrawableChild(ButtonWidget.builder(Text.literal("§6§lFavorites"), btn -> this.client.setScreen(new FavoritesScreen()))
                        .dimensions(nextX, topY, 80, 20)
                        .build());
                nextX -= spacing;
            }

            addDrawableChild(ButtonWidget.builder(Text.literal("§a§lSearch"), btn -> this.client.setScreen(new SearchScreen()))
                    .dimensions(nextX, topY, 80, 20)
                    .build());
            nextX -= spacing;

            addDrawableChild(ButtonWidget.builder(Text.literal("§b§lConfig"), btn -> {
                        if (this.client != null) {
                            this.client.setScreen(QuickJoinConfig.createConfigScreen(this));
                        }
                    })
                    .dimensions(nextX, topY, 80, 20)
                    .build());
        }
    }

    private void handleButtonPress(int id) {
        if (id == 7000) {
            client.setScreen(new FavoritesScreen());
            return;
        }
        if (id == 7001) {
            client.setScreen(new SearchScreen());
            return;
        }

        GuiData gui = guiLayout != null ? guiLayout.guis.get(guiName) : null;
        if (gui == null || gui.funcs == null) {
            return;
        }
        Func func = gui.funcs.get(String.valueOf(id));
        QuickJoinActions.execute(client, guiName, func);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 1 && QuickJoinConfig.enableFavorites()) {
            for (ButtonWidget widget : entryButtons) {
                if (widget.isMouseOver(mouseX, mouseY)) {
                    Integer id = buttonIds.get(widget);
                    if (id == null || id >= 7000) {
                        return super.mouseClicked(mouseX, mouseY, button);
                    }
                    GuiData gui = guiLayout != null ? guiLayout.guis.get(guiName) : null;
                    if (gui == null || gui.funcs == null) return super.mouseClicked(mouseX, mouseY, button);
                    Func func = gui.funcs.get(String.valueOf(id));
                    if (func == null || "opengui".equals(func.type)) {
                        return super.mouseClicked(mouseX, mouseY, button);
                    }
                    boolean added = FavoritesManager.toggleFavorite(guiName, id);
                    if (QuickJoinConfig.debugMode() && client.player != null) {
                        client.player.sendMessage(Text.literal("Favorites " + (added ? "added" : "removed") + ": " + guiName + "#" + id), false);
                    }
                    init();
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, 0x80000000);
        super.render(context, mouseX, mouseY, delta);

        GuiData gui = guiLayout != null ? guiLayout.guis.get(guiName) : null;
        if (gui == null) {
            return;
        }
        int iconY = this.height / 2 - 125;
        
        Identifier icon = Identifier.of(QuickJoinFabric.MOD_ID, "Icon" + gui.icon.sheet + ".png");
        int iconX = this.width / 2 - ICON_SIZE / 2;
        context.drawTexture(RenderPipelines.GUI_TEXTURED, icon, iconX, iconY,
                (float) gui.icon.textureX, (float) gui.icon.textureY,
                ICON_SIZE, ICON_SIZE,
                ICON_SHEET_SIZE, ICON_SHEET_SIZE);

        int titleY = iconY + ICON_SIZE + 4;
        context.drawCenteredTextWithShadow(textRenderer, Text.literal(gui.name), this.width / 2, titleY, 0xFFFFFFFF);
            }

    private int evaluateExpression(String expression, int width, int height) {
        if (expression == null || expression.isEmpty()) {
            return 0;
        }
        String replaced = expression
                .replace("centerx", String.valueOf(width / 2))
                .replace("centery", String.valueOf(height / 2));
        String[] parts = replaced.split(" ");
        try {
            if (parts.length == 1) {
                return Integer.parseInt(parts[0]);
            }
            if (parts.length >= 3) {
                int left = Integer.parseInt(parts[0]);
                int right = Integer.parseInt(parts[2]);
                return switch (parts[1]) {
                    case "+" -> left + right;
                    case "-" -> left - right;
                    default -> 0;
                };
            }
        } catch (NumberFormatException ignored) {
        }
        return 0;
    }
}
