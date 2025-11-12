package app.qwertz.quickjoin.fabric;

import app.qwertz.quickjoin.data.Config;
import app.qwertz.quickjoin.fabric.config.QuickJoinConfig;
import app.qwertz.quickjoin.fabric.screen.FavoritesScreen;
import app.qwertz.quickjoin.fabric.screen.QuickJoinScreen;
import app.qwertz.quickjoin.fabric.screen.SearchScreen;
import app.qwertz.quickjoin.fabric.util.HypixelChecker;
import app.qwertz.quickjoin.favorites.FavoritesManager;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class QuickJoinFabric implements ClientModInitializer {

    public static final String MOD_ID = "quickjoin";

    private static KeyBinding openKey;
    private static KeyBinding favoritesKey;
    private static KeyBinding searchKey;
    private static Supplier<Screen> pendingScreen;
    private static CommandDispatcher<FabricClientCommandSource> commandDispatcher;
    private static LiteralCommandNode<FabricClientCommandSource> aliasNode;

    @Override
    public void onInitializeClient() {
        QuickJoinConfig.init();
        FavoritesManager.initialize(net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir());

        registerKeyBindings();
        registerCommands();
        registerTickHandler();
    }

    private void registerKeyBindings() {
        openKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.quickjoin.open",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_X,
                "category.quickjoin"
        ));
        favoritesKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.quickjoin.favorites",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                "category.quickjoin"
        ));
        searchKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.quickjoin.search",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.quickjoin"
        ));
    }

    private void registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            commandDispatcher = dispatcher;
            dispatcher.register(ClientCommandManager.literal("quickjoin")
                    .executes(ctx -> executeOpenQuickJoin(ctx.getSource())));
            refreshAlias();
        });
    }

    public static void refreshAlias() {
        if (commandDispatcher == null) {
            return;
        }
        removeAliasNode();
        if (!QuickJoinConfig.enableAlias()) {
            return;
        }
        String alias = QuickJoinConfig.commandAlias();
        if (alias.isBlank() || Objects.equals(alias, "quickjoin")) {
            return;
        }
        aliasNode = commandDispatcher.register(ClientCommandManager.literal(alias)
                .executes(ctx -> executeOpenQuickJoin(ctx.getSource())));
    }

    @SuppressWarnings("unchecked")
    private static void removeAliasNode() {
        if (aliasNode == null || commandDispatcher == null) {
            return;
        }
        CommandNode<FabricClientCommandSource> root = commandDispatcher.getRoot();
        root.getChildren().remove(aliasNode);
        try {
            Field childrenField = CommandNode.class.getDeclaredField("children");
            childrenField.setAccessible(true);
            Map<String, CommandNode<FabricClientCommandSource>> children = (Map<String, CommandNode<FabricClientCommandSource>>) childrenField.get(root);
            children.remove(aliasNode.getName());

            Field literalsField = CommandNode.class.getDeclaredField("literals");
            literalsField.setAccessible(true);
            Map<String, LiteralCommandNode<FabricClientCommandSource>> literals = (Map<String, LiteralCommandNode<FabricClientCommandSource>>) literalsField.get(root);
            literals.remove(aliasNode.getName());
        } catch (ReflectiveOperationException ignored) {
        }
        aliasNode = null;
    }

    private static int executeOpenQuickJoin(FabricClientCommandSource source) {
        MinecraftClient client = source.getClient();
        if (client == null) {
            source.sendFeedback(Text.literal("QuickJoin: client unavailable."));
            return Command.SINGLE_SUCCESS;
        }
        client.execute(() -> {
            if (!openMainScreen(client)) {
                source.sendFeedback(Text.literal("QuickJoin: command cancelled."));
            }
        });
        return Command.SINGLE_SUCCESS;
    }

    private void registerTickHandler() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            if (pendingScreen != null && client.currentScreen == null) {
                Screen next = pendingScreen.get();
                pendingScreen = null;
                client.setScreen(next);
            }

            while (openKey.wasPressed()) {
                if (QuickJoinConfig.enableKeybind()) {
                    openMainScreen(client);
                }
            }

            while (favoritesKey.wasPressed()) {
                if (QuickJoinConfig.enableFavorites()) {
                    openFavoritesScreen(client);
                }
            }

            while (searchKey.wasPressed()) {
                openSearchScreen(client);
            }
        });
    }

    private static boolean openMainScreen(MinecraftClient client) {
        if (!QuickJoinConfig.isEnabled()) {
            sendClientMessage(client, Text.literal("§4[§6§lQUICKJOIN§4]§a: The mod is disabled in the config."));
            return false;
        }
        if (!HypixelChecker.isOnHypixel(client)) {
            sendClientMessage(client, Text.literal("§4[§6§lQUICKJOIN§4]§a: You are not on Hypixel."));
            return false;
        }
        client.setScreen(null);
        scheduleScreen(() -> new QuickJoinScreen("QuickJoinGui"));
        return true;
    }

    static void openFavoritesScreen(MinecraftClient client) {
        if (!QuickJoinConfig.enableFavorites()) {
            return;
        }
        client.setScreen(null);
        scheduleScreen(FavoritesScreen::new);
    }

    static void openSearchScreen(MinecraftClient client) {
        client.setScreen(null);
        scheduleScreen(SearchScreen::new);
    }

    public static void openGui(String guiName) {
        scheduleScreen(() -> new QuickJoinScreen(guiName));
    }

    public static Config guiLayout() {
        return QuickJoinConfig.getGuiLayout();
    }

    public static void openConfigScreen(Screen parent) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) {
            return;
        }
        client.execute(() -> client.setScreen(QuickJoinConfig.createConfigScreen(parent)));
    }

    private static void scheduleScreen(Supplier<Screen> supplier) {
        pendingScreen = supplier;
    }

    public static void onConfigSaved() {
        refreshAlias();
    }

    private static void sendClientMessage(MinecraftClient client, Text message) {
        if (client.inGameHud != null && client.inGameHud.getChatHud() != null) {
            client.inGameHud.getChatHud().addMessage(message);
        }
    }
}
