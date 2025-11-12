package app.qwertz.quickjoin;

import app.qwertz.quickjoin.config.QuickJoinConfig;
import app.qwertz.quickjoin.command.QuickJoinCommand;
import app.qwertz.quickjoin.favorites.FavoritesManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = QuickJoin.MODID, name = QuickJoin.NAME, version = QuickJoin.VERSION)
public class QuickJoin {

    public static QuickJoinConfig config;
    public boolean isEnabled() {
        return QuickJoin.config.enabled;
    }
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        config = QuickJoinConfig.INSTANCE;
        MinecraftForge.EVENT_BUS.register(new CommandEventHandler(this));
        ClientCommandHandler.instance.registerCommand(new QuickJoinCommand(this));
        FavoritesManager.initialize(new java.io.File(Minecraft.getMinecraft().mcDataDir, "config").toPath());
    }

    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";

    @Mod.Instance(MODID)
    public static QuickJoin INSTANCE;
}
