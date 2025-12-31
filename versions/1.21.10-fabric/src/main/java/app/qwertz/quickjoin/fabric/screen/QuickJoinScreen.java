package app.qwertz.quickjoin.fabric.screen;

import app.qwertz.quickjoin.data.ButtonConfig;
import app.qwertz.quickjoin.data.Config;
import app.qwertz.quickjoin.data.Func;
import app.qwertz.quickjoin.data.GuiData;
import app.qwertz.quickjoin.fabric.QuickJoinFabric;
import app.qwertz.quickjoin.fabric.config.QuickJoinConfig;
import app.qwertz.quickjoin.fabric.util.LegacyText;
import app.qwertz.quickjoin.favorites.FavoritesManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuickJoinScreen extends Screen {

    private static final int ICON_SIZE = 76;
    private static final int ICON_SHEET_SIZE = 256;

    private final String guiName;
    private final Config guiLayout;

    private final List<Button> entryButtons = new ArrayList<>();
    private final Map<Button, Integer> buttonIds = new HashMap<>();

    public QuickJoinScreen(String guiName) {
        super(Component.literal("QuickJoin"));
        this.guiName = guiName;
        this.guiLayout = QuickJoinConfig.getGuiLayout();
    }

    @Override
    protected void init() {
        super.init();
        clearWidgets();
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
                MutableComponent text = LegacyText.parse(label);
                Button widget = Button.builder(text, btn -> handleButtonPress(button.id))
                        .bounds(x, y, button.width, button.height)
                        .build();
                addRenderableWidget(widget);
                entryButtons.add(widget);
                buttonIds.put(widget, button.id);
            }
        }

        if ("QuickJoinGui".equals(guiName)) {
            int topY = 8;
            int spacing = 90;
            int nextX = this.width - spacing;

            if (QuickJoinConfig.enableFavorites()) {
                addRenderableWidget(Button.builder(Component.literal("§6§lFavorites"), btn -> Minecraft.getInstance().setScreen(new FavoritesScreen()))
                        .bounds(nextX, topY, 80, 20)
                        .build());
                nextX -= spacing;
            }

            addRenderableWidget(Button.builder(Component.literal("§a§lSearch"), btn -> Minecraft.getInstance().setScreen(new SearchScreen()))
                    .bounds(nextX, topY, 80, 20)
                    .build());
            nextX -= spacing;

            addRenderableWidget(Button.builder(Component.literal("§b§lConfig"), btn -> {
                        if (Minecraft.getInstance() != null) {
                            Minecraft.getInstance().setScreen(QuickJoinConfig.createConfigScreen(this));
                        }
                    })
                    .bounds(nextX, topY, 80, 20)
                    .build());
        }
    }

    private void handleButtonPress(int id) {
        if (id == 7000) {
            Minecraft.getInstance().setScreen(new FavoritesScreen());
            return;
        }
        if (id == 7001) {
            Minecraft.getInstance().setScreen(new SearchScreen());
            return;
        }

        GuiData gui = guiLayout != null ? guiLayout.guis.get(guiName) : null;
        if (gui == null || gui.funcs == null) {
            return;
        }
        Func func = gui.funcs.get(String.valueOf(id));
        QuickJoinActions.execute(Minecraft.getInstance(), guiName, func);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent click, boolean doubled) {
        int button = click.button();
        double mouseX = click.x();
        double mouseY = click.y();
        if (button == 1 && QuickJoinConfig.enableFavorites()) {
            for (Button widget : entryButtons) {
                if (widget.isMouseOver(mouseX, mouseY)) {
                    Integer id = buttonIds.get(widget);
                    if (id == null || id >= 7000) {
                        return super.mouseClicked(click, doubled);
                    }
                    GuiData gui = guiLayout != null ? guiLayout.guis.get(guiName) : null;
                    if (gui == null || gui.funcs == null) return super.mouseClicked(click, doubled);
                    Func func = gui.funcs.get(String.valueOf(id));
                    if (func == null || "opengui".equals(func.type)) {
                        return super.mouseClicked(click, doubled);
                    }
                    boolean added = FavoritesManager.toggleFavorite(guiName, id);
                    if (QuickJoinConfig.debugMode() && Minecraft.getInstance().player != null) {
                        Minecraft.getInstance().player.displayClientMessage(Component.literal("Favorites " + (added ? "added" : "removed") + ": " + guiName + "#" + id), false);
                    }
                    init();
                    return true;
                }
            }
        }
        return super.mouseClicked(click, doubled);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, 0x80000000);
        super.render(context, mouseX, mouseY, delta);

        GuiData gui = guiLayout != null ? guiLayout.guis.get(guiName) : null;
        if (gui == null) {
            return;
        }
        int iconY = this.height / 2 - 125;
        
        ResourceLocation icon = ResourceLocation.fromNamespaceAndPath(QuickJoinFabric.MOD_ID, "icon" + gui.icon.sheet + ".png");
        int iconX = this.width / 2 - ICON_SIZE / 2;
        context.blit(RenderPipelines.GUI_TEXTURED, icon, iconX, iconY,
                (float) gui.icon.textureX, (float) gui.icon.textureY,
                ICON_SIZE, ICON_SIZE,
                ICON_SHEET_SIZE, ICON_SHEET_SIZE);

        int titleY = iconY + ICON_SIZE + 4;
        context.drawCenteredString(font, Component.literal(gui.name), this.width / 2, titleY, 0xFFFFFFFF);
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
