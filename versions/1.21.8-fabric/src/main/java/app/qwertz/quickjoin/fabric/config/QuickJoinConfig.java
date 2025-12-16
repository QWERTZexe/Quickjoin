package app.qwertz.quickjoin.fabric.config;

import app.qwertz.quickjoin.data.Config;
import app.qwertz.quickjoin.data.GuiData;
import app.qwertz.quickjoin.fabric.QuickJoinFabric;
import app.qwertz.quickjoin.fallback.JsonFallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;

/**
 * Fabric-side configuration loading using YACL.
 */
public final class QuickJoinConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("quickjoin-fabric.json");
    private static final String REMOTE_GUI_URL = "https://raw.githubusercontent.com/QWERTZexe/Quickjoin/main/common/src/main/resources/assets/quickjoin/guis.json";

    private static Data data = new Data();
    private static Config guiLayout;

    private QuickJoinConfig() {
    }

    public static void init() {
        loadFile();
        loadGuiLayout();
    }

    private static void loadFile() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH, StandardCharsets.UTF_8)) {
                Data loaded = GSON.fromJson(reader, Data.class);
                if (loaded != null) {
                    data = loaded;
                }
            } catch (IOException ignored) {
            }
        }
    }

    private static void saveFile() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH, StandardCharsets.UTF_8)) {
                GSON.toJson(data, writer);
            }
        } catch (IOException ignored) {
        }
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
        return data.enabled;
    }

    public static boolean boldButtons() {
        return data.boldButtons;
    }

    public static boolean coloredButtons() {
        return data.coloredButtons;
    }

    public static boolean enableAlias() {
        return data.enableAlias;
    }

    public static String commandAlias() {
        return data.commandAlias;
    }

    public static boolean enableKeybind() {
        return data.enableKeybind;
    }

    public static boolean enableFavorites() {
        return data.enableFavorites;
    }

    public static boolean debugMode() {
        return data.debugMode;
    }

    public static Screen createConfigScreen(Screen parent) {
        ConfigCategory design = ConfigCategory.createBuilder()
                .name(Text.literal("Design"))
                .option(booleanOption("Bold Buttons", () -> data.boldButtons, value -> data.boldButtons = value))
                .option(booleanOption("Colored Buttons", () -> data.coloredButtons, value -> data.coloredButtons = value))
                .build();

        ConfigCategory accessibility = ConfigCategory.createBuilder()
                .name(Text.literal("Accessibility"))
                .option(booleanOption("Enable Command Alias", () -> data.enableAlias, value -> data.enableAlias = value))
                .option(stringOption("Command Alias", () -> data.commandAlias, value -> data.commandAlias = sanitizeAlias(value)))
                .option(booleanOption("Enable QuickJoin Keybind", () -> data.enableKeybind, value -> data.enableKeybind = value))
                .build();

        ConfigCategory favourites = ConfigCategory.createBuilder()
                .name(Text.literal("Favourites"))
                .option(booleanOption("Enable Favorites", () -> data.enableFavorites, value -> data.enableFavorites = value))
                .build();

        ConfigCategory misc = ConfigCategory.createBuilder()
                .name(Text.literal("Miscellaneous"))
                .option(booleanOption("Debug Mode", () -> data.debugMode, value -> data.debugMode = value))
                .build();

        YetAnotherConfigLib.Builder builder = YetAnotherConfigLib.createBuilder()
                .title(Text.literal("QuickJoin Config"))
                .category(design)
                .category(accessibility)
                .category(favourites)
                .category(misc)
                .save(() -> {
                    saveFile();
                    QuickJoinFabric.onConfigSaved();
                });

        return builder.build().generateScreen(parent);
    }

    private static Option<Boolean> booleanOption(String name, java.util.function.Supplier<Boolean> getter, java.util.function.Consumer<Boolean> setter) {
        return Option.<Boolean>createBuilder()
                .name(Text.literal(name))
                .binding(true, getter, setter)
                .controller(TickBoxControllerBuilder::create)
                .description(OptionDescription.of(Text.empty()))
                .build();
    }

    private static Option<String> stringOption(String name, java.util.function.Supplier<String> getter, java.util.function.Consumer<String> setter) {
        return Option.<String>createBuilder()
                .name(Text.literal(name))
                .binding("", getter, setter)
                .controller(StringControllerBuilder::create)
                .description(OptionDescription.of(Text.empty()))
                .build();
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
        return getGuiLayout() != null ? getGuiLayout().guis : java.util.Collections.emptyMap();
    }

    private static class Data {
        boolean enabled = true;
        boolean boldButtons = true;
        boolean coloredButtons = true;
        boolean enableAlias = true;
        String commandAlias = "qj";
        boolean enableKeybind = true;
        boolean enableFavorites = true;
        boolean debugMode = false;
    }
}
