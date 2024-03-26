package app.qwertz.quickjoin.fallback


fun getJsonFallback(): String {
    val jsonfallback = getResourceAsText("/assets/quickjoin/guis.json") ?: "{}"
    return jsonfallback
}

private fun getResourceAsText(path: String): String? =
        object {}.javaClass.getResource(path)?.readText()
