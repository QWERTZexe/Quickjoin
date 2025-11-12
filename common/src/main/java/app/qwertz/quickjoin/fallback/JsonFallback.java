package app.qwertz.quickjoin.fallback;

public class JsonFallback {

    public static String getJsonFallback() {
        String jsonFallback = getResourceAsText("/assets/quickjoin/guis.json");
        return jsonFallback != null ? jsonFallback : "{}";
    }

    private static String getResourceAsText(String path) {
        try {
            java.io.InputStream is = JsonFallback.class.getResourceAsStream(path);
            if (is == null) return null;
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : null;
        } catch (Exception e) {
            return null;
        }
    }
}
