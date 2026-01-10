package app.qwertz.quickjoin.config;

import app.qwertz.quickjoin.QuickJoin;
import app.qwertz.quickjoin.data.Config;
import app.qwertz.quickjoin.gui.FavoritesGui;
import app.qwertz.quickjoin.gui.QuickJoinGui;
import app.qwertz.quickjoin.gui.SearchGui;
import cc.polyfrost.oneconfig.config.annotations.Header;
import cc.polyfrost.oneconfig.config.annotations.KeyBind;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.annotations.Text;
import cc.polyfrost.oneconfig.config.core.OneKeyBind;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.config.data.OptionSize;
import cc.polyfrost.oneconfig.libs.universal.UKeyboard;
import cc.polyfrost.oneconfig.utils.NetworkUtils;
import cc.polyfrost.oneconfig.utils.gui.GuiUtils;
import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils;
import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import static app.qwertz.quickjoin.fallback.JsonFallback.getJsonFallback;

public class QuickJoinConfig extends cc.polyfrost.oneconfig.config.Config {

    public static Config guis;

    @Header(text = "Design", size = OptionSize.DUAL)
    public boolean abc = false;
    @Switch(name = "Bold Buttons", size = OptionSize.SINGLE)
    public static boolean BoldSwitch = true;
    @Switch(name = "Colored Buttons", size = OptionSize.SINGLE)
    public static boolean ColorSwitch = true;

    @Header(text = "Accessibility", size = OptionSize.DUAL)
    public boolean abc2 = false;
    @Switch(name = "Enable Command Alias", size = OptionSize.SINGLE)
    public boolean EnableAlias = true;
    @Text(name = "Command Alias", size = OptionSize.SINGLE)
    public String CommandAlias = "qj";
    @Switch(name = "Enable Keybind", size = OptionSize.SINGLE)
    public boolean EnableKeyBind = true;
    @KeyBind(name = "Keybind", size = OptionSize.SINGLE)
    public OneKeyBind QJKeyBind = new OneKeyBind(UKeyboard.KEY_X);

    @Header(text = "Favourites", size = OptionSize.DUAL)
    public boolean abc3 = false;
    @Switch(name = "Enable Favorites", size = OptionSize.SINGLE)
    public static boolean EnableFavorites = true;
    @KeyBind(name = "Favorites Keybind", size = OptionSize.SINGLE)
    public OneKeyBind FavoritesKeyBind = new OneKeyBind(UKeyboard.KEY_C);

    @Header(text = "Miscellaneous", size = OptionSize.DUAL)
    public boolean abc4 = false;
    @KeyBind(name = "Search Keybind", size = OptionSize.SINGLE)
    public OneKeyBind SearchKeyBind = new OneKeyBind(UKeyboard.KEY_V);
    @Switch(name = "Debug Mode", size = OptionSize.DUAL)
    public static boolean DebugMode = false;

    public static final QuickJoinConfig INSTANCE = new QuickJoinConfig();

    public QuickJoinConfig() {
        super(new Mod(QuickJoin.NAME, ModType.HYPIXEL, "/QuickJoin.png"), QuickJoin.MODID + ".json");
        guis = loadConfig();
        initialize();

        registerKeyBind(QJKeyBind, () -> {
            if (EnableKeyBind) {
                if (!enabled) {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(
                            new ChatComponentText("§4[§6§lQUICKJOIN§4]§a: The mod is disabled in OneConfig. Please enable it.")
                    );
                } else {
                    if (HypixelUtils.INSTANCE.isHypixel()) {
                        if (DebugMode) {
                            Minecraft.getMinecraft().thePlayer.addChatMessage(
                                    new ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: Displaying MainGui")
                            );
                        }
                        GuiUtils.displayScreen(new QuickJoinGui());
                    } else {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(
                                new ChatComponentText("§4[§6§lQUICKJOIN§4]§a: You are not on Hypixel.")
                        );
                    }
                }
            }
        });

        registerKeyBind(FavoritesKeyBind, () -> {
            if (!EnableFavorites) return;
            if (!enabled) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText("§4[§6§lQUICKJOIN§4]§a: The mod is disabled in OneConfig. Please enable it.")
                );
            } else {
                if (HypixelUtils.INSTANCE.isHypixel()) {
                    if (DebugMode) {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(
                                new ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: Displaying Favorites")
                        );
                    }
                    GuiUtils.displayScreen(new FavoritesGui());
                } else {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(
                            new ChatComponentText("§4[§6§lQUICKJOIN§4]§a: You are not on Hypixel.")
                    );
                }
            }
        });

        registerKeyBind(SearchKeyBind, () -> {
            if (!enabled) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText("§4[§6§lQUICKJOIN§4]§a: The mod is disabled in OneConfig. Please enable it.")
                );
            } else {
                if (HypixelUtils.INSTANCE.isHypixel()) {
                    if (DebugMode) {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(
                                new ChatComponentText("§4[§6§lQUICKJOIN-DEBUG§4]§a: Displaying Search")
                        );
                    }
                    GuiUtils.displayScreen(new SearchGui());
                } else {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(
                            new ChatComponentText("§4[§6§lQUICKJOIN§4]§a: You are not on Hypixel.")
                    );
                }
            }
        });
    }

    public Config loadConfig() {
        try {
            return new Gson().fromJson(
                    NetworkUtils.getJsonElement("https://raw.githubusercontent.com/QWERTZexe/Quickjoin/main/common/src/main/resources/assets/quickjoin/guis.json").getAsJsonObject(),
                    Config.class
            );
        } catch (Exception e) {
            System.out.println("Error loading config: " + e.getMessage());
            return new Gson().fromJson(getJsonFallback(), Config.class);
        }
    }
}
