package app.qwertz.quickjoin.gui;

import app.qwertz.quickjoin.QuickJoin;
import app.qwertz.quickjoin.config.QuickJoinConfig;
import app.qwertz.quickjoin.favorites.FavoritesManager;
import app.qwertz.quickjoin.data.Config;
import app.qwertz.quickjoin.data.Func;
import app.qwertz.quickjoin.data.ButtonConfig;
import cc.polyfrost.oneconfig.utils.gui.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.List;

public class QuickJoinGui extends GuiScreen {

    private String guiname;
    private Config guiConfig;
    private String bold;
    private String color;

    public QuickJoinGui() {
        this("QuickJoinGui");
    }

    public QuickJoinGui(String guiname) {
        this.guiname = guiname;
        this.guiConfig = QuickJoinConfig.guis;
        CheckConfig check = new CheckConfig();
        this.bold = check.boldCheck();
        this.color = check.colorCheck();
    }

    public static class CheckConfig {
        public String boldCheck() {
            return QuickJoinConfig.BoldSwitch ? "§l" : "";
        }

        public String colorCheck() {
            return QuickJoinConfig.ColorSwitch ? "" : "§f";
        }
    }

    private double evaluateExpression(String expression) {
        String modifiedExpression = expression
                .replace("centerx", String.valueOf(this.width / 2))
                .replace("centery", String.valueOf(this.height / 2));
        String[] result = modifiedExpression.split(" ");
        if (result.length < 3) return 0;

        int left = Integer.parseInt(result[0]);
        int right = Integer.parseInt(result[2]);
        switch (result[1]) {
            case "-": return left - right;
            case "+": return left + right;
            default: return 0;
        }
    }

    @Override
    public void initGui() {
        this.buttonList.clear();

        List<ButtonConfig> buttons = guiConfig.guis.get(guiname).buttons;
        for (ButtonConfig buttonConfig : buttons) {
            boolean isFav = QuickJoinConfig.EnableFavorites && FavoritesManager.isFavorite(guiname, buttonConfig.id);
            String star = isFav ? "✮ " : "";
            String label = star + buttonConfig.colorCode + color + bold + buttonConfig.label;

            int resultX = (int) evaluateExpression(buttonConfig.x);
            int resultY = (int) evaluateExpression(buttonConfig.y);

            if (QuickJoinConfig.DebugMode) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: Adding button " + label)
                );
            }
            this.buttonList.add(new GuiButton(buttonConfig.id, resultX, resultY, buttonConfig.width, buttonConfig.height, label));
        }

        if ("QuickJoinGui".equals(guiname)) {
            int topY = 8;
            int rightX = this.width - 90;
            if (QuickJoinConfig.EnableFavorites) {
                this.buttonList.add(new GuiButton(7000, rightX, topY, 80, 20, "§6§lFavorites"));
                this.buttonList.add(new GuiButton(7001, this.width - 180, topY, 80, 20, "§a§lSearch"));
            } else {
                this.buttonList.add(new GuiButton(7001, rightX, topY, 80, 20, "§a§lSearch"));
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawRect(0, 0, this.width, this.height, 0x80000000);
        super.drawScreen(mouseX, mouseY, partialTicks);

        ResourceLocation texture = new ResourceLocation("quickjoin",
                "icon" + guiConfig.guis.get(guiname).icon.sheet + ".png");
        mc.getTextureManager().bindTexture(texture);
        GlStateManager.color(1f, 1f, 1f, 1f);
        drawTexturedModalRect(this.width / 2 - 38, this.height / 2 - 125,
                guiConfig.guis.get(guiname).icon.textureX, guiConfig.guis.get(guiname).icon.textureY,
                76, 76);
        this.drawCenteredString(this.fontRendererObj, guiConfig.guis.get(guiname).name,
                this.width / 2, this.height / 2 - 45, 0xFFFFFF);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 1 && QuickJoinConfig.EnableFavorites) {
            for (GuiButton btn : buttonList) {
                if (btn.id >= 0 && btn.mousePressed(mc, mouseX, mouseY)) {
                    Func func = guiConfig.guis.get(guiname).funcs.get(String.valueOf(btn.id));
                    if (func == null || "opengui".equals(func.type)) {
                        if (QuickJoinConfig.DebugMode) {
                            Minecraft.getMinecraft().thePlayer.addChatMessage(
                                    new ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: Favorites are only for playable entries, not categories.")
                            );
                        }
                        return;
                    }
                    boolean added = FavoritesManager.toggleFavorite(guiname, btn.id);
                    if (QuickJoinConfig.DebugMode) {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(
                                new ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: " + (added ? "added to" : "removed from") +
                                        " favorites: " + guiname + "#" + btn.id)
                        );
                    }
                    initGui();
                    return;
                }
            }
        }
        try {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        } catch (Exception ignored) {
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 7000) {
            GuiUtils.displayScreen(new FavoritesGui());
            return;
        }
        if (button.id == 7001) {
            GuiUtils.displayScreen(new SearchGui());
            return;
        }

        String buttonPressedId = String.valueOf(button.id);
        for (java.util.Map.Entry<String, Func> funcConfig : guiConfig.guis.get(guiname).funcs.entrySet()) {
            if (funcConfig.getKey().equals(buttonPressedId)) {
                Func func = funcConfig.getValue();
                switch (func.type) {
                    case "opengui":
                        if (QuickJoinConfig.DebugMode) {
                            Minecraft.getMinecraft().thePlayer.addChatMessage(
                                    new ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: Displaying " + func.string)
                            );
                        }
                        GuiUtils.displayScreen(new QuickJoinGui(func.string));
                        break;
                    case "command":
                        if (QuickJoinConfig.DebugMode) {
                            Minecraft.getMinecraft().thePlayer.addChatMessage(
                                    new ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: Sending command " + func.string)
                            );
                        }
                        Minecraft.getMinecraft().thePlayer.sendChatMessage(func.string);
                        GuiUtils.closeScreen();
                        break;
                    case "chat":
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(func.string));
                        GuiUtils.closeScreen();
                        break;
                    case "close":
                        if (QuickJoinConfig.DebugMode) {
                            Minecraft.getMinecraft().thePlayer.addChatMessage(
                                    new ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: Closing all GUIs")
                            );
                        }
                        GuiUtils.closeScreen();
                        break;
                    case "repeat":
                        if (QuickJoinConfig.DebugMode) {
                            Minecraft.getMinecraft().thePlayer.addChatMessage(
                                    new ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: Spamming " + func.string + " " + func.times + " times")
                            );
                        }
                        for (int i = 0; i < func.times; i++) {
                            GuiUtils.closeScreen();
                            Minecraft.getMinecraft().thePlayer.sendChatMessage(func.string);
                        }
                        break;
                }
            }
        }
    }
}
