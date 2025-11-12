package app.qwertz.quickjoin.favorites;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class FavoritesManager {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Set<FavoriteEntry> favorites = new LinkedHashSet<>();
    private static Path favoritesFile;

    public static void initialize(Path configDirectory) {
        if (configDirectory == null) return;
        Path quickJoinDir = configDirectory.resolve("QuickJoin");
        try {
            Files.createDirectories(quickJoinDir);
        } catch (Exception ignored) {
            // If we can't create the directory, leave favorites disabled
            favoritesFile = null;
            return;
        }
        favoritesFile = quickJoinDir.resolve("favorites.json");
        load();
    }

    public static boolean isFavorite(String guiName, int buttonId) {
        return favorites.contains(new FavoriteEntry(guiName, buttonId));
    }

    public static boolean toggleFavorite(String guiName, int buttonId) {
        FavoriteEntry entry = new FavoriteEntry(guiName, buttonId);
        boolean added;
        if (favorites.contains(entry)) {
            favorites.remove(entry);
            added = false;
        } else {
            favorites.add(entry);
            added = true;
        }
        save();
        return added;
    }

    public static List<FavoriteEntry> getFavorites() {
        return new ArrayList<>(favorites); // Java 8 compatible
    }
    private static void load() {
        if (favoritesFile == null || !Files.exists(favoritesFile)) return;
        try (FileReader reader = new FileReader(favoritesFile.toFile())) {
            Type type = new TypeToken<List<FavoriteEntry>>() {}.getType();
            List<FavoriteEntry> loaded = gson.fromJson(reader, type);
            favorites.clear();
            if (loaded != null) favorites.addAll(loaded);
        } catch (Exception ignored) {
            favorites.clear(); // malformed file; start clean
        }
    }

    private static void save() {
        if (favoritesFile == null) return;
        try (FileWriter writer = new FileWriter(favoritesFile.toFile(), false)) {
            gson.toJson(favorites, writer);
        } catch (Exception ignored) {
            // ignore save errors to avoid crashing client
        }
    }
}
