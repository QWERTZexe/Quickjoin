package app.qwertz.quickjoin.data;

import java.util.Map;

public class Config {
    public MainGui mainGui;
    public Map<String, GuiData> guis;

    public Config(MainGui mainGui, Map<String, GuiData> guis) {
        this.mainGui = mainGui;
        this.guis = guis;
    }
}
