package app.qwertz.quickjoin.data;

import java.util.List;
import java.util.Map;

public class GuiData {
    public List<ButtonConfig> buttons;
    public Map<String, Func> funcs;
    public String name;
    public Icon icon;

    public GuiData(List<ButtonConfig> buttons, Map<String, Func> funcs, String name, Icon icon) {
        this.buttons = buttons;
        this.funcs = funcs;
        this.name = name;
        this.icon = icon;
    }
}
