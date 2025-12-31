package app.qwertz.quickjoin.fabric.screen;

import app.qwertz.quickjoin.data.ButtonConfig;
import app.qwertz.quickjoin.data.Config;
import app.qwertz.quickjoin.data.Func;
import app.qwertz.quickjoin.data.GuiData;
import app.qwertz.quickjoin.fabric.config.QuickJoinConfig;
import app.qwertz.quickjoin.fabric.util.LegacyText;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;

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

    private EditBox searchField;
    private int scrollOffset = 0;

    private final List<Button> entryButtons = new ArrayList<>();
    private final Map<Button, SearchEntry> entryMap = new HashMap<>();

    private final Minecraft clientInstance = Minecraft.getInstance();

    public SearchScreen() {
        super(Component.literal("Search"));
    }

    @Override
    protected void init() {
        super.init();
        clearWidgets();
        entryButtons.clear();
        entryMap.clear();

        if (entries.isEmpty()) {
            collectEntries();
        }

        int availableWidth = 260;
        int startX = this.width / 2 - availableWidth / 2;
        int topY = this.height / 2 - 90;

        if (searchField == null) {
            searchField = new EditBox(font, startX, topY - 22, availableWidth, 18, Component.empty());
            searchField.setCanLoseFocus(true);
            searchField.setResponder(s -> {
                scrollOffset = 0;
                rebuildEntries();
            });
        }
        searchField.setFocused(true);
        addRenderableWidget(searchField);

        rebuildEntries();

        addRenderableWidget(Button.builder(Component.literal("Back"), btn -> Minecraft.getInstance().setScreen(new QuickJoinScreen("QuickJoinGui")))
                .bounds(startX, this.height / 2 + 90, 128, 20)
                .build());
        addRenderableWidget(Button.builder(Component.literal("Close"), btn -> Minecraft.getInstance().setScreen(null))
                .bounds(startX + 132, this.height / 2 + 90, 128, 20)
                .build());
        addRenderableWidget(Button.builder(Component.literal("▲"), btn -> {
                    if (scrollOffset > 0) {
                        scrollOffset--;
                        rebuildEntries();
                    }
                })
                .bounds(startX, this.height / 2 + 66, 62, 20)
                .build());
        addRenderableWidget(Button.builder(Component.literal("▼"), btn -> {
                    int max = Math.max(0, filtered.size() - MAX_VISIBLE);
                    if (scrollOffset < max) {
                        scrollOffset++;
                        rebuildEntries();
                    }
                })
                .bounds(startX + 66, this.height / 2 + 66, 62, 20)
                .build());
        addRenderableWidget(Button.builder(Component.literal("Clear Search"), btn -> {
                    searchField.setValue("");
                    scrollOffset = 0;
                    rebuildEntries();
                })
                .bounds(startX + 132, this.height / 2 + 66, 128, 20)
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

        for (Button widget : entryButtons) {
            removeWidget(widget);
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
            MutableComponent label = LegacyText.parse(entry.formattedLabel());
            Button widget = Button.builder(label, btn -> onEntryClicked(entry))
                    .bounds(startX, y, availableWidth, 20)
                    .build();
            entryButtons.add(widget);
            entryMap.put(widget, entry);
            addRenderableWidget(widget);
            y += 22;
        }
    }

    private List<SearchEntry> filterEntries() {
        String query = searchField != null ? searchField.getValue() : "";
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
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, 0xB0000000);
        context.drawCenteredString(font, Component.literal("§aSearch"), width / 2, height / 2 - 110, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    private record SearchEntry(String guiName, int buttonId, String parentTitle, String childName, String childColor) {
        public String formattedLabel() {
            return "§b§l" + parentTitle + " §r§7>> " + childColor + childName;
        }
    }
}
