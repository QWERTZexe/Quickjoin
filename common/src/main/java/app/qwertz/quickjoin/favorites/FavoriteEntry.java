package app.qwertz.quickjoin.favorites;

import java.util.Objects;

public class FavoriteEntry {
    public String guiName;
    public int buttonId;

    public FavoriteEntry(String guiName, int buttonId) {
        this.guiName = guiName;
        this.buttonId = buttonId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FavoriteEntry)) return false;
        FavoriteEntry other = (FavoriteEntry) obj;
        return this.buttonId == other.buttonId && Objects.equals(this.guiName, other.guiName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiName, buttonId);
    }
}
