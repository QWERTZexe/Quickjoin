package app.qwertz.quickjoin.fabric.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

public final class LegacyText {

    private LegacyText() {
    }

    public static MutableText parse(String input) {
        MutableText result = Text.empty();
        if (input == null || input.isEmpty()) {
            return result;
        }

        Style style = Style.EMPTY;
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '§' && i + 1 < input.length()) {
                // flush existing text with current style
                if (current.length() > 0) {
                    result.append(Text.literal(current.toString()).setStyle(style));
                    current.setLength(0);
                }
                char code = Character.toLowerCase(input.charAt(++i));
                if (code == 'r') {
                    style = Style.EMPTY;
                    continue;
                }
                Formatting formatting = Formatting.byCode(code);
                if (formatting != null) {
                    if (formatting.isColor()) {
                        Integer colorValue = formatting.getColorValue();
                        style = Style.EMPTY;
                        if (colorValue != null) {
                            style = style.withColor(TextColor.fromRgb(colorValue));
                        }
                    } else {
                        style = applyFormat(style, formatting);
                    }
                }
            } else {
                current.append(c);
            }
        }
        if (current.length() > 0) {
            result.append(Text.literal(current.toString()).setStyle(style));
        }
        return result;
    }

    private static Style applyFormat(Style style, Formatting formatting) {
        switch (formatting) {
            case BOLD:
                return style.withBold(true);
            case ITALIC:
                return style.withItalic(true);
            case UNDERLINE:
                return style.withUnderline(true);
            case STRIKETHROUGH:
                return style.withStrikethrough(true);
            case OBFUSCATED:
                return style.withObfuscated(true);
            default:
                return style;
        }
    }
}
