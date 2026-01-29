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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class QuickJoinFabric implements ClientModInitializer {

    public static final String MOD_ID = "quickjoin";

    private static KeyMapping openKey;
    private static KeyMapping favoritesKey;
    private static KeyMapping searchKey;
    private static Supplier<Screen> pendingScreen;
    private static CommandDispatcher<FabricClientCommandSource> commandDispatcher;
    private static LiteralCommandNode<FabricClientCommandSource> aliasNode;
    private static KeyMapping keyBinding;
    private static final KeyMapping.Category QUICKJOIN_KEYMAPPING = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("quickjoin", "general"));
    @Override
    public void onInitializeClient() {
        QuickJoinConfig.init();
        FavoritesManager.initialize(net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir());

        registerKeyBindings();
        registerCommands();
        registerTickHandler();
    }
    private void registerKeyBindings() {
        openKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.quickjoin.open",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_X,
                QUICKJOIN_KEYMAPPING
        ));
        favoritesKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.quickjoin.favorites",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                QUICKJOIN_KEYMAPPING
        ));
        searchKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.quickjoin.search",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                QUICKJOIN_KEYMAPPING
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
        Minecraft client = source.getClient();
        if (client == null) {
            source.sendFeedback(Component.literal("QuickJoin: client unavailable."));
            return Command.SINGLE_SUCCESS;
        }
        client.execute(() -> {
            if (!openMainScreen(client)) {
                source.sendFeedback(Component.literal("QuickJoin: command cancelled."));
            }
        });
        return Command.SINGLE_SUCCESS;
    }

    private void registerTickHandler() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            if (pendingScreen != null && client.screen == null) {
                Screen next = pendingScreen.get();
                pendingScreen = null;
                client.setScreen(next);
            }

            while (openKey.consumeClick()) {
                if (QuickJoinConfig.enableKeybind()) {
                    openMainScreen(client);
                }
            }

            while (favoritesKey.consumeClick()) {
                if (QuickJoinConfig.enableFavorites()) {
                    openFavoritesScreen(client);
                }
            }

            while (searchKey.consumeClick()) {
                openSearchScreen(client);
            }
        });
    }

    private static boolean openMainScreen(Minecraft client) {
        if (!QuickJoinConfig.isEnabled()) {
            sendClientMessage(client, Component.literal("§4[§6§lQUICKJOIN§4]§a: The mod is disabled in the config."));
            return false;
        }
        if (!HypixelChecker.isOnHypixel(client)) {
            sendClientMessage(client, Component.literal("§4[§6§lQUICKJOIN§4]§a: You are not on Hypixel."));
            return false;
        }
        client.setScreen(null);
        scheduleScreen(() -> new QuickJoinScreen("QuickJoinGui"));
        return true;
    }

    static void openFavoritesScreen(Minecraft client) {
        if (!QuickJoinConfig.enableFavorites()) {
            return;
        }
        client.setScreen(null);
        scheduleScreen(FavoritesScreen::new);
    }

    static void openSearchScreen(Minecraft client) {
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
        Minecraft client = Minecraft.getInstance();
        if (client == null) {
            return;
        }
        client.execute(() -> client.setScreen(QuickJoinConfig.createConfigScreen(parent)));
    }

    public static Screen getConfigScreen(Screen parent) {
        return QuickJoinConfig.createConfigScreen(parent);
    }

    private static void scheduleScreen(Supplier<Screen> supplier) {
        pendingScreen = supplier;
    }

    public static void onConfigSaved() {
        refreshAlias();
    }

    private static void sendClientMessage(Minecraft client, Component message) {
        if (client.gui != null && client.gui.getChat() != null) {
            client.gui.getChat().addMessage(message);
        }
    }
}
