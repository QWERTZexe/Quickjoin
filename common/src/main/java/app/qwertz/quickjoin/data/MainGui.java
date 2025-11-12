package app.qwertz.quickjoin.data;

import java.util.List;
import java.util.Map;

public class MainGui {
    public List<ButtonConfig> buttons;
    public Map<String, Func> funcs;
    public String name;

    public MainGui(List<ButtonConfig> buttons, Map<String, Func> funcs, String name) {
        this.buttons = buttons;
        this.funcs = funcs;
        this.name = name;
    }
}
