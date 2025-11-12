package app.qwertz.quickjoin.fabric.screen;

import app.qwertz.quickjoin.data.ButtonConfig;
import app.qwertz.quickjoin.data.Config;
import app.qwertz.quickjoin.data.Func;
import app.qwertz.quickjoin.data.GuiData;
import app.qwertz.quickjoin.fabric.config.QuickJoinConfig;
import app.qwertz.quickjoin.fabric.util.LegacyText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SearchScreen extends Screen {

    private static final int MAX_VISIBLE = 7;

    private final List<SearchEntry> entries = new ArrayList<>();
    private List<SearchEntry> filtered = new ArrayList<>();

    private TextFieldWidget searchField;
    private int scrollOffset = 0;

    private final List<ButtonWidget> entryButtons = new ArrayList<>();
    private final Map<ButtonWidget, SearchEntry> entryMap = new HashMap<>();

    private final MinecraftClient clientInstance = MinecraftClient.getInstance();

    public SearchScreen() {
        super(Text.literal("Search"));
    }

    @Override
    protected void init() {
        super.init();
        clearChildren();
        entryButtons.clear();
        entryMap.clear();

        if (entries.isEmpty()) {
            collectEntries();
        }

        int availableWidth = 260;
        int startX = this.width / 2 - availableWidth / 2;
        int topY = this.height / 2 - 90;

        if (searchField == null) {
            searchField = new TextFieldWidget(textRenderer, startX, topY - 22, availableWidth, 18, Text.empty());
            searchField.setFocusUnlocked(true);
            searchField.setChangedListener(s -> {
                scrollOffset = 0;
                rebuildEntries();
            });
        }
        searchField.setFocused(true);
        addDrawableChild(searchField);

        rebuildEntries();

        addDrawableChild(ButtonWidget.builder(Text.literal("Back"), btn -> this.client.setScreen(new QuickJoinScreen("QuickJoinGui")))
                .dimensions(startX, this.height / 2 + 90, 128, 20)
                .build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Close"), btn -> this.client.setScreen(null))
                .dimensions(startX + 132, this.height / 2 + 90, 128, 20)
                .build());
        addDrawableChild(ButtonWidget.builder(Text.literal("▲"), btn -> {
                    if (scrollOffset > 0) {
                        scrollOffset--;
                        rebuildEntries();
                    }
                })
                .dimensions(startX, this.height / 2 + 66, 62, 20)
                .build());
        addDrawableChild(ButtonWidget.builder(Text.literal("▼"), btn -> {
                    int max = Math.max(0, filtered.size() - MAX_VISIBLE);
                    if (scrollOffset < max) {
                        scrollOffset++;
                        rebuildEntries();
                    }
                })
                .dimensions(startX + 66, this.height / 2 + 66, 62, 20)
                .build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Clear Search"), btn -> {
                    searchField.setText("");
                    scrollOffset = 0;
                    rebuildEntries();
                })
                .dimensions(startX + 132, this.height / 2 + 66, 128, 20)
                .build());
    }

    private void collectEntries() {
        Config layout = QuickJoinConfig.getGuiLayout();
        if (layout == null || layout.guis == null) {
            return;
        }
        layout.guis.forEach((guiName, gui) -> {
            if (gui == null || gui.funcs == null) return;
            String parent = "QuickJoinGui".equals(guiName) ? "Quickjoin" : gui.name;
            for (Map.Entry<String, Func> entry : gui.funcs.entrySet()) {
                Func func = entry.getValue();
                if (func == null || "opengui".equals(func.type)) continue;
                int id;
                try {
                    id = Integer.parseInt(entry.getKey());
                } catch (NumberFormatException e) {
                    continue;
                }
                ButtonConfig button = null;
                if (gui.buttons != null) {
                    for (ButtonConfig b : gui.buttons) {
                        if (b.id == id) {
                            button = b;
                            break;
                        }
                    }
                }
                String child = button != null ? button.label : guiName + "#" + id;
                String color = button != null ? button.colorCode : "§f";
                entries.add(new SearchEntry(guiName, id, parent, child, color));
            }
        });
        entries.sort(Comparator.comparing((SearchEntry se) -> se.parentTitle).thenComparing(se -> se.childName));
    }

    private void rebuildEntries() {
        filtered = filterEntries();

        for (ButtonWidget widget : entryButtons) {
            remove(widget);
        }
        entryButtons.clear();
        entryMap.clear();

        int availableWidth = 260;
        int startX = this.width / 2 - availableWidth / 2;
        int topY = this.height / 2 - 90;

        int visible = Math.min(MAX_VISIBLE, Math.max(0, filtered.size() - scrollOffset));
        int y = topY;
        for (int i = 0; i < visible; i++) {
            SearchEntry entry = filtered.get(scrollOffset + i);
            MutableText label = LegacyText.parse(entry.formattedLabel());
            ButtonWidget widget = ButtonWidget.builder(label, btn -> onEntryClicked(entry))
                    .dimensions(startX, y, availableWidth, 20)
                    .build();
            entryButtons.add(widget);
            entryMap.put(widget, entry);
            addDrawableChild(widget);
            y += 22;
        }
    }

    private List<SearchEntry> filterEntries() {
        String query = searchField != null ? searchField.getText() : "";
        if (query == null || query.isBlank()) {
            return new ArrayList<>(entries);
        }
        String q = query.toLowerCase(Locale.ROOT);
        List<SearchEntry> list = new ArrayList<>();
        for (SearchEntry entry : entries) {
            if (matches(entry, q)) {
                list.add(entry);
            }
        }
        return list;
    }

    private boolean matches(SearchEntry entry, String query) {
        String parent = entry.parentTitle.toLowerCase(Locale.ROOT);
        String child = entry.childName.toLowerCase(Locale.ROOT);
        String combined = (entry.parentTitle + " >> " + entry.childName).toLowerCase(Locale.ROOT);
        return containsOrFuzzy(parent, query) || containsOrFuzzy(child, query) || containsOrFuzzy(combined, query);
    }

    private boolean containsOrFuzzy(String text, String query) {
        if (text.contains(query)) {
            return true;
        }
        if (Math.abs(text.length() - query.length()) > 1) {
            return false;
        }
        return levenshteinLeq1(text, query);
    }

    private boolean levenshteinLeq1(String a, String b) {
        if (a.equals(b)) return true;
        int la = a.length();
        int lb = b.length();
        if (Math.abs(la - lb) > 1) return false;
        int i = 0, j = 0, edits = 0;
        while (i < la && j < lb) {
            if (a.charAt(i) == b.charAt(j)) {
                i++; j++;
            } else {
                if (edits == 1) return false;
                edits++;
                if (la > lb) {
                    i++;
                } else if (lb > la) {
                    j++;
                } else {
                    i++; j++;
                }
            }
        }
        if (i < la || j < lb) edits++;
        return edits <= 1;
    }

    private void onEntryClicked(SearchEntry entry) {
        Config layout = QuickJoinConfig.getGuiLayout();
        if (layout == null || layout.guis == null) return;
        GuiData gui = layout.guis.get(entry.guiName);
        if (gui == null || gui.funcs == null) return;
        Func func = gui.funcs.get(String.valueOf(entry.buttonId));
        if (func == null) return;
        QuickJoinActions.execute(clientInstance, entry.guiName, func);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (verticalAmount > 0) {
            if (scrollOffset > 0) {
                scrollOffset--;
                rebuildEntries();
                return true;
            }
        } else if (verticalAmount < 0) {
            int max = Math.max(0, filtered.size() - MAX_VISIBLE);
            if (scrollOffset < max) {
                scrollOffset++;
                rebuildEntries();
                return true;
            }
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, 0xB0000000);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("§aSearch"), width / 2, height / 2 - 110, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    private record SearchEntry(String guiName, int buttonId, String parentTitle, String childName, String childColor) {
        public String formattedLabel() {
            return "§b§l" + parentTitle + " §r§7>> " + childColor + childName;
        }
    }
}
