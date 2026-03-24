package app.qwertz.quickjoin.fabric.config;

import app.qwertz.quickjoin.data.Config;
import app.qwertz.quickjoin.data.GuiData;
import app.qwertz.quickjoin.fabric.QuickJoinFabric;
import app.qwertz.quickjoin.fallback.JsonFallback;
import app.qwertz.modernconfig.config.ConfigBuilder;
import app.qwertz.modernconfig.config.ConfigOption;
import app.qwertz.modernconfig.config.ModernConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * Fabric-side configuration loading using ModernConfig.
 */
public final class QuickJoinConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String REMOTE_GUI_URL = "https://raw.githubusercontent.com/QWERTZexe/Quickjoin/main/common/src/main/resources/assets/quickjoin/guis.json";

    private static ModernConfig modernConfig;
    private static Config guiLayout;

    private QuickJoinConfig() {
    }

    public static void init() {
        modernConfig = ConfigBuilder.create("QuickJoin", "QuickJoin Config", ResourceLocation.fromNamespaceAndPath("quickjoin", "logo.png"))
                .category("design", "Design", "Button appearance")
                .toggle("boldButtons", "Bold Buttons", true)
                .toggle("coloredButtons", "Colored Buttons", true)
                .end()
                .category("accessibility", "Accessibility", "Command alias and keybind")
                .toggle("enableAlias", "Enable Command Alias", true)
                .text("commandAlias", "Command Alias", "qj")
                .toggle("enableKeybind", "Enable QuickJoin Keybind", true)
                .end()
                .category("favourites", "Favourites", "Favorites list")
                .toggle("enableFavorites", "Enable Favorites", true)
                .end()
                .category("misc", "Miscellaneous", "Other options")
                .toggle("enabled", "Enable QuickJoin", true)
                .toggle("debugMode", "Debug Mode", false)
                .end()
                .build();
        modernConfig.onConfigSave(QuickJoinFabric::onConfigSaved);
        loadGuiLayout();
    }

    private static void loadGuiLayout() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(REMOTE_GUI_URL).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            try (Reader reader = new java.io.InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)) {
                guiLayout = GSON.fromJson(reader, Config.class);
            }
        } catch (Exception ignored) {
            guiLayout = GSON.fromJson(JsonFallback.getJsonFallback(), Config.class);
        }
    }

    public static Config getGuiLayout() {
        if (guiLayout == null) {
            loadGuiLayout();
        }
        return guiLayout;
    }

    public static boolean isEnabled() {
        return getBoolean("misc", "enabled", true);
    }

    public static boolean boldButtons() {
        return getBoolean("design", "boldButtons", true);
    }

    public static boolean coloredButtons() {
        return getBoolean("design", "coloredButtons", true);
    }

    public static boolean enableAlias() {
        return getBoolean("accessibility", "enableAlias", true);
    }

    public static String commandAlias() {
        return sanitizeAlias(getString("accessibility", "commandAlias", "qj"));
    }

    public static boolean enableKeybind() {
        return getBoolean("accessibility", "enableKeybind", true);
    }

    public static boolean enableFavorites() {
        return getBoolean("favourites", "enableFavorites", true);
    }

    public static boolean debugMode() {
        return getBoolean("misc", "debugMode", false);
    }

    public static Screen createConfigScreen(Screen parent) {
        return modernConfig != null ? modernConfig.getScreen() : parent;
    }

    private static boolean getBoolean(String category, String option, boolean defaultValue) {
        if (modernConfig == null) return defaultValue;
        ConfigOption<?> opt = modernConfig.getOption(category, option);
        return opt != null && opt.getValue() instanceof Boolean b ? b : defaultValue;
    }

    private static String getString(String category, String option, String defaultValue) {
        if (modernConfig == null) return defaultValue;
        ConfigOption<?> opt = modernConfig.getOption(category, option);
        return opt != null && opt.getValue() instanceof String s ? s : defaultValue;
    }

    private static String sanitizeAlias(String alias) {
        if (alias == null) return "";
        alias = alias.trim();
        if (alias.startsWith("/")) alias = alias.substring(1);
        return alias.replaceAll("\\s+", "").toLowerCase(Locale.ROOT);
    }

    public static void reloadGuiLayout() {
        loadGuiLayout();
    }

    public static Map<String, GuiData> getGuiMap() {
        return getGuiLayout() != null ? getGuiLayout().guis : Collections.emptyMap();
    }
}
