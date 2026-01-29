package app.qwertz.quickjoin.fabric.modmenu;

import app.qwertz.quickjoin.fabric.QuickJoinFabric;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;

public class ModMenuApiImpl implements com.terraformersmc.modmenu.api.ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return QuickJoinFabric::getConfigScreen;
    }
}
