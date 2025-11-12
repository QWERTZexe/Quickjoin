package app.qwertz.quickjoin.data;

public class ButtonConfig {
    public int id;
    public String x;
    public String y;
    public int width;
    public int height;
    public String label;
    public String colorCode;

    public ButtonConfig(int id, String x, String y, int width, int height, String label, String colorCode) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        this.colorCode = colorCode;
    }
}
