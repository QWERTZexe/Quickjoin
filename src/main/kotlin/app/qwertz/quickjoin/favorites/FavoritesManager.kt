package app.qwertz.quickjoin.favorites

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.minecraft.client.Minecraft
import java.io.File
import java.io.FileReader
import java.io.FileWriter

data class FavoriteEntry(
    val guiName: String,
    val buttonId: Int
)

object FavoritesManager {
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val favorites: MutableSet<FavoriteEntry> = linkedSetOf()
    private lateinit var favoritesFile: File

    fun initialize() {
        val mcConfigDir = File(Minecraft.getMinecraft().mcDataDir, "config")
        val quickJoinDir = File(mcConfigDir, "QuickJoin")
        if (!quickJoinDir.exists()) quickJoinDir.mkdirs()
        favoritesFile = File(quickJoinDir, "favorites.json")
        load()
    }

    fun isFavorite(guiName: String, buttonId: Int): Boolean {
        return favorites.contains(FavoriteEntry(guiName, buttonId))
    }

    fun toggleFavorite(guiName: String, buttonId: Int): Boolean {
        val entry = FavoriteEntry(guiName, buttonId)
        val added = if (favorites.contains(entry)) {
            favorites.remove(entry)
            false
        } else {
            favorites.add(entry)
            true
        }
        save()
        return added
    }

    fun getFavorites(): List<FavoriteEntry> {
        return favorites.toList()
    }

    private fun load() {
        if (!::favoritesFile.isInitialized || !favoritesFile.exists()) return
        try {
            FileReader(favoritesFile).use { reader ->
                val type = object : TypeToken<List<FavoriteEntry>>() {}.type
                val loaded: List<FavoriteEntry> = gson.fromJson(reader, type) ?: emptyList()
                favorites.clear()
                favorites.addAll(loaded)
            }
        } catch (_: Exception) {
            // Ignore malformed file; start clean
            favorites.clear()
        }
    }

    private fun save() {
        if (!::favoritesFile.isInitialized) return
        try {
            FileWriter(favoritesFile, false).use { writer ->
                gson.toJson(favorites.toList(), writer)
            }
        } catch (_: Exception) {
            // Ignore save errors to avoid crashing the client
        }
    }
}


